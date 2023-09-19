package com.hisun.kugga.duke.chat.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 */
@Getter
@AllArgsConstructor
public enum PayChatStatusApiEnum {

    /**
     * 支付完成待确认聊天
     */
    TO_BE_CONFIRMED,

    /**
     * 聊天过期，用户未确认
     */
    EXPIRE,

}
