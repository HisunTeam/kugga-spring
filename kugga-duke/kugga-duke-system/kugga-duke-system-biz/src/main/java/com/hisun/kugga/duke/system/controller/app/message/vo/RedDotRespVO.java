package com.hisun.kugga.duke.system.controller.app.message.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhou_xiong
 */
@ApiModel("未读消息红点 Response VO")
@Data
public class RedDotRespVO {
    @ApiModelProperty(value = "聊天红点")
    private Boolean chatRedDot;
    @ApiModelProperty(value = "个人消息中心红点")
    private Boolean messageRedDot;
}
