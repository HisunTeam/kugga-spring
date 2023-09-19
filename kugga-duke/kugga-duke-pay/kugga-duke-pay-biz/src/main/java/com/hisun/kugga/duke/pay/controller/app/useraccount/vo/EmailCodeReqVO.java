package com.hisun.kugga.duke.pay.controller.app.useraccount.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author: zhou_xiong
 */
@ApiModel("修改/重置支付密码发送邮箱验证码 Request VO")
@Data
public class EmailCodeReqVO {
    @ApiModelProperty(value = "验证码类型【1：设置支付密码，2：修改支付密码，3：重置支付密码】", required = true)
    @NotEmpty(message = "emailType cannot be empty")
    @Pattern(regexp = "[1-3]", message = "emailType is incorrect")
    private String emailType;
}
