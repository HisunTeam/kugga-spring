package com.hisun.kugga.duke.pay.api.order;

import com.hisun.kugga.duke.pay.api.order.dto.*;
import com.hisun.kugga.duke.pay.service.payorder.PayOrderService;
import com.hisun.kugga.duke.pay.service.redpacket.RedPacketService;
import com.hisun.kugga.framework.idempotent.core.annotation.Idempotent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author: zhou_xiong
 */
@Slf4j
@Service
public class OrderApiImpl implements OrderApi {

    @Resource
    private PayOrderService payOrderService;
    @Resource
    private RedPacketService redPacketService;

    @Override
    @Idempotent(timeout = 2, message = "Repeat ordering,please try again later")
    public OrderCreateRspDTO createOrder(OrderCreateReqDTO orderCreateReqDTO) {
        return payOrderService.createOrder(orderCreateReqDTO);
    }

    @Override
    @Idempotent(timeout = 2, message = "Repeat paying,please try again later")
    public void pay(PayReqDTO payReqDTO) {
        payOrderService.pay(payReqDTO);
    }

    @Override
    @Idempotent(timeout = 2, message = "Repeat spliting account,please try again later")
    public void splitAccount(SplitAccountReqDTO splitAccountReqDTO) {
        payOrderService.splitAccount(splitAccountReqDTO);
    }

    @Override
    @Idempotent(timeout = 2, message = "Repeat refunding,please try again later")
    public void refund(RefundReqDTO refundReqDTO) {
        payOrderService.refund(refundReqDTO);
    }

    @Override
    @Idempotent(timeout = 2, message = "Giving red packets repeatedly,please try again later")
    public RedPacketApplyRspDTO redPacketApply(@Valid RedPacketApplyReqDTO redPacketApplyReqDTO) {
        return redPacketService.redPacketApply(redPacketApplyReqDTO);
    }
}
