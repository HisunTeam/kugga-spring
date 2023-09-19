package com.hisun.kugga.duke.user.cv.controller.app.resume.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

import static com.hisun.kugga.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH;


/**
 * @author admin
 */
@ApiModel("简历信息 校验是否为空vo")
@Data
public class ResumeValidateVO {

    @ApiModelProperty(value = "自我介绍")
    private String introduce;

    // 个人信息
    @ApiModelProperty(value = "姓名")
    private String resumeName;

    @ApiModelProperty(value = "性别")
    private String sex;

//    @ApiModelProperty(value = "简历头像")
//    private String resumeAvatar;

    @ApiModelProperty(value = "出生年月")
    private Date birthday;

    @ApiModelProperty(value = "学历")
    private String degree;

    @ApiModelProperty(value = "手机号")
    private String phoneNum;

    @ApiModelProperty(value = "邮箱")
    private String email;

//    @ApiModelProperty(value = "学习经历")
//    private List<StudyExperience> studyExperience;
//
//    @ApiModelProperty(value = "工作经历")
//    private List<WorkExperience> workExperience;

    @ApiModelProperty(value = "职业技能")
    private String skills;

    @Data
    public static class StudyExperience {
        @ApiModelProperty(value = "学校")
        private String school;
        @ApiModelProperty(value = "学历")
        private String degree;
        @ApiModelProperty(value = "开始时间")
        private Date beginTime;
        @ApiModelProperty(value = "结束时间")
        private Date endTime;
        @ApiModelProperty(value = "主修")
        private String major;
    }
    @Data
    public static class WorkExperience {
        @ApiModelProperty(value = "公司")
        private String company;
        @ApiModelProperty(value = "职位")
        private String position;
        @ApiModelProperty(value = "开始时间")
        private Date beginTime;
        @ApiModelProperty(value = "结束时间")
        private Date endTime;
        @ApiModelProperty(value = "经历描述")
        private String description;
    }


}
