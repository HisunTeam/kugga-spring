package com.hisun.kugga.duke.user.controller.vo.favorite;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 收藏创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FavoriteCreateReqVO extends FavoriteBaseVO {

    @ApiModelProperty(value = "写推荐报告所在公会id", required = false)
    private Long recommendationLeagueId;

    @ApiModelProperty(value = "收藏推荐报告时所在公会id", required = false)
    private Long favoriteLeagueId;
}
