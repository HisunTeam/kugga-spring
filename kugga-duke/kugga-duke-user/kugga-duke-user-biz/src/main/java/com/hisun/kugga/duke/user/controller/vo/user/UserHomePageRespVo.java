package com.hisun.kugga.duke.user.controller.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: 个人主页返回vo
 * @author： Lin
 * @Date 2022/7/27 15:21
 */
@ApiModel("用户主页vo")
@Data
@ToString(callSuper = true)
public class UserHomePageRespVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户名 99duke*")
    private String username;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "姓", required = true, example = "史蒂夫")
    private String lastName;

    @ApiModelProperty(value = "名", required = true, example = "乔布斯")
    private String firstName;

    //个人简历

    //推荐报告

}
