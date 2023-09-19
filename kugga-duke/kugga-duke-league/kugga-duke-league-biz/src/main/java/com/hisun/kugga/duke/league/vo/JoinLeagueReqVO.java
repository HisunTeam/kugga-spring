package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zuocheng
 */
@ApiModel("加入公会 Request VO")
@Data
public class JoinLeagueReqVO {
    @ApiModelProperty(value = "公会ID", required = true, example = "001")
    @NotNull(message = "公会ID不能为空")
    private Long leagueId;

    @ApiModelProperty(value = "邀请人ID", required = true, example = "01")
    @NotNull(message = "邀请人不能为空")
    private Long inviteUserId;
}
