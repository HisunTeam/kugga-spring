package com.hisun.kugga.duke.bos.controller.admin.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 公会详情
 *
 * @author zuocheng
 */
@ApiModel("公会详情 VO")
@Data
public class LeagueUpdateBatchVO {

    @ApiModelProperty(value = "批量选中项", required = true, example = "1")
    private List<LeagueRecommendsUpdateVO> multipleSelection;

}
