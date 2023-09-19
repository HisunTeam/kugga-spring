package com.hisun.kugga.duke.user.cv.controller.app.recommendation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.hisun.kugga.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "用户 APP - 推荐报告 Excel 导出 Request VO", description = "参数和 DukeRecommendationPageReqVO 是一致的")
@Data
public class RecommendationExportReqVO {

    @ApiModelProperty(value = "推荐人编号")
    private Long recommenderId;

    @ApiModelProperty(value = "被推荐人编号")
    private Long recommendedId;

    @ApiModelProperty(value = "推荐信内容")
    private String content;

    @ApiModelProperty(value = "分享链接")
    private String shareLink;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}
