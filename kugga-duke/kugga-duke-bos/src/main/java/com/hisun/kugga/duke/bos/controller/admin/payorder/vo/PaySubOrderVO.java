package com.hisun.kugga.duke.bos.controller.admin.payorder.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("管理后台 - 支付订单 Response VO")
@Data
public class PaySubOrderVO {

    @ApiModelProperty(value = "子订单号")
    private Long id;

    @ApiModelProperty(value = "内部订单号")
    private String appOrderNo;

    @ApiModelProperty(value = "订单类型")
    private String orderType;

    @ApiModelProperty(value = "支付方式【balance：余额支付，paypal：paypal支付】")
    private String payChannel;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "交易状态")
    private String status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
