package com.hisun.kugga.duke.system.controller.app.email.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@ApiModel("管理后台 - 邮件发送流水 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmailSendJournalRespVO extends EmailSendJournalBaseVO {

    @ApiModelProperty(value = "发送邮件流水ID", required = true)
    private Long id;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
