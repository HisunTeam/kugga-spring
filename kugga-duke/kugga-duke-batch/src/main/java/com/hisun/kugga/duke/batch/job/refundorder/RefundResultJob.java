package com.hisun.kugga.duke.batch.job.refundorder;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.batch.channel.wallet.WalletClient;
import com.hisun.kugga.duke.batch.channel.wallet.dto.DrawbackDetailReqBody;
import com.hisun.kugga.duke.batch.channel.wallet.dto.DrawbackDetailRspBody;
import com.hisun.kugga.duke.batch.dal.dataobject.leaguebill.LeagueBillDO;
import com.hisun.kugga.duke.batch.dal.dataobject.payorder.PayOrderDO;
import com.hisun.kugga.duke.batch.dal.dataobject.payorderrefund.PayOrderRefundDO;
import com.hisun.kugga.duke.batch.dal.dataobject.userbill.UserBillDO;
import com.hisun.kugga.duke.batch.dal.mysql.leaguebill.LeagueBillMapper;
import com.hisun.kugga.duke.batch.dal.mysql.payorder.PayOrderMapper;
import com.hisun.kugga.duke.batch.dal.mysql.payorderrefund.PayOrderRefundMapper;
import com.hisun.kugga.duke.batch.dal.mysql.userbill.UserBillMapper;
import com.hisun.kugga.duke.common.CommonConstants;
import com.hisun.kugga.duke.enums.AccountType;
import com.hisun.kugga.duke.enums.PayOrderRefundStatus;
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
import static com.hisun.kugga.duke.common.CommonConstants.WalletStatus.FAILED;
import static com.hisun.kugga.duke.common.CommonConstants.WalletStatus.SUCCESS;

/**
 * 退款结果定时轮询
 *
 * @author: zhou_xiong
 */
@Slf4j
@Component
public class RefundResultJob implements JobHandler {
    @Resource
    private PayOrderRefundMapper payOrderRefundMapper;
    @Resource
    private WalletClient walletClient;
    @Resource
    private PayOrderMapper payOrderMapper;
    @Resource
    private UserBillMapper userBillMapper;
    @Resource
    private LeagueBillMapper leagueBillMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String execute(String param) throws Exception {
        // 查出待退款记录
        Cursor<PayOrderRefundDO> selectCursor = payOrderRefundMapper.selectPreRefund();
        // 查询钱包退款结果
        Iterator<PayOrderRefundDO> iterator = selectCursor.iterator();
        while (iterator.hasNext()) {
            PayOrderRefundDO payOrderRefundDO = iterator.next();
            DrawbackDetailReqBody drawbackDetailReqBody = new DrawbackDetailReqBody()
                    .setOrderNo(payOrderRefundDO.getRefundNo());
            DrawbackDetailRspBody drawbackDetailRspBody = walletClient.drawbackDetail(drawbackDetailReqBody);
            // 查询订单记录
            PayOrderDO payOrderDO = payOrderMapper.selectOne(new LambdaQueryWrapper<PayOrderDO>()
                    .eq(PayOrderDO::getAppOrderNo, payOrderRefundDO.getAppOrderNo()));
            if (SUCCESS.equals(drawbackDetailRspBody.getStatus())) {
                // 修改订单退款记录状态
                payOrderRefundMapper.updateStatus(payOrderRefundDO.getId(), PayOrderRefundStatus.REFUND_SUCCESS,
                        drawbackDetailRspBody.getSuccessTime());
                // 修改订单状态（部分退款或已退款），退款金额
                payOrderMapper.updateStatusRefundSuccess(payOrderDO.getId(), payOrderRefundDO.getAmount());
                // 生成账单
                saveRefundBill(payOrderDO, payOrderRefundDO);
            } else if (FAILED.equals(drawbackDetailRspBody.getStatus())) {
                // 修改订单退款记录状态
                payOrderRefundMapper.updateStatus(payOrderRefundDO.getId(), PayOrderRefundStatus.REFUND_FAILED,
                        drawbackDetailRspBody.getSuccessTime());
                // 修改订单状态（退款失败）
                if (payOrderRefundDO.getAmount().compareTo(payOrderDO.getPayAmount()) >= 0) {
                    payOrderMapper.updateStatus(payOrderDO.getId(), PayOrderStatus.REFUND_FAILED);
                }
            }
        }
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    private void saveRefundBill(PayOrderDO payOrderDO, PayOrderRefundDO payOrderRefundDO) {
        AccountType accountType = payOrderDO.getAccountType();
        String billNo = SNOWFLAKE.nextIdStr();
        switch (accountType) {
            case USER:
                UserBillDO userBillDO = new UserBillDO();
                userBillDO.setBillNo(billNo);
                userBillDO.setWalletOrderNo(payOrderDO.getWalletOrderNo());
                userBillDO.setUserId(payOrderDO.getPayerId());
                userBillDO.setAmount(payOrderRefundDO.getAmount());
                userBillDO.setStatus(CommonConstants.BillStatus.SUCCESS);
                userBillDO.setRemark(payOrderRefundDO.getRemark());
                // 退款生成账单，可以有多比账单，支付账单和退款账单
                userBillMapper.insert(userBillDO);
                break;
            case LEAGUE:
                LeagueBillDO leagueBillDO = new LeagueBillDO();
                leagueBillDO.setBillNo(billNo);
                leagueBillDO.setWalletOrderNo(payOrderDO.getWalletOrderNo());
                leagueBillDO.setLeagueId(payOrderDO.getPayerId());
                leagueBillDO.setAmount(payOrderRefundDO.getAmount());
                leagueBillDO.setStatus(CommonConstants.BillStatus.SUCCESS);
                leagueBillDO.setRemark(payOrderRefundDO.getRemark());
                leagueBillMapper.insert(leagueBillDO);
                break;
            default:
                return;
        }

    }
}
