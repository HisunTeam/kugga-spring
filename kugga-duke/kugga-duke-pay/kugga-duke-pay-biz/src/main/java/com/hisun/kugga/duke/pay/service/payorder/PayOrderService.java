package com.hisun.kugga.duke.pay.service.payorder;

import com.hisun.kugga.duke.pay.api.order.dto.*;

/**
 * 订单 Service 接口
 *
 * @author zhou_xiong
 */
public interface PayOrderService {

    /**
     * 创建订单
     *
     * @param orderCreateReqDTO
     * @return
     */
    OrderCreateRspDTO createOrder(OrderCreateReqDTO orderCreateReqDTO);

    /**
     * 支付
     *
     * @param payReqDTO
     */
    void pay(PayReqDTO payReqDTO);

    /**
     * 分账
     *
     * @param splitAccountReqDTO
     */
    void splitAccount(SplitAccountReqDTO splitAccountReqDTO);

    /**
     * 退款
     *
     * @param refundReqDTO
     */
    void refund(RefundReqDTO refundReqDTO);

    /**
     * 取消支付订单
     *
     * @param appOrderNo
     */
    void cancel(String appOrderNo);
}
