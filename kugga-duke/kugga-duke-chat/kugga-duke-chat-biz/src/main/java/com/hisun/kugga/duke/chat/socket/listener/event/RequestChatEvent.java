package com.hisun.kugga.duke.chat.socket.listener.event;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.hisun.kugga.duke.chat.dal.dataobject.RoomDO;
import com.hisun.kugga.duke.chat.service.bo.ChatRoomBo;
import com.hisun.kugga.duke.chat.service.chat.ChatRoomService;
import com.hisun.kugga.duke.chat.socket.constant.SocketEvent;
import com.hisun.kugga.duke.chat.socket.constant.SocketRoomPrefix;
import com.hisun.kugga.duke.chat.socket.listener.event.entity.Message;
import com.hisun.kugga.duke.chat.socket.listener.event.entity.RequestChat;
import com.hisun.kugga.duke.chat.socket.utils.SocketUtils;
import com.hisun.kugga.framework.common.api.oauth2.BaseOAuth2TokenApi;
import com.hisun.kugga.framework.common.api.oauth2.dto.OAuth2AccessTokenDTO;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.socketio.handler.AbstractEventListenerHandler;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 发起聊天请求，移动到controller接口，暂时注释
 */
@Deprecated
public class RequestChatEvent extends AbstractEventListenerHandler<Message> {

    @Resource
    private ChatRoomService chatRoomService;
    @Resource
    private BaseOAuth2TokenApi baseOAuth2TokenApi;

    /**
     * 用户发起聊天申请
     */
    @OnEvent(SocketEvent.REQUEST_CHAT)
    public void onData(SocketIOClient client, RequestChat message) {
        logger.info("request chat:{}", message);

        String token = SocketUtils.getHandshakeToken(client.getHandshakeData());
        OAuth2AccessTokenDTO accessToken = baseOAuth2TokenApi.getAccessToken(token);
        Long userId = accessToken.getUserId();

        CommonResult<Object> result = CommonResult.success(message);
        try {
            //first check sendUserId and receiveUserId in sameRoom
            RoomDO roomDO = chatRoomService.checkUserInPrivateRoom(userId, message.getReceiveUserId());
            RoomDO newRoom = roomDO;
            if (roomDO == null) {//if users not contact before, create relationship with two users
                newRoom = chatRoomService.createPrivateChatRoom(ChatRoomBo.builder()
                        .userId(userId)
                        .receiveUserId(message.getReceiveUserId())
                        .build());
                // 新创建的房间，用户自己加入房间
                client.joinRoom(SocketRoomPrefix.CHATROOM + newRoom.getId());
            }
            message.setRoomId(Optional.ofNullable(roomDO).map(RoomDO::getId).orElse(newRoom.getId()));
        } catch (ServiceException ex) {
            result = CommonResult.error(ex);
        }
        client.sendEvent(SocketEvent.REQUEST_CHAT, result);
    }

}
