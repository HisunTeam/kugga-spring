package com.hisun.kugga.duke.bos.controller.admin.resumeexperience.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hisun.kugga.duke.bos.enums.CertFlagEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

import static com.hisun.kugga.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH;

/**
 * 个人简历经历 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
@ApiModel("管理后台 - 个人简历经历 Request VO")
public class ResumeExperienceBaseVO {

    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH)
    private Date beginTime;

    @ApiModelProperty(value = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH)
    private Date endTime;

    @ApiModelProperty(value = "学校")
    private String school;

    @ApiModelProperty(value = "学历")
    private String degree;

    @ApiModelProperty(value = "主修")
    private String major;

    @ApiModelProperty(value = "公司")
    private String company;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "附件描述")
    private String remark;

    @ApiModelProperty(value = "审核状态  0认证中 1通过认证 2认证未通过")
    private CertFlagEnum certFlag;

    @ApiModelProperty(value = "审核意见")
    private String suggestion;

    @ApiModelProperty(value = "审核图片")
    private List<String> certification;

    @ApiModelProperty(value = "经历类型 0教育经历 1工作经历")
    private String type;

}
