package com.hisun.kugga.duke.user.controller.vo.auth;

import com.hisun.kugga.duke.enums.email.EmailScene;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author Lin
 */
@ApiModel("用户 APP - 发送手机验证码 Request VO")
@Data
@Accessors(chain = true)
public class AppAuthEmailSendReqVO {

    @ApiModelProperty(value = "邮箱", required = true, example = "1073920692@qq.com")
    @NotNull(message = "Please enter a valid email address")
    @Email(message = "Incorrect mailbox format")
    private String email;

    /**
     * {@link EmailScene}
     * register 注册
     * reset    重置密码
     */
    @ApiModelProperty(value = "发送场景", example = "USER_REGISTER", notes = "对应 EmailScene 枚举")
    @NotNull(message = "Scene cannot be empty")
    private EmailScene emailScene;

}
