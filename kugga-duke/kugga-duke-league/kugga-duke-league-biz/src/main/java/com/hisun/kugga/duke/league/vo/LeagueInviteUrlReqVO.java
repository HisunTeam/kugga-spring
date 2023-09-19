package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zuocheng
 */
@ApiModel("获取公会邀请链接 Request VO")
@Data
public class LeagueInviteUrlReqVO {
    @ApiModelProperty(value = "公会ID", required = true, example = "001")
    @NotNull(message = "公会ID不能为空")
    private Long leagueId;

    @ApiModelProperty(value = "域名", required = true, example = "http://localhost:18081")
    @NotNull(message = "域名不能为空")
    private String url;
}
