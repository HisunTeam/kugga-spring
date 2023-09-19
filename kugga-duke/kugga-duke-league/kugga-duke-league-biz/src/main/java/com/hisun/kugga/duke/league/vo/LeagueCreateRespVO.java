package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zuocheng
 */
@ApiModel("创建公会  Response VO")
@Data
public class LeagueCreateRespVO {
    @ApiModelProperty(value = "公会编号", example = "001")
    private Long leagueId;

    @ApiModelProperty(value = "公会激活状态", example = "true:激活 false:未激活")
    private Boolean activationFlag;
}
