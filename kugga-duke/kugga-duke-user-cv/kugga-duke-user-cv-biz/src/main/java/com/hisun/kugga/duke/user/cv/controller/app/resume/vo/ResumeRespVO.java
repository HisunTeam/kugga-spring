package com.hisun.kugga.duke.user.cv.controller.app.resume.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("个人中心 - 个人简历信息 Response VO")
@Data
public class ResumeRespVO extends ResumeBaseVO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名")
    private String lastName;

    @ApiModelProperty(value = "姓")
    private String firstName;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "是否属于同公会")
    private Boolean isSameLeague;


}
