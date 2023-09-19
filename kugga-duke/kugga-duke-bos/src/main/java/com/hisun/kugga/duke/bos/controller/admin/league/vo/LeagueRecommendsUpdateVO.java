package com.hisun.kugga.duke.bos.controller.admin.league.vo;


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
public class LeagueRecommendsUpdateVO {

    @ApiModelProperty(value = "公会编号", required = true, example = "1")
    private Long leagueId;

    @ApiModelProperty(value = "排序编号", required = true, example = "1")
    private Integer sortId;

    @ApiModelProperty(value = "公会标签", example = "1")
    private String leagueLabel;

    private LocalDateTime createTime;

}
