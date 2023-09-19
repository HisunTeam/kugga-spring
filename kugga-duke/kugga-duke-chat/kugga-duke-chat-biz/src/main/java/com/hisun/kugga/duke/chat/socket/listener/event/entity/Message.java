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
public class Message {

    private String tempId;

    private String messageToken;

    /**
     * 消息类型 默认为 1 暂时只支持文字类型
     */
    // private Integer type;

    private Data data;

    @lombok.Data
    public static class Data {
        private String content;
    }

}
