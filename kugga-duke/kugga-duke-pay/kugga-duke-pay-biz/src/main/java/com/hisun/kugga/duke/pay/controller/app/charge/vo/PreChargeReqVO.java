package com.hisun.kugga.duke.pay.controller.app.charge.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
@ApiModel("预充值 Request VO")
@Data
public class PreChargeReqVO {
    /**
     * 充值金额，单位：元
     */
    @ApiModelProperty(value = "充值金额", required = true)
    @NotNull(message = "amount cannot be empty")
    @DecimalMin(value = "1", message = "The minimum top-up amount is $1")
    private BigDecimal amount;
}
