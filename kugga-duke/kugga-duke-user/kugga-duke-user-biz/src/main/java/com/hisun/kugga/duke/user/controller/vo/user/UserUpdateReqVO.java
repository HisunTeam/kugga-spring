package com.hisun.kugga.duke.user.controller.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;

@ApiModel("管理后台 - 用户信息更新 Request VO")
@Data
@ToString(callSuper = true)
public class UserUpdateReqVO {

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "用户昵称", required = false)
//    @NotNull(message = "用户昵称不能为空")
    private String nickname;

    @ApiModelProperty(value = "邮箱", required = false)
    @Email
    private String email;
    /**
     * lastName 姓  姓：选填    名：必填
     */
    @ApiModelProperty(value = "姓", required = false, example = "史蒂夫")
    private String lastName;
    /**
     * firstName 名
     */
    @ApiModelProperty(value = "名", required = false, example = "乔布斯")
//    @NotNull(message = "名不能为空")
    private String firstName;

}
