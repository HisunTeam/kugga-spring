package com.hisun.kugga.duke.chat.socket.listener.event.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求消息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadMessage {

    /**
     * 房间号
     */
    private Long roomId;

    /**
     * 消息id
     */
    private Long recordId;

}
