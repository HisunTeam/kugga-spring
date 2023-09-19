package com.hisun.kugga.duke.system.controller.app.email.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 邮件发送流水 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class EmailSendJournalBaseVO {

    @ApiModelProperty(value = "邮件模板ID")
    private Long templateId;

    @ApiModelProperty(value = "发送对象")
    private String receiver;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "发送成功与否【success、failed】")
    private String result;

}
