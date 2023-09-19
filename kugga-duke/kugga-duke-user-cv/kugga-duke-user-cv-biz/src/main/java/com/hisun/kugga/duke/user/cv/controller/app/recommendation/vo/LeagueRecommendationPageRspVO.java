package com.hisun.kugga.duke.user.cv.controller.app.recommendation.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class LeagueRecommendationPageRspVO extends RecommendationPageRspBaseVO {
    /**
     * 名
     */
    @ApiModelProperty(value = "名")
    private String lastName;
    /**
     * 姓
     */
    @ApiModelProperty(value = "姓")
    private String firstName;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;
    /**
     * 被推荐人编号
     */
    @ApiModelProperty(value = "被推荐人编号")
    private Long recommendedId;
    /**
     * 收藏标识
     */
    @ApiModelProperty(value = "收藏标识，true-已收藏，false-未收藏")
    private Boolean favoriteFlag;
    /**
     * 分享链接
     */
    @ApiModelProperty(value = "分享链接，都是null，需要前端生成")
    private String shareLink;
}
