package com.hisun.kugga.duke.pay.controller.app.useraccount.vo;

import com.hisun.kugga.duke.entity.VerifyBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: zhou_xiong
 */
@ApiModel("设置支付密码 Request VO")
@Data
public class SetPayPasswordReqVO extends VerifyBaseVo {
    @ApiModelProperty(value = "6位数字支付密码", required = true)
    @NotBlank(message = "payPassword cannot be empty")
//    @Pattern(regexp = "^\\d{6}$", message = "Please enter a 6-digit password")
    private String payPassword;

//    @ApiModelProperty(value = "确认密码", required = true)
////    @NotBlank(message = "rePayPassword cannot be empty")
////    @Pattern(regexp = "^\\d{6}$", message = "Please enter a 6-digit password")
//    private String rePayPassword;

    @ApiModelProperty(value = "验证码", required = true)
    @NotBlank(message = "verifyCode cannot be empty")
    private String verifyCode;
}
