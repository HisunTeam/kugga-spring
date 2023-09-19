package com.hisun.kugga.duke.user.controller.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * 用户信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class UserBaseVO {

    @ApiModelProperty(value = "用户名 99duke*")
    private String username;

    @ApiModelProperty(value = "用户昵称", required = true)
    private String nickname;

    @ApiModelProperty(value = "邮箱", required = true)
    @NotNull(message = "Please enter a valid email address")
    @Email(message = "Incorrect mailbox format")
    private String email;


    @ApiModelProperty(value = "密码", required = true)
    @NotNull(message = "Password cannot be empty")
    private String password;

    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * lastName 姓  姓：选填    名：必填
     */
    @ApiModelProperty(value = "姓", required = true, example = "史蒂夫")
    private String lastName;
    /**
     * firstName 名
     */
    @ApiModelProperty(value = "名", required = true, example = "乔布斯")
    @NotNull(message = "Name cannot be empty")
    private String firstName;

}
