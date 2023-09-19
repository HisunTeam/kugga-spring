package com.hisun.kugga.duke.batch.service;

import com.hisun.kugga.duke.batch.dto.RefundReqDTO;

/**
 * 订单 Service 接口
 *
 * @author zuo_cheng
 */
public interface PayOrderService {
    /**
     * 退款
     *
     * @param refundReqDTO
     */
    void refund(RefundReqDTO refundReqDTO);
}
