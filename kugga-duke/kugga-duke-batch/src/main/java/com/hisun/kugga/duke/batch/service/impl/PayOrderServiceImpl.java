package com.hisun.kugga.duke.batch.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.batch.channel.wallet.WalletClient;
import com.hisun.kugga.duke.batch.channel.wallet.dto.DrawbackApplyReqBody;
import com.hisun.kugga.duke.batch.channel.wallet.dto.DrawbackApplyRspBody;
import com.hisun.kugga.duke.batch.dal.dataobject.payorder.PayOrderDO;
import com.hisun.kugga.duke.batch.dal.dataobject.payorderrefund.PayOrderRefundDO;
import com.hisun.kugga.duke.batch.dal.mysql.payorder.PayOrderMapper;
import com.hisun.kugga.duke.batch.dal.mysql.payorderrefund.PayOrderRefundMapper;
import com.hisun.kugga.duke.batch.dto.RefundReqDTO;
import com.hisun.kugga.duke.batch.service.PayOrderService;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.enums.OrderType;
import com.hisun.kugga.duke.enums.PayOrderRefundStatus;
import com.hisun.kugga.duke.enums.PayOrderStatus;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.util.amount.AmountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;


/**
 * Pay Order Service Implementation
 *
 * Author: Zuo Cheng
 */
@Slf4j
@Service
@Validated
public class PayOrderServiceImpl implements PayOrderService {

    @Resource
    private PayOrderMapper payOrderMapper;

    @Resource
    private WalletClient walletClient;

    @Resource
    private PayOrderRefundMapper payOrderRefundMapper;

    @Override
    public void refund(RefundReqDTO refundReqDTO) {
        // Query the transaction order
        PayOrderDO payOrderDO = payOrderMapper.selectOne(new LambdaQueryWrapper<PayOrderDO>()
                .eq(PayOrderDO::getAppOrderNo, refundReqDTO.getAppOrderNo()));
        if (ObjectUtil.isNull(payOrderDO) || !PayOrderStatus.PAY_SUCCESS.equals(payOrderDO.getStatus())) {
            return;
        }
        // Refund amount cannot exceed the available refundable amount
        BigDecimal canRefundAmount = AmountUtil.sub(payOrderDO.getPayAmount(), payOrderDO.getRefundAmount());
        if (refundReqDTO.getRefundAmount().compareTo(canRefundAmount) > 0) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.ILLEGAL_REFUND_AMOUNT);
        }
        // Request a refund from the wallet
        DrawbackApplyReqBody drawbackApplyReqBody = new DrawbackApplyReqBody()
                .setDrawbackAmount(AmountUtil.yuanToFen(refundReqDTO.getRefundAmount()))
                .setAccount(payOrderDO.getAccountId())
                .setCallbackUrl("https://www.baidu.com")
                .setOrderType("pay")
                .setOriginalOrderNo(payOrderDO.getWalletOrderNo());
        DrawbackApplyRspBody drawbackApplyRspBody = walletClient.drawbackApply(drawbackApplyReqBody);
        log.info("Order [{}] refund response: {}", refundReqDTO.getAppOrderNo(), drawbackApplyRspBody);
        // Save the refund record
        PayOrderRefundDO payOrderRefundDO = new PayOrderRefundDO()
                .setAppOrderNo(payOrderDO.getAppOrderNo())
                .setRefundNo(drawbackApplyRspBody.getOrderNo())
                .setAmount(refundReqDTO.getRefundAmount())
                .setRemark(OrderType.REFUND.getDesc() + payOrderDO.getRemark())
                .setStatus(PayOrderRefundStatus.PRE_REFUND);
        payOrderRefundMapper.insert(payOrderRefundDO);
    }
}
