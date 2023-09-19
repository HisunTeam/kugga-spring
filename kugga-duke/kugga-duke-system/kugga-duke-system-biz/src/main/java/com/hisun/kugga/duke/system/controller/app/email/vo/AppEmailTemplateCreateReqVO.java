package com.hisun.kugga.duke.system.controller.app.email.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("用户 APP - 邮件模板参数创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppEmailTemplateCreateReqVO extends AppEmailTemplateBaseVO {

}
