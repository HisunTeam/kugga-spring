package com.hisun.kugga.duke.chat.service.chat;

import com.hisun.kugga.duke.chat.dal.dataobject.RoomDO;
import com.hisun.kugga.duke.chat.dal.dataobject.RoomMemberDO;
import com.hisun.kugga.duke.chat.dal.dataobject.SessionDO;
import com.hisun.kugga.duke.chat.dal.dataobject.enums.RoomMemberRoleEnum;
import com.hisun.kugga.duke.chat.dal.dataobject.enums.RoomTypeEnum;
import com.hisun.kugga.duke.chat.dal.dataobject.enums.SessionTypeEnum;
import com.hisun.kugga.duke.chat.dal.mysql.RoomMapper;
import com.hisun.kugga.duke.chat.dal.mysql.RoomMemberMapper;
import com.hisun.kugga.duke.chat.dal.mysql.SessionMapper;
import com.hisun.kugga.duke.chat.enums.PayTypeEnum;
import com.hisun.kugga.duke.chat.service.bo.ChatRoomBo;
import com.hisun.kugga.duke.user.api.oauth2.DukeUserApi;
import com.hisun.kugga.duke.user.api.oauth2.dto.UserInfoRespDTO;
import com.hisun.kugga.framework.common.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static com.hisun.kugga.duke.chat.enums.ErrorCodeConstantsChat.SameUsersChat;

@Service
public class ChatRoomService {

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private RoomMemberMapper roomMemberMapper;

    @Resource
    private SessionMapper sessionMapper;

    @Resource
    private DukeUserApi dukeUserApi;

    /**
     * 创建一个两个聊天的私聊 聊天室
     */
    @Transactional
    public RoomDO createPrivateChatRoom(ChatRoomBo chatRoomBo) {

        // check receiveUserId is exists, or else throw user not exists serviceException
        UserInfoRespDTO userById = dukeUserApi.getUserById(chatRoomBo.getReceiveUserId());

        if (chatRoomBo.getUserId().equals(chatRoomBo.getReceiveUserId())) {
            ServiceException.throwServiceException(SameUsersChat);
        }

        RoomDO insertRoom = RoomDO.builder()
                .name("PRIVATE_CHATROOM")
                .roomType(RoomTypeEnum.TWO_CHAT.getType())
                .peopleLimit(2)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .payType(chatRoomBo.getPayTypeEnum().getType())
                .leagueId(chatRoomBo.getLeagueId())
                .inviterUserId(chatRoomBo.getUserId())
                .build();
        if (PayTypeEnum.PAY_CHAT == chatRoomBo.getPayTypeEnum()) {
            insertRoom.setExpireTime(chatRoomBo.getExpireTime());
        }
        int i = roomMapper.insertOne(insertRoom);

        int j = roomMemberMapper.insertOne(RoomMemberDO.builder()
                .roomId(insertRoom.getId())
                .userId(chatRoomBo.getUserId())
                .nickname("unknown") //todo
                .role(RoomMemberRoleEnum.NORMAL.getRole())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build());
        int k = roomMemberMapper.insertOne(RoomMemberDO.builder()
                .roomId(insertRoom.getId())
                .userId(chatRoomBo.getReceiveUserId())
                .nickname("unknown")
                .role(RoomMemberRoleEnum.NORMAL.getRole())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build());

        // 免费聊天，接收方会话列表不显示
        Boolean receiveChatSessionVisible = chatRoomBo.getPayTypeEnum() == PayTypeEnum.PAY_CHAT ? Boolean.TRUE : Boolean.FALSE;

        // add chat session
        int l = sessionMapper.insertOne(SessionDO.builder()
                .userId(chatRoomBo.getUserId())
                .receiveUserId(chatRoomBo.getReceiveUserId())
                .sessionType(SessionTypeEnum.TYPE_CHATROOM.getType())
                .roomId(insertRoom.getId())
                .roomType(RoomTypeEnum.TWO_CHAT.getType())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .readTime(LocalDateTime.now())
                .visible(Boolean.TRUE)
                .build());
        int m = sessionMapper.insertOne(SessionDO.builder()
                .userId(chatRoomBo.getReceiveUserId())
                .receiveUserId(chatRoomBo.getUserId())
                .sessionType(SessionTypeEnum.TYPE_CHATROOM.getType())
                .roomId(insertRoom.getId())
                .roomType(RoomTypeEnum.TWO_CHAT.getType())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .readTime(LocalDateTime.now())
                .visible(receiveChatSessionVisible)
                .build());
        return insertRoom;
    }

    public boolean checkUserInRoom(Long userId, Long roomId) {
        return roomMemberMapper.checkUserIdRoom(userId, roomId) > 0;
    }

    public RoomDO selectByRoomId(Long id) {
        return roomMapper.selectByRoomId(id);
    }

    /**
     * 判断用户是否在回话列表，如果存在则不需要创建好友关系
     */
    public RoomDO checkUserInPrivateRoom(Long userId, Long receiveUserId) {
        return roomMapper.checkUsersInSameRoom(userId, receiveUserId);
    }

    public void updateRoomFree(Long leagueId, Long roomId) {
        int i = roomMapper.updateRoomFree(leagueId, roomId);
    }

}
