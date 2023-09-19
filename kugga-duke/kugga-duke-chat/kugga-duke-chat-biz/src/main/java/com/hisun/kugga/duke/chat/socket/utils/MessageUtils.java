package com.hisun.kugga.duke.chat.socket.utils;

import com.hisun.kugga.duke.chat.dal.dataobject.enums.MessageTypeEnum;
import com.hisun.kugga.duke.chat.socket.listener.event.entity.Message;
import com.hisun.kugga.framework.common.util.json.JsonUtils;

public class MessageUtils {

    public static Object convertMessage(Integer messageType, String message) {
        if (MessageTypeEnum.TEXT.getType().equals(messageType)) {
            return JsonUtils.parseObject(message, Message.Data.class);
        }
        return null;
    }
}
