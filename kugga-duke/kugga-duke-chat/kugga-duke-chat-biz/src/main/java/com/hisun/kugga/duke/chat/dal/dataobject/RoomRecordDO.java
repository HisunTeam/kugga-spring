package com.hisun.kugga.duke.chat.dal.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 聊天记录 DO
 *
 * @author toi
 */
@TableName("chat_room_record")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRecordDO {

    /**
     * id
     */
    @TableId
    private Long id;
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
     * 消息数据体
     */
    private String data;
    /**
     * 回复消息的消息记录ID
     */
    private Long replyId;

    /**
     * 创建时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
