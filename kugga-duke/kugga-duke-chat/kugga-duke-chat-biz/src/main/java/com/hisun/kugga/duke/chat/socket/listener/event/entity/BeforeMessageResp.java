package com.hisun.kugga.duke.chat.socket.listener.event.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeforeMessageResp {

    private Long roomId;

    private String messageToken;
}
