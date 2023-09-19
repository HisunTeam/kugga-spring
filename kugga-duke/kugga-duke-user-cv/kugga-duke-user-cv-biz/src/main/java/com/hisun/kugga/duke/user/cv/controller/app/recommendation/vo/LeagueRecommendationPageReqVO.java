package com.hisun.kugga.duke.user.cv.controller.app.recommendation.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: zhou_xiong
 */
@Data
public class LeagueRecommendationPageReqVO extends PageParam {
    @ApiModelProperty(value = "公会编号", required = true)
    @NotNull(message = "公会编号不能为空")
    private Long leagueId;
}
