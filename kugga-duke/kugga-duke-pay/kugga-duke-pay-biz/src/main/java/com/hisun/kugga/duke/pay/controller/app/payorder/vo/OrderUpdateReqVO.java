package com.hisun.kugga.duke.pay.controller.app.payorder.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 订单更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderUpdateReqVO extends OrderBaseVO {

    @ApiModelProperty(value = "交易id", required = true)
    @NotNull(message = "交易id不能为空")
    private Long id;

}
