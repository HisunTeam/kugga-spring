package com.hisun.kugga.duke.user.cv.controller.app.recommendation.vo;

import com.hisun.kugga.duke.entity.Content;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhou_xiong
 */
@Data
public class RecommendationPageRspBaseVO {
    /**
     * 推荐报告ID
     */
    @ApiModelProperty(value = "推荐报告编号，点击分享链接进入个人主页时的置顶推荐报告")
    private Long recommendationId;
    /**
     * 推荐报告内容
     */
    @ApiModelProperty(value = "推荐报告内容")
    private List<Content> content;
}
