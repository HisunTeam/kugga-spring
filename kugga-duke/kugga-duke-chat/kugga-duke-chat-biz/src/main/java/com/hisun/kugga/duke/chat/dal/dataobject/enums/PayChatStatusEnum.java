package com.hisun.kugga.duke.chat.dal.dataobject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 */
@Getter
@AllArgsConstructor
public enum PayChatStatusEnum {

    /**
     * 支付完成待确认聊天
     */
    TO_BE_CONFIRMED("TO_BE_CONFIRMED"),

    /**
     * 确认聊天
     */
    CONFIRMED("CONFIRMED"),

    /**
     * 聊天过期
     */
    EXPIRE("EXPIRE"),

    ;
    private final String status;

}
