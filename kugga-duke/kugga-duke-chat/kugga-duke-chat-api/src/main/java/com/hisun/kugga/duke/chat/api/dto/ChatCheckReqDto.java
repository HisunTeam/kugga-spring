package com.hisun.kugga.duke.chat.api.dto;

import lombok.Data;

@Data
public class ChatCheckReqDto {

    /**
     * 聊天发起方
     */
    private Long userId;

    /**
     * 聊天被发起方
     */
    private Long receiveUserId;

    private Boolean isRoomPayCheck = Boolean.FALSE;

}
