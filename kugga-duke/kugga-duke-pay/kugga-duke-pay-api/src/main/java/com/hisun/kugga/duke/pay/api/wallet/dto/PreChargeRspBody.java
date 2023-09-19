package com.hisun.kugga.duke.pay.api.wallet.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class PreChargeRspBody {
    /**
     * 钱包订单号
     */
    private String orderNo;
    /**
     * paypal充值跳转url
     */
    private String paypalPrepayUrl;
    /**
     * 手续费
     */
    private Integer fee;
}
