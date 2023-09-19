package com.hisun.kugga.duke.chat.controller.app.vo.roomrecord;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel("管理后台 - 聊天记录 Response VO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRecordRespVO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "消息数据体")
    private Object data;

    /**
     * 聊天室ID
     */
    private Long roomId;
    /**
     * 消息发送者ID
     */
    private Long userId;
    /**
     * @see com.hisun.kugga.duke.chat.dal.dataobject.enums.MessageTypeEnum
     * <p>
     * 消息类型. 系统消息：0， 文字 1 ，图片，视频，语音，文件，撤回消息
     */
    private Integer messageType;

    /**
     * 回复消息的消息记录ID
     */
    private Long replyId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
