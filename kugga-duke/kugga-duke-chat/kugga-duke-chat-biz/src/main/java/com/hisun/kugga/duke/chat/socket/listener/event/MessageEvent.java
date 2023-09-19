package com.hisun.kugga.duke.chat.socket.listener.event;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.hisun.kugga.duke.chat.dal.dataobject.RoomRecordDO;
import com.hisun.kugga.duke.chat.dal.dataobject.SessionDO;
import com.hisun.kugga.duke.chat.dal.mysql.SessionMapper;
import com.hisun.kugga.duke.chat.service.bo.MessageBo;
import com.hisun.kugga.duke.chat.service.chat.ChatRecordService;
import com.hisun.kugga.duke.chat.socket.bo.MessageRoomLatestBo;
import com.hisun.kugga.duke.chat.socket.bo.MessageTokenBo;
import com.hisun.kugga.duke.chat.socket.constant.SocketEvent;
import com.hisun.kugga.duke.chat.socket.constant.SocketRoomPrefix;
import com.hisun.kugga.duke.chat.socket.listener.event.entity.Message;
import com.hisun.kugga.duke.chat.socket.listener.event.entity.MessageResp;
import com.hisun.kugga.duke.chat.socket.redis.MessageTokenRedisRepository;
import com.hisun.kugga.duke.chat.socket.schedule.MessageSessionUpdateSchedule;
import com.hisun.kugga.duke.chat.socket.utils.SocketUtils;
import com.hisun.kugga.duke.system.api.message.RedDotApi;
import com.hisun.kugga.duke.system.api.message.dto.RedDotReqDTO;
import com.hisun.kugga.duke.user.api.oauth2.DukeUserApi;
import com.hisun.kugga.duke.user.api.oauth2.dto.UserInfoRespDTO;
import com.hisun.kugga.framework.common.api.oauth2.BaseOAuth2TokenApi;
import com.hisun.kugga.framework.common.api.oauth2.dto.OAuth2AccessTokenDTO;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.util.json.JsonUtils;
import com.hisun.kugga.framework.socketio.handler.AbstractEventListenerHandler;

import javax.annotation.Resource;
import java.util.Optional;

import static com.hisun.kugga.duke.chat.enums.ErrorCodeConstantsChat.InputIsNull;
import static com.hisun.kugga.duke.chat.enums.ErrorCodeConstantsChat.MessageTokenExp;

public class MessageEvent extends AbstractEventListenerHandler<Message> {

    @Resource
    MessageTokenRedisRepository messageTokenRedisRepository;
    @Resource
    SocketIOServer socketIOServer;
    @Resource
    private ChatRecordService chatRecordService;
    @Resource
    private BaseOAuth2TokenApi baseOAuth2TokenApi;
    @Resource
    private MessageSessionUpdateSchedule messageSessionUpdateSchedule;
    @Resource
    private DukeUserApi dukeUserApi;
    @Resource
    private SessionMapper sessionMapper;
    @Resource
    private RedDotApi redDotApi;

    @OnEvent(SocketEvent.MESSAGE)
    public void onData(SocketIOClient client, Message message) {
        logger.info("message:{}", message);
        CommonResult<Object> result;
        try {
            String token = SocketUtils.getHandshakeToken(client.getHandshakeData());
            Optional.ofNullable(token).orElseThrow(() -> new ServiceException(InputIsNull, "token"));
            //check message token is expires
            MessageTokenBo messageTokenBo = messageTokenRedisRepository.get(message.getMessageToken());
            Optional.ofNullable(messageTokenBo).orElseThrow(() -> new ServiceException(MessageTokenExp));

            OAuth2AccessTokenDTO accessToken = baseOAuth2TokenApi.getAccessToken(token);
            Optional.ofNullable(accessToken).orElseThrow(() -> new ServiceException(MessageTokenExp));
            Optional.ofNullable(accessToken.getUserId()).orElseThrow(() -> new ServiceException(MessageTokenExp));
            //check input message
            Optional.ofNullable(message.getData()).orElseThrow(() -> new ServiceException(InputIsNull, "data"));
            Optional.ofNullable(message.getData().getContent()).orElseThrow(() -> new ServiceException(InputIsNull, "content"));
            String data = JsonUtils.toJsonString(message.getData());
            RoomRecordDO recordDO = chatRecordService.saveRoomRecord(MessageBo.builder()
                    .roomId(messageTokenBo.getRoomId())
                    .userId(accessToken.getUserId())
                    .data(data)
                    .build());
            UserInfoRespDTO userById = dukeUserApi.getUserById(accessToken.getUserId());
            MessageResp messageResp = MessageResp.builder()
                    .tempId(message.getTempId())
                    .id(recordDO.getId())
                    .roomId(recordDO.getRoomId())
                    .userId(recordDO.getUserId())
                    .messageType(recordDO.getMessageType())
                    .data(message.getData())
                    .replyId(recordDO.getReplyId())
                    .createTime(recordDO.getCreateTime())
                    .firstName(userById.getFirstName())
                    .lastName(userById.getLastName())
                    .avatar(userById.getAvatar())
                    .payType(messageTokenBo.getPayType())
                    .build();

            //将消息写入redis
            //同一个房间的消息只会存在一条默认的消息，按照时间顺序来说最新的消息将被更新至双方用户的会话列表
            MessageRoomLatestBo lastMessageBo = MessageRoomLatestBo.builder()
                    .recordId(recordDO.getId())
                    .userId(recordDO.getUserId())
                    .unread(Boolean.TRUE)
                    .messageType(recordDO.getMessageType())
                    .data(data)
                    .dateTime(recordDO.getCreateTime()).build();
            messageTokenRedisRepository.setLatest(recordDO.getRoomId(), lastMessageBo);

            // 定时更新会话列表
            messageSessionUpdateSchedule.addToDoMessage(recordDO.getRoomId(), lastMessageBo);

            // 广播消息
            socketIOServer.getRoomOperations(SocketRoomPrefix.CHATROOM + messageTokenBo.getRoomId())
                    .sendEvent(SocketEvent.MESSAGE, CommonResult.success(messageResp));
            // 发布未读红点
            SessionDO sessionDO = sessionMapper.selectOne(new LambdaQueryWrapper<SessionDO>()
                    .eq(SessionDO::getRoomId, messageTokenBo.getRoomId())
                    .eq(SessionDO::getUserId, accessToken.getUserId())
                    .select(SessionDO::getReceiveUserId));
            if (ObjectUtil.isNotNull(sessionDO)) {
                redDotApi.publish(new RedDotReqDTO()
                        .setUserId(sessionDO.getReceiveUserId())
                        .setChatRedDot(true));
            }
        } catch (ServiceException ex) {
            Long roomId = StrUtil.isNotBlank(message.getMessageToken()) ? SocketUtils.getRoomIdByMessageToken(message.getMessageToken()) : null;
            MessageResp build = MessageResp.builder()
                    .tempId(message.getTempId())
                    .roomId(roomId)
                    .build();
            result = CommonResult.error(ex, build);
            client.sendEvent(SocketEvent.MESSAGE, result);
        }
    }

}
