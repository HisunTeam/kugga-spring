package com.hisun.kugga.duke.bos.controller.admin.resumeexperience.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 个人简历经历 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResumeExperienceRespVO extends ResumeExperienceBaseVO {


    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "简历名称")
    private String resumeName;


}
