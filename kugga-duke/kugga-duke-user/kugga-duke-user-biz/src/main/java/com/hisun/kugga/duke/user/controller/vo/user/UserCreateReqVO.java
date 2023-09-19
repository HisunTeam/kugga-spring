package com.hisun.kugga.duke.user.controller.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("用户信息创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserCreateReqVO extends UserBaseVO {

    @ApiModelProperty(value = "验证码", required = true)
    @NotNull(message = "Verification code cannot be empty")
    private String captcha;

    @ApiModelProperty(value = "确认密码", required = true)
    // @NotNull(message = "Confirm password cannot be empty")
    private String ensurePwd;

    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 场景类型 LOGIN、PAY
     */
//    private SecretTypeEnum type;

//    @ApiModelProperty(value = "密码", required = true)
//    @NotNull(message = "Password cannot be empty")
//    private String password;
}
