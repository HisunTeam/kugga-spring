package com.hisun.kugga.duke.user.cv.controller.app.resume.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

import static com.hisun.kugga.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH;

/**
 * 个人简历信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ResumeBaseVO {


//    @ApiModelProperty(value = "个人简历内容")
//    private List<Content> content;

    @ApiModelProperty(value = "自我介绍")
    private String introduce;

    @ApiModelProperty(value = "姓名")
    private String resumeName;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "简历头像")
    private String resumeAvatar;

    @ApiModelProperty(value = "出生年月")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH)
    private Date birthday;

    @ApiModelProperty(value = "学历")
    private String degree;

    @ApiModelProperty(value = "手机号")
    private String phoneNum;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "学习经历")
    private List<Experience> studyExperience;

    @ApiModelProperty(value = "工作经历")
    private List<Experience> workExperience;

    @ApiModelProperty(value = "职业技能")
    private String skills;

    @ApiModelProperty(value = "隐藏标识1-隐藏 0-展示")
    private String hide;

    @Data
    public static class Experience {
        @ApiModelProperty(value = "id")
        private Long id;
        @ApiModelProperty(value = "学校")
        private String school;
        @ApiModelProperty(value = "开始时间")
        @DateTimeFormat(pattern = FORMAT_YEAR_MONTH)
        @JsonFormat(pattern = FORMAT_YEAR_MONTH)
        private Date beginTime;
        @ApiModelProperty(value = "结束时间")
        @DateTimeFormat(pattern = FORMAT_YEAR_MONTH)
        @JsonFormat(pattern = FORMAT_YEAR_MONTH)
        private Date endTime;
        @ApiModelProperty(value = "学历")
        private String degree;
        @ApiModelProperty(value = "主修")
        private String major;
        @ApiModelProperty(value = "公司")
        private String company;
        @ApiModelProperty(value = "职位")
        private String position;
        @ApiModelProperty(value = "经历描述")
        private String description;
        @ApiModelProperty(value = "认证标识")
        private String certFlag;
        @ApiModelProperty(value = "认证图片")
        private List<String> certification;
        @ApiModelProperty(value = "经历备注")
        private String remark;
        @ApiModelProperty(value = "审核意见")
        private String suggestion;
    }


}
