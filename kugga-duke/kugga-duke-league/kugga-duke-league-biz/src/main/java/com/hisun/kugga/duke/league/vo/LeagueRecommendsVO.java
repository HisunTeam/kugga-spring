package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公会详情
 *
 * @author zuocheng
 */
@ApiModel("公会详情 VO")
@Data
public class LeagueRecommendsVO extends LeagueVO {
    @ApiModelProperty(value = "此公会是否已被当前用户收藏", example = "true")
    private Boolean favoriteFlag;
}
