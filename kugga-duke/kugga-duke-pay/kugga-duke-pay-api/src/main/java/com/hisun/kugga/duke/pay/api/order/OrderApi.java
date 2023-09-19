package com.hisun.kugga.duke.pay.api.order;

import com.hisun.kugga.duke.pay.api.order.dto.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * @author: zhou_xiong
 */
@Validated
public interface OrderApi {
    /**
     * 下单
     *
     * @param orderCreateReqDTO
     * @return
     */
    OrderCreateRspDTO createOrder(@Valid OrderCreateReqDTO orderCreateReqDTO);

    /**
     * 支付
     *
     * @param payReqDTO
     */
    void pay(@Valid PayReqDTO payReqDTO);

    /**
     * 分账
     */
    void splitAccount(@Valid SplitAccountReqDTO splitAccountReqDTO);

    /**
     * 退款
     *
     * @param refundReqDTO
     */
    void refund(@Valid RefundReqDTO refundReqDTO);

    /**
     * 申请发红包
     *
     * @param redPacketApplyReqDTO
     * @return
     */
    RedPacketApplyRspDTO redPacketApply(@Valid RedPacketApplyReqDTO redPacketApplyReqDTO);

}
