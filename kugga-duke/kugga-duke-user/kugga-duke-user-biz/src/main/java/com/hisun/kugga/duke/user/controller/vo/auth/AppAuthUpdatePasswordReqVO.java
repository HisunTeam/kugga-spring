package com.hisun.kugga.duke.user.controller.vo.auth;

import com.hisun.kugga.duke.entity.VerifyBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// TODO 芋艿：code review 相关逻辑
@ApiModel("用户 APP - 修改密码 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppAuthUpdatePasswordReqVO extends VerifyBaseVo {

    @ApiModelProperty(value = "用户旧密码", required = true, example = "123456")
    @NotBlank(message = "Old password cannot be empty")
    private String oldPassword;

    @ApiModelProperty(value = "密码", required = true, example = "buzhidao")
    @NotNull(message = "Password cannot be empty")
    private String password;

    @ApiModelProperty(value = "确认密码", required = true)
    private String ensurePwd;
}
