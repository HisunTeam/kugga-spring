package com.hisun.kugga.duke.chat.dal.dataobject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 */
@Getter
@AllArgsConstructor
public enum RoomTypeEnum {

    /**
     * 私聊
     */
    TWO_CHAT(0),
    /**
     * 群聊
     */
    MANY_CHAT(1),
    /**
     * 单聊
     */
    SELF_CHAT(2);

    private final Integer type;

}
