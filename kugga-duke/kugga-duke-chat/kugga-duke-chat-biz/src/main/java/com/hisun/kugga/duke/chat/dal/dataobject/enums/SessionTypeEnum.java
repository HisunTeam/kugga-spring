package com.hisun.kugga.duke.chat.dal.dataobject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 */
@Getter
@AllArgsConstructor
public enum SessionTypeEnum {

    /**
     * 聊天室会话
     */
    TYPE_CHATROOM(0),
    /**
     * 聊天室通知会话
     */
    TYPE_CHATROOM_NOTICE(1);

    private final Integer type;

}
