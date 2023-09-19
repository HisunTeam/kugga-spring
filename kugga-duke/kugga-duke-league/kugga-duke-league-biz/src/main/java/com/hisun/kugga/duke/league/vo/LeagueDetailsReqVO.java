package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zuocheng
 */
@ApiModel("公会详情 Request VO")
@Data
public class LeagueDetailsReqVO {
    @ApiModelProperty(value = "公会ID", required = true, example = "001")
    @NotNull(message = "公会ID不能为空")
    private Long leagueId;
}
