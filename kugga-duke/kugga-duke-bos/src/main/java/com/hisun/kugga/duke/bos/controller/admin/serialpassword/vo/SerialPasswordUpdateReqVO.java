package com.hisun.kugga.duke.bos.controller.admin.serialpassword.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 序列密码信息更新 Request VO")
@Data
public class SerialPasswordUpdateReqVO {

    @NotNull(message = "序列密码不能为空")
    @ApiModelProperty(value = "序列密码", required = true)
    private String serialPassword;

}
