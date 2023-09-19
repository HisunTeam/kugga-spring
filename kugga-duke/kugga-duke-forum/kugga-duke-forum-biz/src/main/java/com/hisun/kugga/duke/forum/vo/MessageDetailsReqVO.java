package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zuocheng
 */
@ApiModel("消息详情 request VO")
@Data
public class MessageDetailsReqVO {
    @ApiModelProperty(value = "消息ID", required = true, example = "1")
    @NotNull(message = "messageId field cannot be empty")
    private Long messageId;
}
