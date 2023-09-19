package com.hisun.kugga.duke.pay.controller.app.withdraw.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
@Data
@ApiModel("提现至PayPal Response VO")
public class WithdrawToPayPalRspVO {
    @ApiModelProperty(value = "提现订单号")
    private String orderNo;

    @ApiModelProperty(value = "手续费")
    private BigDecimal fee;
}
