package com.hisun.kugga.duke.pay.controller.app.useraccount.vo;

import com.hisun.kugga.duke.entity.VerifyBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: zhou_xiong
 */
@ApiModel("修改支付密码 Request VO")
@Data
public class UpdatePayPasswordReqVO extends VerifyBaseVo {
    @ApiModelProperty(value = "6位数字旧密码", required = true)
    @NotBlank(message = "oldPayPassword cannot be empty")
//    @Pattern(regexp = "^\\d{6}$", message = "Please enter a 6-digit password")
    private String oldPayPassword;

    @ApiModelProperty(value = "6位数字新密码", required = true)
    @NotBlank(message = "newPayPassword cannot be empty")
//    @Pattern(regexp = "^\\d{6}$", message = "Please enter a 6-digit password")
    private String newPayPassword;

    @ApiModelProperty(value = "邮箱验证码", required = true)
    @NotBlank(message = "verifyCode cannot be empty")
    private String verifyCode;
}
