package com.hisun.kugga.duke.chat.socket.listener.init;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.hisun.kugga.duke.chat.dal.dataobject.RoomMemberDO;
import com.hisun.kugga.duke.chat.dal.mysql.RoomMemberMapper;
import com.hisun.kugga.duke.chat.socket.constant.SocketEvent;
import com.hisun.kugga.duke.chat.socket.constant.SocketRoomPrefix;
import com.hisun.kugga.duke.chat.socket.utils.SocketUtils;
import com.hisun.kugga.framework.common.api.oauth2.BaseOAuth2TokenApi;
import com.hisun.kugga.framework.common.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import com.hisun.kugga.framework.socketio.handler.AbstractSocketEventHandler;
import lombok.Data;

import javax.annotation.Resource;
import java.util.List;

@Data
public class InitSocketEventHandler extends AbstractSocketEventHandler {

    @Resource
    private RoomMemberMapper roomMemberMapper;

    @Resource
    private BaseOAuth2TokenApi baseOAuth2TokenApi;

    public InitSocketEventHandler(SocketIOServer socketIOServer) {
        super(socketIOServer);
    }

    /**
     * 用户连接服务器，获取用户所有房间并加入房间
     */
    @Override
    public void onConnect(SocketIOClient client) {
        super.onConnect(client);
        String token = SocketUtils.getHandshakeToken(client.getHandshakeData());
        OAuth2AccessTokenCheckRespDTO accessToken = baseOAuth2TokenApi.checkAccessToken(token);
        // 查询该用户下所有的聊天室,加入房间
        List<RoomMemberDO> roomDOS = roomMemberMapper.selectAllRoomsByUserId(accessToken.getUserId());
        roomDOS.forEach(i -> client.joinRoom(SocketRoomPrefix.CHATROOM + i.getRoomId()));
        client.joinRoom(SocketRoomPrefix.USER + accessToken.getUserId());
//        SocketUtils.addTokenToUserIdMap(token, accessToken.getUserId());
        client.sendEvent(SocketEvent.INIT, accessToken.getUserId());
    }

    /**
     * 断开连接操作
     */
    @Override
    public void onDisconnect(SocketIOClient client) {
        super.onDisconnect(client);
        String token = SocketUtils.getHandshakeToken(client.getHandshakeData());
//        SocketUtils.deleteTokenToUserIdMap(token);
        OAuth2AccessTokenCheckRespDTO accessToken = baseOAuth2TokenApi.checkAccessToken(token);
        List<RoomMemberDO> roomDOS = roomMemberMapper.selectAllRoomsByUserId(accessToken.getUserId());
        // 加入用户房间
        roomDOS.forEach(i -> client.leaveRoom(SocketRoomPrefix.CHATROOM + i.getRoomId()));
        client.leaveRoom(SocketRoomPrefix.USER + accessToken.getUserId());
        client.sendEvent(SocketEvent.RTC_HANG_UP, accessToken.getUserId());
    }
}
