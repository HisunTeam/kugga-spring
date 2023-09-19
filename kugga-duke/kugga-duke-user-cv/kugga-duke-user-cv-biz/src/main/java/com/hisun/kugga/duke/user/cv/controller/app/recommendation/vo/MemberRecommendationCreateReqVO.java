package com.hisun.kugga.duke.user.cv.controller.app.recommendation.vo;

import com.hisun.kugga.duke.entity.Content;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel("推荐报告创建 Request VO")
@Data
public class MemberRecommendationCreateReqVO {
    @ApiModelProperty(value = "被推荐人编号", required = true)
    @NotNull(message = "The recommendedId cannot be empty")
    private Long recommendedId;

    @ApiModelProperty(value = "推荐人公会编号", required = true)
    @NotNull(message = "The recommenderLeagueId cannot be empty")
    private Long recommenderLeagueId;

    @ApiModelProperty(value = "推荐报告内容", required = true)
    @NotEmpty(message = "The content of the recommendation cannot be empty")
    private List<Content> content;
}
