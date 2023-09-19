package com.hisun.kugga.duke.system.controller.app.email.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("用户 APP - 邮件模板参数更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppEmailTemplateUpdateReqVO extends AppEmailTemplateBaseVO {

    @ApiModelProperty(value = "邮件模板ID", required = true)
    private Long id;

}
