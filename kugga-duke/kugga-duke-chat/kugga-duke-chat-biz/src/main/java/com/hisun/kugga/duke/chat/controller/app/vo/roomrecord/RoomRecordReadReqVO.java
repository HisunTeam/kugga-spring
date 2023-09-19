package com.hisun.kugga.duke.chat.controller.app.vo.roomrecord;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("管理后台 - 聊天记录已读 Request VO")
@Data
public class RoomRecordReadReqVO {

    @ApiModelProperty(value = "聊天室ID(roomId)")
    private Long roomId;

    @ApiModelProperty(value = "未读消息数")
    private Integer unread;

}
