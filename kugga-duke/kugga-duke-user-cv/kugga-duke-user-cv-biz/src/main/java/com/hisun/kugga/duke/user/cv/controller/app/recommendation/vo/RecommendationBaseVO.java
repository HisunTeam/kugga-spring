package com.hisun.kugga.duke.user.cv.controller.app.recommendation.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 推荐报告 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class RecommendationBaseVO {

    @ApiModelProperty(value = "推荐人编号")
    private Long recommenderId;

    @ApiModelProperty(value = "被推荐人编号")
    private Long recommendedId;

    @ApiModelProperty(value = "推荐人公会编号")
    private Long recommenderLeagueId;

    @ApiModelProperty(value = "推荐信内容")
    private String content;

    @ApiModelProperty(value = "分享链接")
    private String shareLink;

}
