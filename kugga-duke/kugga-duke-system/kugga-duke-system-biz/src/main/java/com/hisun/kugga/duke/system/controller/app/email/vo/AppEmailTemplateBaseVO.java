package com.hisun.kugga.duke.system.controller.app.email.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 邮件模板参数 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AppEmailTemplateBaseVO {

    @ApiModelProperty(value = "邮件场景，对应com.hisun.kugga.duke.system.enums.EmailScene", required = true)
    private String emailScene;

    @ApiModelProperty(value = "邮件类型【SIMPLE_MAIL：简单邮件、SIMPLE_HTML_MAIL：简单HTML邮件、ATTACHMENTS_MAIL：附件邮件，暂不支持、HTML_AND_IMAGE_MAIL：带图片的邮件，暂不支持】")
    private String emailType;

    @ApiModelProperty(value = "国际化标识")
    private String locale;

    @ApiModelProperty(value = "邮件主题")
    private String subject;

    @ApiModelProperty(value = "邮件模板，替换值以{}标识")
    private String template;

    @ApiModelProperty(value = "发送间隔，单位：秒")
    private Long sendInterval;

    @ApiModelProperty(value = "每日单个发送对象发送次数限制")
    private Integer sendLimit;

}
