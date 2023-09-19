package com.hisun.kugga.duke.chat.socket.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRoomLatestBo {

    /**
     * 消息id
     */
    Long recordId;

    /**
     * 发消息人
     */
    Long userId;

    /**
     * 已读过该消息，对于接收方调用
     */
    Boolean unread;

    Integer messageType;

    String data;

    LocalDateTime dateTime;
}
