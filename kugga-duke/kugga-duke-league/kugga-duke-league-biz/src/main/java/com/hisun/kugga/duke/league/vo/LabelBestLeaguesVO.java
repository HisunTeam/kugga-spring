package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zuocheng
 */
@ApiModel("标签推荐公会列表 Response VO")
@Data
public class LabelBestLeaguesVO extends LeagueLabelVO {

    @ApiModelProperty(value = "公会列表", required = true, example = "1")
    private List<LeagueVO> leagues;
}
