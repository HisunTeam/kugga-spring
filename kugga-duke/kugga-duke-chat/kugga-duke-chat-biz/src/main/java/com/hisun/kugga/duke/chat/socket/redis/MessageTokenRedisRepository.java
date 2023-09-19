package com.hisun.kugga.duke.chat.socket.redis;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.hisun.kugga.duke.chat.socket.bo.MessageRoomLatestBo;
import com.hisun.kugga.duke.chat.socket.bo.MessageTokenBo;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.util.json.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import static com.hisun.kugga.duke.chat.enums.ErrorCodeConstantsChat.RoomExpTime;
import static com.hisun.kugga.duke.chat.socket.redis.RedisKeyConstants.*;

@Repository
public class MessageTokenRedisRepository {

    @Value("${duck.chat.message.expires-time:1800}")
//    @Value("${duck.chat.message.expires-time:120}")
    private Integer expiresTime;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static String formatTokenKey(String messageToken) {
        return String.format(CHAT_MESSAGE_TOKEN.getKeyTemplate(), messageToken);
    }

    private static String formatRoomKey(Long roomId) {
        return String.format(CHAT_MESSAGE_ROOM.getKeyTemplate(), roomId);
    }

    private static String formatRoomLatestKey(Long roomId) {
        return String.format(CHAT_MESSAGE_ROOM_LATEST.getKeyTemplate(), roomId);
    }

    /**
     * room cache
     */
    public String getRoomToken(Long roomId) {
        String redisKey = formatRoomKey(roomId);
        return stringRedisTemplate.opsForValue().get(redisKey);
    }

    public void setRoomToken(Long roomId, String messageToken, LocalDateTime roomExpireTime) {

        String redisKey = formatRoomKey(roomId);
        stringRedisTemplate.opsForValue().set(redisKey, messageToken,
                getSmallExpireTime(roomExpireTime),
                TimeUnit.SECONDS);
    }

    private long getSmallExpireTime(LocalDateTime roomExpireTime) {
        // 免费聊天过期时间为空
        if (roomExpireTime == null) {
            return expiresTime;
        }
        // 当申请的token过期时间处于边缘状态时，取最小的那个时间不要超过过期时间
        long betweenSeconds = LocalDateTimeUtil.between(LocalDateTimeUtil.now(), roomExpireTime, ChronoUnit.SECONDS);
        if (betweenSeconds >= expiresTime) {
            return expiresTime;
        }
        if (expiresTime > betweenSeconds && betweenSeconds >= 1) {
            return betweenSeconds;
        }
        // 小于 1s 的情况，或者为负数的情况，则认为失效报错
        ServiceException.throwServiceException(RoomExpTime);
        // 返回一个错误的值
        return 1;
    }

    /**
     * token cache
     */
    public MessageTokenBo get(String messageToken) {
        String redisKey = formatTokenKey(messageToken);
        return JsonUtils.parseObject(stringRedisTemplate.opsForValue().get(redisKey), MessageTokenBo.class);
    }

    public void set(String messageToken, MessageTokenBo accessTokenDO, LocalDateTime roomExpireTime) {
        String redisKey = formatTokenKey(messageToken);
        stringRedisTemplate.opsForValue().set(redisKey,
                JsonUtils.toJsonString(accessTokenDO),
                // token的有效期稍微略长于room的有效期 + 3s
                getSmallExpireTime(roomExpireTime) + 3,
                TimeUnit.SECONDS);
    }

    /**
     * room latest message
     */
    public MessageRoomLatestBo getLatest(Long roomId) {
        String redisKey = formatRoomLatestKey(roomId);
        return JsonUtils.parseObject(stringRedisTemplate.opsForValue().get(redisKey), MessageRoomLatestBo.class);
    }

    public void setLatest(Long roomId, MessageRoomLatestBo messageRoomLatestBo) {
        String redisKey = formatRoomLatestKey(roomId);
        stringRedisTemplate.opsForValue().set(redisKey,
                JsonUtils.toJsonString(messageRoomLatestBo),
                expiresTime, TimeUnit.SECONDS);
    }

}
