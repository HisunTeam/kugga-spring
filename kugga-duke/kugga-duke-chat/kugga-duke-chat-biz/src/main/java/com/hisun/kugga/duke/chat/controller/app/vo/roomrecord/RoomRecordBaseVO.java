package com.hisun.kugga.duke.chat.controller.app.vo.roomrecord;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 聊天记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class RoomRecordBaseVO {

    @ApiModelProperty(value = "聊天室ID", required = true)
    @NotNull(message = "聊天室ID不能为空")
    private Long roomId;

    @ApiModelProperty(value = "消息发送者ID")
    private Long userId;

    @ApiModelProperty(value = "消息类型. 文字，图片，视频，语音，文件，撤回消息", required = true)
    @NotNull(message = "消息类型. 文字，图片，视频，语音，文件，撤回消息不能为空")
    private Integer messageType;

    @ApiModelProperty(value = "消息数据体", required = true)
    @NotNull(message = "消息数据体不能为空")
    private String data;

    @ApiModelProperty(value = "回复消息的消息记录ID")
    private Long replyId;

}
