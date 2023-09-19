package com.hisun.kugga.duke.chat.dal.dataobject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTypeEnum {

    /**
     * 系统消息
     */
    SYSTEM(0),
    /**
     * 文本
     */
    TEXT(1),
    /**
     * 富文本
     */
    RICH_TEXT(2),
    /**
     * 文字提示
     */
    TIPS(3),
    /**
     * 群聊邀请
     */
    CHAT_INVITATION(4),
    /**
     * 图片
     */
    IMAGE(5),
    /**
     * 语音
     */
    VOICE(6),
    ;

    private final Integer type;
}
