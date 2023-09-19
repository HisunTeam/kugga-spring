package com.hisun.kugga.duke.chat.controller.app.vo.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("chat - 聊天室创建 Request VO")
@Data
public class RequestChatReqVO {

    /**
     * 工会Id
     */
    @ApiModelProperty(value = "工会Id")
    private Long leagueId;

    /**
     * 待接收消息的用户
     */
    @ApiModelProperty(value = "待接收消息的用户")
    private Long receiveUserId;

}
