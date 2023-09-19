package com.hisun.kugga.duke.pay.api.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
@Data
public class OrderCreateRspDTO {
    /**
     * 内部订单号
     */
    private String appOrderNo;
    /**
     * 钱包（外部）订单号
     */
    private String walletOrderNo;
    /**
     * 手续费 单位：元
     */
    private BigDecimal fee;
}
