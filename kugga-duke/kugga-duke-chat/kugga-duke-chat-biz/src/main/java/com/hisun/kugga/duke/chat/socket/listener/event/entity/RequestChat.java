package com.hisun.kugga.duke.chat.socket.listener.event.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestChat {

    /**
     * 工会Id
     */
    private Long leagueId;

    /**
     * 待接收消息的用户
     */
    private Long receiveUserId;

    private Long roomId;
}
