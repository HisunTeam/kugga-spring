package com.hisun.kugga.duke.pay.controller.app.charge.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
@ApiModel("预充值 Response VO")
@Data
public class PreChargeRspVO {
    /**
     * 钱包订单号
     */
    @ApiModelProperty(value = "钱包订单号")
    private String orderNo;
    /**
     * paypal充值跳转url
     */
    @ApiModelProperty(value = "paypal充值跳转url")
    private String paypalPrepayUrl;

    @ApiModelProperty(value = "手续费")
    private BigDecimal fee;
}
