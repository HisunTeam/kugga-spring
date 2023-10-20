package com.hisun.kugga.duke.batch.service;

import com.hisun.kugga.duke.batch.dto.RefundReqDTO;

/**
 * Order Service Interface
 *
 * @author zuo_cheng
 */
public interface PayOrderService {
    /**
     * Refund
     *
     * @param refundReqDTO
     */
    void refund(RefundReqDTO refundReqDTO);
}
