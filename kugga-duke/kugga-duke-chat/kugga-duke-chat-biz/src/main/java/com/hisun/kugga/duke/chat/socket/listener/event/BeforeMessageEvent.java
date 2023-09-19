package com.hisun.kugga.duke.chat.socket.listener.event;

import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.hisun.kugga.duke.chat.dal.dataobject.RoomDO;
import com.hisun.kugga.duke.chat.enums.PayTypeEnum;
import com.hisun.kugga.duke.chat.service.chat.ChatRoomService;
import com.hisun.kugga.duke.chat.socket.bo.MessageTokenBo;
import com.hisun.kugga.duke.chat.socket.constant.SocketEvent;
import com.hisun.kugga.duke.chat.socket.listener.event.entity.BeforeMessage;
import com.hisun.kugga.duke.chat.socket.listener.event.entity.BeforeMessageResp;
import com.hisun.kugga.duke.chat.socket.listener.event.entity.Message;
import com.hisun.kugga.duke.chat.socket.redis.MessageTokenRedisRepository;
import com.hisun.kugga.duke.chat.socket.utils.SocketUtils;
import com.hisun.kugga.framework.common.api.oauth2.BaseOAuth2TokenApi;
import com.hisun.kugga.framework.common.api.oauth2.dto.OAuth2AccessTokenDTO;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.socketio.handler.AbstractEventListenerHandler;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.hisun.kugga.duke.chat.enums.ErrorCodeConstantsChat.*;

/**
 * 给某人发消息前预处理，获取消息token
 */
public class BeforeMessageEvent extends AbstractEventListenerHandler<Message> {

    @Resource
    MessageTokenRedisRepository messageTokenRedisRepository;
    @Resource
    private ChatRoomService chatRoomService;
    @Resource
    private BaseOAuth2TokenApi baseOAuth2TokenApi;

    @OnEvent(SocketEvent.BEFORE_MESSAGE)
    public void onData(SocketIOClient client, BeforeMessage beforeMessage) {
        logger.info("before_message:{}", beforeMessage);

        BeforeMessageResp resp = BeforeMessageResp.builder()
                .roomId(beforeMessage.getRoomId())
                .build();
        CommonResult<Object> result = CommonResult.success(resp);
        try {
            //check input
            Optional.ofNullable(beforeMessage.getRoomId()).orElseThrow(() -> new ServiceException(InputIsNull, "roomId"));

            String token = SocketUtils.getHandshakeToken(client.getHandshakeData());
            Optional.ofNullable(token).orElseThrow(() -> new ServiceException(InputIsNull, "token"));
            OAuth2AccessTokenDTO accessToken = baseOAuth2TokenApi.getAccessToken(token);
            Optional.ofNullable(accessToken).orElseThrow(() -> new ServiceException(MessageTokenExp));
            Long userId = accessToken.getUserId();

            //check current user is in the room
            boolean b = chatRoomService.checkUserInRoom(userId, beforeMessage.getRoomId());
            if (!b) {
                ServiceException.throwServiceException(UserNotInRoom);
            }
            //get room token cache
            String messageToken = messageTokenRedisRepository.getRoomToken(beforeMessage.getRoomId());
            if (StrUtil.isEmpty(messageToken)) {
                // query room info
                RoomDO roomDO = chatRoomService.selectByRoomId(beforeMessage.getRoomId());
                // check room type and expire time
                chatRoomService.selectByRoomId(beforeMessage.getRoomId());
                boolean isPayChat = PayTypeEnum.valueOf(roomDO.getPayType()) == PayTypeEnum.PAY_CHAT;
                if (isPayChat &&
                        LocalDateTime.now().isAfter(roomDO.getExpireTime())) {
                    // 付费聊天已过期
                    ServiceException.throwServiceException(RoomExpTime);
                }

                // message token 生成规则： roomId + 32位 uuid
                messageToken = SocketUtils.createMessageTokenString(beforeMessage.getRoomId());
                messageTokenRedisRepository.setRoomToken(beforeMessage.getRoomId(),
                        messageToken,
                        isPayChat ? roomDO.getExpireTime() : null);
                messageTokenRedisRepository.set(messageToken,
                        MessageTokenBo.builder()
                                .roomId(beforeMessage.getRoomId())
                                .payType(roomDO.getPayType())
                                .build(),
                        isPayChat ? roomDO.getExpireTime() : null);
            }
            resp.setMessageToken(messageToken);
        } catch (ServiceException ex) {
            result = CommonResult.error(ex, resp);
        }
        client.sendEvent(SocketEvent.BEFORE_MESSAGE, result);
    }

}
