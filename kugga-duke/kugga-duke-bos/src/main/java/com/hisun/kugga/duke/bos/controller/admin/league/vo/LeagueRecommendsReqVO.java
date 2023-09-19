package com.hisun.kugga.duke.bos.controller.admin.league.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeagueRecommendsReqVO extends PageParam {
    @ApiModelProperty(value = "公会标签类型", required = true, example = "1")
    private String leagueLabel;

    @ApiModelProperty(value = "公会名称", example = "duke")
    private String leagueName;

    @ApiModelProperty(value = "公会名称", example = "duke")
    private Boolean authFlag;
}
