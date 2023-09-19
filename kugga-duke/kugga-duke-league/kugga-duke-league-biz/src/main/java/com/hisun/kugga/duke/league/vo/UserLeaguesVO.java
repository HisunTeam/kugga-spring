package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author zuocheng
 */
@ApiModel("用户公会列表 VO")
@Data
public class UserLeaguesVO extends LeagueVO {
    @ApiModelProperty(value = "用户在公会的级别(用户是当前公会成员时,则存在值)", required = true, example = " 0:超级管理员 1:管理员 2:成员  其它:非公会成员")
    private Integer userLevel;
    @ApiModelProperty(value = "用户公会等级")
    private Integer growthLevel;
}
