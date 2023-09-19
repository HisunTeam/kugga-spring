package com.hisun.kugga.duke.user.cv.controller.app.resume.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;


@ApiModel("个人中心 - 个人简历信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResumePageReqVO extends PageParam {

    @ApiModelProperty(value = "用户编号", required = true)
    @NotNull(message = "The user id can't be empty")
    private Long resumeUserId;

    @ApiModelProperty(value = "个人简历内容")
    private String content;

}
