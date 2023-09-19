package com.hisun.kugga.duke.bos.controller.admin.resumeexperience.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 个人简历经历分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResumeExperiencePageReqVO extends PageParam {


    @ApiModelProperty(value = "学校")
    private String school;

    @ApiModelProperty(value = "简历名字")
    private String resumeName;

    @ApiModelProperty(value = "认证状态")
    private String certFlag;

    @ApiModelProperty(value = "公司")
    private String company;

    @ApiModelProperty(value = "经历类型 0 教育经历 1 工作经历")
    private String type;

}
