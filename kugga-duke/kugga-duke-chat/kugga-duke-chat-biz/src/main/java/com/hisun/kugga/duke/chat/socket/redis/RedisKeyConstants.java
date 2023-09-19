package com.hisun.kugga.duke.chat.socket.redis;

import com.hisun.kugga.duke.chat.socket.bo.MessageTokenBo;
import com.hisun.kugga.framework.redis.core.RedisKeyDefine;

import static com.hisun.kugga.framework.redis.core.RedisKeyDefine.KeyTypeEnum.STRING;

/**
 * System Redis Key 枚举类
 */
public interface RedisKeyConstants {

    RedisKeyDefine CHAT_MESSAGE_TOKEN = new RedisKeyDefine("",
            "chat_message_token:%s", // 参数为访问令牌 token
            STRING, MessageTokenBo.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

    RedisKeyDefine CHAT_MESSAGE_ROOM = new RedisKeyDefine("",
            "chat_message_room:%s", // 参数为访问令牌 token
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

    RedisKeyDefine CHAT_MESSAGE_ROOM_LATEST = new RedisKeyDefine("",
            "chat_message_room_latest:%s", // 参数为访问令牌 token
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);
}
