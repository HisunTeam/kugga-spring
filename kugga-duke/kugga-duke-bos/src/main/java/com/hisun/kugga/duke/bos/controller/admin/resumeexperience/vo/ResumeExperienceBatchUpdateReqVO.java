package com.hisun.kugga.duke.bos.controller.admin.resumeexperience.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel("管理后台 - 个人简历经历更新 Request VO")
@Data
public class ResumeExperienceBatchUpdateReqVO {

    @ApiModelProperty(value = "批量选中项", required = true)
    @NotNull(message = "未传入参数")
    private List<ResumeExperienceUpdateReqVO> multipleSelection;

}
