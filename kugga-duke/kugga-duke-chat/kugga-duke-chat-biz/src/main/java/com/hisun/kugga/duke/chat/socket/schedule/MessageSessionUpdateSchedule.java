package com.hisun.kugga.duke.chat.socket.schedule;

import com.hisun.kugga.duke.chat.dal.dataobject.SessionDO;
import com.hisun.kugga.duke.chat.dal.mysql.SessionMapper;
import com.hisun.kugga.duke.chat.socket.bo.MessageRoomLatestBo;
import com.hisun.kugga.duke.chat.socket.redis.MessageTokenRedisRepository;
import com.hisun.kugga.duke.system.api.message.RedDotApi;
import com.hisun.kugga.duke.system.api.message.dto.RedDotReqDTO;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定时刷新用户会话列表
 */
public class MessageSessionUpdateSchedule {

    private final Map<Long, MessageRoomLatestBo> chatRoomMessageMap = new ConcurrentHashMap<>();

    @Resource
    SessionMapper sessionMapper;
    @Resource
    MessageTokenRedisRepository messageTokenRedisRepository;
    @Resource
    private RedDotApi redDotApi;

    /*
     *  定时触发在不同机器可能存在时间顺序延后的情况(集群部署，多台机器定时触发)
     *  判断如果当前消息不是自己设置到缓存里面的，则认为当前消息已被其他人更新放弃更新会话列表
     */
    @Scheduled(fixedDelay = 5 * 1000, initialDelay = 15 * 1000)
    public void refreshChatSession() {
        if (chatRoomMessageMap.isEmpty()) {
            return;
        }

        chatRoomMessageMap.forEach((roomId, messageRoomLatestBo) -> {
            MessageRoomLatestBo latest = messageTokenRedisRepository.getLatest(roomId);
            chatRoomMessageMap.remove(roomId);
            if (messageRoomLatestBo != null &&
                    latest != null &&
                    messageRoomLatestBo.getRecordId().equals(latest.getRecordId())) {
                sessionMapper.updateNewMessageByRoomId(roomId,
                        latest.getUserId(),
                        latest.getUnread(),
                        SessionDO.builder()
                                .updateTime(messageRoomLatestBo.getDateTime())
                                .recordId(messageRoomLatestBo.getRecordId())
                                .build());
                if (sessionMapper.redDot(latest.getUserId()) == 0) {
                    redDotApi.publish(new RedDotReqDTO()
                            .setUserId(latest.getUserId())
                            .setChatRedDot(false));
                }
            }
        });
    }

    /**
     * 当用户在会话框持续聊天中，重置用户聊天为已读状态
     * <p>
     * 这里会舍弃过期的消息已最新的消息为准，发送方默认就是已读状态，接收方取最新的一笔设置为已读状态
     */
    public void readMessage(Long roomId, Long recordId) {
        MessageRoomLatestBo latest = messageTokenRedisRepository.getLatest(roomId);
        if (latest.getRecordId().equals(recordId)) {
            latest.setUnread(Boolean.FALSE);
            messageTokenRedisRepository.setLatest(roomId, latest);
        }
    }

    public void addToDoMessage(Long roomId, MessageRoomLatestBo messageRoomLatestBo) {
        chatRoomMessageMap.put(roomId, messageRoomLatestBo);
    }

}
