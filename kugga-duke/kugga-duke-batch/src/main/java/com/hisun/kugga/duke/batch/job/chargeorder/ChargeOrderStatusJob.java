package com.hisun.kugga.duke.batch.job.chargeorder;

import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.duke.batch.channel.wallet.WalletClient;
import com.hisun.kugga.duke.batch.channel.wallet.dto.ChargeDetailReqBody;
import com.hisun.kugga.duke.batch.channel.wallet.dto.ChargeDetailRspBody;
import com.hisun.kugga.duke.batch.dal.dataobject.chargeorder.ChargeOrderDO;
import com.hisun.kugga.duke.batch.dal.dataobject.userbill.UserBillDO;
import com.hisun.kugga.duke.batch.dal.mysql.chargeorder.ChargeOrderMapper;
import com.hisun.kugga.duke.batch.service.UserBillService;
import com.hisun.kugga.duke.common.CommonConstants;
import com.hisun.kugga.duke.enums.ChargeOrderStatus;
import com.hisun.kugga.duke.enums.OrderType;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.common.util.amount.AmountUtil;
import com.hisun.kugga.framework.quartz.core.handler.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Iterator;

import static com.hisun.kugga.duke.common.CommonConstants.SNOWFLAKE;

/**
 * Charge Order Status Job for Polling
 * Handles the results of recharge orders.
 * Author: zhou_xiong
 */
@Slf4j
@Component
public class ChargeOrderStatusJob implements JobHandler {
    @Resource
    private WalletClient walletClient;
    @Resource
    private ChargeOrderMapper chargeOrderMapper;
    @Resource
    private UserBillService userBillService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String execute(String param) throws Exception {
        // Cursor to query all initialized recharge order numbers in the database
        Cursor<ChargeOrderDO> selectCursor = chargeOrderMapper.selectInitChargeOrders();
        try {
            Iterator<ChargeOrderDO> iterator = selectCursor.iterator();
            while (iterator.hasNext()) {
                ChargeOrderDO chargeOrderDO = iterator.next();
                // Query recharge details
                ChargeDetailRspBody chargeDetailRspBody = walletClient.chargeDetail(new ChargeDetailReqBody().setOrderNo(chargeOrderDO.getWalletOrderNo()));
                // Update recharge order status
                chargeOrderMapper.updateStatus(chargeOrderDO.getWalletOrderNo(), chargeDetailRspBody.getStatus(), chargeDetailRspBody.getReceivedTime());
                // Generate user bill after successful recharge
                if (StrUtil.equals(chargeDetailRspBody.getStatus(), ChargeOrderStatus.CHARGE_SUCCESS.getKey())) {
                    UserBillDO userBillDO = new UserBillDO()
                            .setBillNo(SNOWFLAKE.nextIdStr())
                            .setWalletOrderNo(chargeOrderDO.getWalletOrderNo())
                            .setUserId(chargeOrderDO.getUserId())
                            .setAmount(AmountUtil.fenToYuan(chargeDetailRspBody.getActualAmount()))
                            .setFee(AmountUtil.fenToYuan(chargeDetailRspBody.getFee()).negate())
                            .setStatus(CommonConstants.BillStatus.SUCCESS)
                            .setRemark(OrderType.CHARGE.getDesc());
                    userBillService.insertIfNotExist(userBillDO);
                }
            }
        } catch (Exception e) {
            log.error("ChargeOrderStatusJob.execute() error", e);
            throw e;
        } finally {
            selectCursor.close();
        }
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }
}
