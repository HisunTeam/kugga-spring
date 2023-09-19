package com.hisun.kugga.duke.user.controller.vo.favorite;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@ApiModel("收藏认证VO")
@Data
@ToString(callSuper = true)
public class FavoriteLeagueAuthVO {

    @ApiModelProperty(value = "收藏id")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "工会id")
    private Long contentId;

    @ApiModelProperty(value = "收藏类型 G-工会 T-推荐报告")
    private String avatar;

    @ApiModelProperty(value = "公会名称")
    private String leagueName;

    @ApiModelProperty(value = "公会认证价格")
    private BigDecimal leagueAuthPrice;

    @ApiModelProperty(value = "内容")
    private String content;


}
