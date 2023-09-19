package com.hisun.kugga.duke.batch.job.withdraworder;

import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.duke.batch.channel.wallet.WalletClient;
import com.hisun.kugga.duke.batch.channel.wallet.dto.WithdrawDetailReqBody;
import com.hisun.kugga.duke.batch.channel.wallet.dto.WithdrawDetailRspBody;
import com.hisun.kugga.duke.batch.dal.dataobject.userbill.UserBillDO;
import com.hisun.kugga.duke.batch.dal.dataobject.withdraworder.WithdrawOrderDO;
import com.hisun.kugga.duke.batch.dal.mysql.withdraworder.WithdrawOrderMapper;
import com.hisun.kugga.duke.batch.service.UserBillService;
import com.hisun.kugga.duke.common.CommonConstants;
import com.hisun.kugga.duke.enums.OrderType;
import com.hisun.kugga.duke.enums.WithdrawStatus;
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
 * 提现订单结果定时轮询
 *
 * @author: zhou_xiong
 */
@Slf4j
@Component
public class WithdrawOrderStatusJob implements JobHandler {
    @Resource
    private WalletClient walletClient;
    @Resource
    private WithdrawOrderMapper withdrawOrderMapper;
    @Resource
    private UserBillService userBillService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String execute(String param) throws Exception {
        // 游标查询数据库中所有draft状态提现订单号
        Cursor<WithdrawOrderDO> selectCursor = withdrawOrderMapper.selectDraftOrders();
        try {
            Iterator<WithdrawOrderDO> iterator = selectCursor.iterator();
            while (iterator.hasNext()) {
                WithdrawOrderDO withdrawOrderDO = iterator.next();
                // 查询提现详情
                WithdrawDetailRspBody withdrawDetailRspBody = walletClient.withdrawDetail(new WithdrawDetailReqBody().setOrderNo(withdrawOrderDO.getWalletOrderNo()));
                // 修改提现订单状态
                withdrawOrderMapper.updateStatus(withdrawOrderDO.getWalletOrderNo(), withdrawDetailRspBody);
                // 提现成功后生成用户账单
                if (StrUtil.equals(withdrawDetailRspBody.getStatus(), WithdrawStatus.SUCCESS.getKey())) {
                    UserBillDO userBillDO = new UserBillDO()
                            .setBillNo(SNOWFLAKE.nextIdStr())
                            .setWalletOrderNo(withdrawOrderDO.getWalletOrderNo())
                            .setUserId(withdrawOrderDO.getUserId())
                            .setAmount(AmountUtil.fenToYuan(withdrawDetailRspBody.getActualAmount()).negate())
                            .setFee(AmountUtil.fenToYuan(withdrawDetailRspBody.getFee()).negate())
                            .setStatus(CommonConstants.BillStatus.SUCCESS)
                            .setRemark(OrderType.WITHDRAW.getDesc());
                    userBillService.insertIfNotExist(userBillDO);
                }
            }
        } catch (Exception e) {
            log.error("WithdrawOrderStatusJob.execute() error", e);
            throw e;
        } finally {
            selectCursor.close();
        }
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }
}
