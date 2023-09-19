package com.hisun.kugga.duke.system.controller.app.email.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: zhou_xiong
 */
@ApiModel("注册发送邮件验证码 Request VO")
@Data
public class RegisterEmailReqVO {
    @ApiModelProperty(value = "发送对象", required = true)
    @NotNull(message = "发送对象不能为空")
    private String to;
}
