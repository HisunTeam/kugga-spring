package com.hisun.kugga.duke.system.controller.app.message.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("消息更新 Request VO")
@Data
@ToString(callSuper = true)
public class MessagesUpdateReqVO {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id cannot be empty")
    private Long id;

    /**
     * 任务id
     */
    private Long taskId;

}
