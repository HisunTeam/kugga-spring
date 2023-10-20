package com.hisun.kugga.duke.batch.job.payorder;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.batch.bo.BillBO;
import com.hisun.kugga.duke.batch.channel.wallet.WalletClient;
import com.hisun.kugga.duke.batch.channel.wallet.dto.PayDetailReqBody;
import com.hisun.kugga.duke.batch.channel.wallet.dto.PayDetailRspBody;
import com.hisun.kugga.duke.batch.dal.dataobject.leaguebill.LeagueBillDO;
import com.hisun.kugga.duke.batch.dal.dataobject.payorder.PayOrderDO;
import com.hisun.kugga.duke.batch.dal.dataobject.userbill.UserBillDO;
import com.hisun.kugga.duke.batch.dal.mysql.payorder.PayOrderMapper;
import com.hisun.kugga.duke.batch.service.BillService;
import com.hisun.kugga.duke.batch.service.LeagueBillService;
import com.hisun.kugga.duke.batch.service.UserBillService;
import com.hisun.kugga.duke.common.CommonConstants;
import com.hisun.kugga.duke.enums.AccountType;
import com.hisun.kugga.duke.enums.PayOrderStatus;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.quartz.core.handler.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Iterator;

import static com.hisun.kugga.duke.common.CommonConstants.SNOWFLAKE;
import static com.hisun.kugga.duke.common.CommonConstants.WalletStatus.*;

/**
 * Scheduled Task for Pay Order Status Polling
 * This task periodically checks the status of payment orders.
 * Author: zhou_xiong
 */
@Slf4j
@Component
public class PayOrderStatusJob implements JobHandler {
    @Resource
    private WalletClient walletClient;
    @Resource
    private UserBillService userBillService;
    @Resource
    private LeagueBillService leagueBillService;
    @Resource
    private PayOrderMapper payOrderMapper;
    @Resource
    private BillService billService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String execute(String param) throws Exception {
        // Cursor-based query for all payment orders in the initialized state
        Cursor<PayOrderDO> selectCursor = payOrderMapper.selectPrepayOrders();
        try {
            Iterator<PayOrderDO> iterator = selectCursor.iterator();
            while (iterator.hasNext()) {
                PayOrderDO payOrderDO = iterator.next();
                // Query payment details
                PayDetailRspBody payDetailRspBody = walletClient.payDetail(new PayDetailReqBody().setOrderNo(payOrderDO.getWalletOrderNo()));
                // Update payment order status
                if (StrUtil.equalsAny(payDetailRspBody.getStatus(), SUCCESS, FAILED, CLOSED)) {
                    payOrderMapper.update(null, new LambdaUpdateWrapper<PayOrderDO>()
                            .set(PayOrderDO::getStatus, statusEscape(payDetailRspBody.getStatus()))
                            .eq(PayOrderDO::getAppOrderNo, payOrderDO.getAppOrderNo()));
                }
                // Generate user bills after successful payment
                if (StrUtil.equals(payDetailRspBody.getStatus(), SUCCESS)) {
                    BillBO billBO = new BillBO()
                            .setAccountType(payOrderDO.getAccountType())
                            .setWalletOrderNo(payOrderDO.getWalletOrderNo())
                            .setObjectId(payOrderDO.getPayerId())
                            .setAmount(payOrderDO.getPayAmount().negate())
                            .setRemark(payOrderDO.getOrderType().getDesc());
                    billService.saveBillByAccountType(billBO);
                }
            }
        } catch (Exception e) {
            log.error("PayOrderStatusJob.execute() error", e);
            throw e;
        } finally {
            selectCursor.close();
        }
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    private PayOrderStatus statusEscape(String status) {
        switch (status) {
            case SUCCESS:
                return PayOrderStatus.PAY_SUCCESS;
            case FAILED:
                return PayOrderStatus.PAY_FAILED;
            case CLOSED:
                return PayOrderStatus.CLOSED;
            default:
                return PayOrderStatus.PREPAY;
        }
    }
}
