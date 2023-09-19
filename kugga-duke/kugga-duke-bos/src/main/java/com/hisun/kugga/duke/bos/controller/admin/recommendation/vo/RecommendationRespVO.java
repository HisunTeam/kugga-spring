package com.hisun.kugga.duke.bos.controller.admin.recommendation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 公会详情
 *
 * @author zuocheng
 */
@ApiModel("公会详情 VO")
@Data
public class RecommendationRespVO {


    @ApiModelProperty(value = "用户名", required = true, example = "99duke56")
    private String username;

    @ApiModelProperty(value = "姓", example = "99")
    private String lastName;

    @ApiModelProperty(value = "名", example = "100")
    private String firstName;

    @ApiModelProperty(value = "被推荐用户的用户名", example = "1")
    private String getUsername;

    @ApiModelProperty(value = "被推荐用户的姓", example = "1")
    private String getLastName;

    @ApiModelProperty(value = "被推荐用户的名", example = "1")
    private String getFirstName;

    @ApiModelProperty(value = "编写时所在公会", example = "1")
    private String leagueName;

    @ApiModelProperty(value = "用户邮箱", example = "1")
    private String email;

    @ApiModelProperty(value = "创建时间", example = "2022-08-22 14:59:37")
    private LocalDateTime createTime;

}
