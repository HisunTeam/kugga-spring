package com.hisun.kugga.duke.user.controller.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel("管理后台 - 用户信息 Response VO")
@Data
@ToString(callSuper = true)
public class UserInfoRespVO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户名 99duke*")
    private String username;

    @ApiModelProperty(value = "用户昵称", required = true)
    private String nickname;

    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

//    @ApiModelProperty(value = "密码", required = true)
//    private String password;

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
    private String firstName;

    @ApiModelProperty(value = "是否设置支付密码，true：已设置，false：未设置")
    private Boolean payPasswordFlag;
}
