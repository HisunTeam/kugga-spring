package com.hisun.kugga.duke.pay.controller.app.withdraw.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
@Data
@ApiModel("提现至PayPal Request VO")
public class WithdrawToPayPalReqVO {
    @ApiModelProperty(value = "PayPal账号", required = true)
    @NotEmpty(message = "payPalNo cannot be empty")
    @Email(message = "PayPal Account is incorrect")
    private String payPalNo;

    @ApiModelProperty(value = "确认PayPal账号", required = true)
    @NotEmpty(message = "rePayPalNo cannot be empty")
    private String rePayPalNo;

    @ApiModelProperty(value = "提现金额", required = true)
    @NotNull(message = "amount cannot be empty")
    @DecimalMin(value = "1", message = "The minimum withdrawal amount is $1")
    private BigDecimal amount;

    @ApiModelProperty(value = "支付密码", required = true)
    @NotEmpty(message = "payPassword cannot be empty")
    private String payPassword;

    @ApiModelProperty(value = "公钥", required = true)
    @NotEmpty(message = "publicKey cannot be empty")
    private String publicKey;
}
