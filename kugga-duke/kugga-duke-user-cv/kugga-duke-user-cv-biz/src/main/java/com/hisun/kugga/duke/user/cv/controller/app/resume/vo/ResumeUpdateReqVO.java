package com.hisun.kugga.duke.user.cv.controller.app.resume.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("个人中心 - 个人简历信息更新 Request VO")
@Data
public class ResumeUpdateReqVO extends ResumeBaseVO {

    @ApiModelProperty(value = "id", required = true)
    private Long id;

    private Long userId;

}
