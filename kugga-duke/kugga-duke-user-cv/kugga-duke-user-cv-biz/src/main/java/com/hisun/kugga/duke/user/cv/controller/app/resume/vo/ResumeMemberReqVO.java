package com.hisun.kugga.duke.user.cv.controller.app.resume.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@ApiModel("同公会成员-获取个人简历信息 Request VO")
@Data
public class ResumeMemberReqVO {

    @ApiModelProperty(value = "用户编号", required = true)
    @NotNull(message = "The user id can't be empty")
    private Long resumeUserId;

    @ApiModelProperty(value = "公会编号")
    private Long leagueId;

}
