package com.hisun.kugga.duke.system.controller.app.email.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@ApiModel("用户 APP - 邮件模板参数 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppEmailTemplateRespVO extends AppEmailTemplateBaseVO {

    @ApiModelProperty(value = "邮件模板ID", required = true)
    private Long id;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
