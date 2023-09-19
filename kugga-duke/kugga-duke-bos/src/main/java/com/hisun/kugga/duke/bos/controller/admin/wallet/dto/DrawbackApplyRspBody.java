package com.hisun.kugga.duke.bos.controller.admin.wallet.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class DrawbackApplyRspBody {
    /**
     * 退款状态
     */
    private String drawbackStatus;
    /**
     * 钱包订单号
     */
    private String orderNo;
}
