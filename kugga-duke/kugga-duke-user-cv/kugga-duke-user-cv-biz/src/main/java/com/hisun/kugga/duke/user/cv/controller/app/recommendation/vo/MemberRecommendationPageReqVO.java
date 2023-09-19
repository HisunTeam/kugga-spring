package com.hisun.kugga.duke.user.cv.controller.app.recommendation.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: zhou_xiong
 */
@ApiModel("个人首页 - 推荐报告 Request VO")
@Data
public class MemberRecommendationPageReqVO extends PageParam {
    @ApiModelProperty(value = "用户编号", required = true)
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @ApiModelProperty(value = "推荐报告编号，此推荐报告置顶，如果没传则无置顶报告")
    private Long recommendationId;
}
