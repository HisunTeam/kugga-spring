package com.hisun.kugga.duke.chat.service.chat;

import com.hisun.kugga.duke.chat.dal.dataobject.RoomRecordDO;
import com.hisun.kugga.duke.chat.dal.dataobject.enums.MessageTypeEnum;
import com.hisun.kugga.duke.chat.dal.mysql.RoomMemberMapper;
import com.hisun.kugga.duke.chat.dal.mysql.RoomRecordMapper;
import com.hisun.kugga.duke.chat.service.bo.MessageBo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class ChatRecordService {

    @Resource
    RoomRecordMapper roomRecordMapper;

    @Resource
    RoomMemberMapper roomMemberMapper;

    /**
     * 保存消息至聊天室
     */
    public RoomRecordDO saveRoomRecord(MessageBo messageBo) {
        // 在获取messageToken的时候已经校验了用户在房间，不需要重复校验
        // RoomMemberDO roomMemberDO = roomMemberMapper.selectRoomsByUserId(messageBo.getRoomId(), messageBo.getUserId());
        // Optional.ofNullable(roomMemberDO).orElseThrow(()->new ServiceException(UserNotInRoom));

        RoomRecordDO roomRecordDO = RoomRecordDO.builder()
                .roomId(messageBo.getRoomId())
                .userId(messageBo.getUserId())
                .data(messageBo.getData())
                .messageType(MessageTypeEnum.TEXT.getType())
                .createTime(LocalDateTime.now())
                .build();
        roomRecordMapper.insertOne(roomRecordDO);
        return roomRecordDO;
    }

}
