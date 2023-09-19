package com.hisun.kugga.duke.chat.socket.listener.event.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hisun.kugga.duke.chat.enums.PayTypeEnum;
import com.hisun.kugga.framework.jackson.core.databind.LocalDateTimeDeserializer;
import com.hisun.kugga.framework.jackson.core.databind.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 响应消息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResp {

    // 请求消息
    private String tempId;

    /**
     * id
     */
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
    private Message.Data data;
    /**
     * 回复消息的消息记录ID
     */
    private Long replyId;

    /**
     * 创建时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * lastName 姓  姓：选填    名：必填
     */
    private String lastName;
    /**
     * firstName 名
     */
    private String firstName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * @see PayTypeEnum
     */
    private Integer payType;
}
