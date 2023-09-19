package com.hisun.kugga.duke.user.cv.controller.app.resume.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.hisun.kugga.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "个人中心 - 个人简历信息 Excel 导出 Request VO", description = "参数和 ResumePageReqVO 是一致的")
@Data
public class ResumeExportReqVO {

    @ApiModelProperty(value = "用户编号")
    private Long resumeUserId;

    @ApiModelProperty(value = "个人简历内容")
    private String content;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}
