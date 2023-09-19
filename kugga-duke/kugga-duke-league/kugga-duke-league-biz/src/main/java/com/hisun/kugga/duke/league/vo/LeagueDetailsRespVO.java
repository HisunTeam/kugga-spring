package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zuocheng
 */
@ApiModel("公会详情  Response VO")
@Data
public class LeagueDetailsRespVO extends LeagueVO {
    @ApiModelProperty(value = "用户在公会的级别(用户是当前公会成员时,则存在值)", required = true, example = " 0:超级管理员 1:管理员 2:成员  其它:非公会成员")
    private Integer userLevel;

    @ApiModelProperty(value = "当前用户是否为公会成员", required = true, example = " true:是, false:否")
    private Boolean memberFlag;

    @ApiModelProperty(value = "是否已收藏", example = " true:是, false:否")
    private Boolean favoriteFlag;

    @ApiModelProperty(value = "是否允许用户加入")
    private Boolean enabledUserJoin;
    @ApiModelProperty(value = "是否需要管理员审批")
    private Boolean enabledAdminApproval;
    @ApiModelProperty(value = "用户加入价格")
    private BigDecimal userJoinPrice;

}
