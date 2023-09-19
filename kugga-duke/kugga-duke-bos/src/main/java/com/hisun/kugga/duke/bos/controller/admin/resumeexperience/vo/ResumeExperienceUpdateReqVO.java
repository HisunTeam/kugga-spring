package com.hisun.kugga.duke.bos.controller.admin.resumeexperience.vo;

import com.hisun.kugga.duke.bos.enums.CertFlagEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 个人简历经历更新 Request VO")
@Data
public class ResumeExperienceUpdateReqVO {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "审核状态  0认证中 1通过认证 2认证未通过")
    private CertFlagEnum certFlag;

    @ApiModelProperty(value = "审核意见")
    private String suggestion;

    @ApiModelProperty(value = "经历类型 0教育经历 1工作经历")
    private String type;

}
