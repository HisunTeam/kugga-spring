package com.hisun.kugga.duke.chat.service.api;

import com.hisun.kugga.duke.chat.api.ChatApi;
import com.hisun.kugga.duke.chat.api.dto.*;
import com.hisun.kugga.duke.chat.dal.dataobject.RoomDO;
import com.hisun.kugga.duke.chat.dal.dataobject.enums.PayChatStatusEnum;
import com.hisun.kugga.duke.chat.dal.mysql.RoomMapper;
import com.hisun.kugga.duke.chat.enums.PayTypeEnum;
import com.hisun.kugga.duke.chat.service.SessionService;
import com.hisun.kugga.duke.chat.service.bo.ChatRoomBo;
import com.hisun.kugga.duke.chat.service.chat.ChatRoomService;
import com.hisun.kugga.framework.common.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.hisun.kugga.duke.chat.enums.ErrorCodeConstantsChat.*;

@Service
public class ChatApiImpl implements ChatApi {

    @Resource
    private ChatRoomService chatRoomService;
    @Resource
    private RoomMapper roomMapper;
    @Resource
    private SessionService sessionService;

    @Override
    public ChatCheckRespDto chatCheck(ChatCheckReqDto reqDto) {
        Optional.ofNullable(reqDto.getUserId()).orElseThrow(() -> new ServiceException(InputIsNull, "userId"));
        Optional.ofNullable(reqDto.getReceiveUserId()).orElseThrow(() -> new ServiceException(InputIsNull, "receiveUserId"));

        ChatCheckRespDto respDto = ChatCheckRespDto.builder()
                .isOnChat(Boolean.TRUE)
                .build();
        RoomDO roomDO = chatRoomService.checkUserInPrivateRoom(reqDto.getUserId(), reqDto.getReceiveUserId());
        if (roomDO == null) {
            // 无可用的聊天房间
            respDto.setIsOnChat(Boolean.FALSE);
            respDto.setIsInPrivateRoom(Boolean.FALSE);
            return respDto;
        }

        PayTypeEnum payTypeEnum = PayTypeEnum.valueOf(roomDO.getPayType());
        if (payTypeEnum == PayTypeEnum.PAY_CHAT &&
                LocalDateTime.now().isAfter(roomDO.getExpireTime())) {
            // 付费聊天已过期
            respDto.setIsOnChat(Boolean.FALSE);
            respDto.setIsInPrivateRoom(Boolean.TRUE);
            respDto.setRoomId(roomDO.getId());
        }

        boolean isRoomPayCheck = Optional.ofNullable(reqDto.getIsRoomPayCheck()).orElse(Boolean.FALSE);
        if (isRoomPayCheck) {
            // 房间内支付，需要校验是发起方支付
            if (!reqDto.getUserId().equals(roomDO.getInviterUserId())) {
                ServiceException.throwServiceException(MustInviterUserPay);
            }
        }
        return respDto;
    }

    @Override
    public void payChatStatus(PayChatStatusReqDto reqDto) {
        Optional.ofNullable(reqDto.getRoomId()).orElseThrow(() -> new ServiceException(InputIsNull, "roomId"));
        Optional.ofNullable(reqDto.getPayChatStatus()).orElseThrow(() -> new ServiceException(InputIsNull, "payChatStatus"));
        Optional.ofNullable(reqDto.getReceiveLeagueId()).orElseThrow(() -> new ServiceException(InputIsNull, "receiveLeagueId"));
        Optional.ofNullable(reqDto.getInviterUserId()).orElseThrow(() -> new ServiceException(InputIsNull, "inviterUserId"));

        String payChatStatus = PayChatStatusEnum.valueOf(reqDto.getPayChatStatus().toString()).getStatus();
        int i = roomMapper.updatePayChatStatus(reqDto.getRoomId(),
                payChatStatus,
                reqDto.getReceiveLeagueId(),
                reqDto.getInviterUserId());
        if (i < 1) {
            ServiceException.throwServiceException(RoomNotExists);
        }
    }

    @Override
    public PayChatRespDto payChat(PayChatReqDto reqDto) {
        Optional.ofNullable(reqDto.getLeagueId()).orElseThrow(() -> new ServiceException(InputIsNull, "leagueId"));
        Optional.ofNullable(reqDto.getUserId()).orElseThrow(() -> new ServiceException(InputIsNull, "userId"));
        Optional.ofNullable(reqDto.getReceiveUserId()).orElseThrow(() -> new ServiceException(InputIsNull, "receiveUserId"));
        Optional.ofNullable(reqDto.getExpireTime()).orElseThrow(() -> new ServiceException(InputIsNull, "expireTime"));

        RoomDO roomDO = chatRoomService.checkUserInPrivateRoom(reqDto.getUserId(), reqDto.getReceiveUserId());
        if (roomDO == null) {
            // 无可用的聊天房间
            RoomDO newRoom = chatRoomService.createPrivateChatRoom(ChatRoomBo.builder()
                    .leagueId(reqDto.getLeagueId())
                    .payTypeEnum(PayTypeEnum.PAY_CHAT)
                    .userId(reqDto.getUserId())
                    .receiveUserId(reqDto.getReceiveUserId())
                    .expireTime(reqDto.getExpireTime())
                    .build());
            return PayChatRespDto.builder()
                    .roomId(newRoom.getId())
                    .build();
        }

        // 存在可用的聊天
        if (PayTypeEnum.valueOf(roomDO.getPayType()) == PayTypeEnum.FREE_CHAT) {
            // 如果存在免费聊天的情况，不需要续费房间了
            ServiceException.throwServiceException(ExistsFreeRoom);
        }

        if (LocalDateTime.now().isAfter(reqDto.getExpireTime())) {
            // 时间已经过期超过当前时间
            ServiceException.throwServiceException(RoomExpTime);
        }

        int i = roomMapper.updateRoomExpireTime(roomDO.getId(),
                reqDto.getLeagueId(),
                reqDto.getUserId(),
                reqDto.getExpireTime(),
                PayChatStatusEnum.CONFIRMED.getStatus()
        );

        return PayChatRespDto.builder()
                .roomId(roomDO.getId())
                .build();
    }

    @Override
    public void expireRoomToFree(ExpireRoomToFreeReqDto reqDto) {
        Optional.ofNullable(reqDto.getLeagueId()).orElseThrow(() -> new ServiceException(InputIsNull, "leagueId"));
        Optional.ofNullable(reqDto.getRoomId()).orElseThrow(() -> new ServiceException(InputIsNull, "roomId"));
        RoomDO roomDO = chatRoomService.selectByRoomId(reqDto.getRoomId());
        if (PayTypeEnum.PAY_CHAT == PayTypeEnum.valueOf(roomDO.getPayType())
                && LocalDateTime.now().isAfter(roomDO.getExpireTime())
        ) {
            chatRoomService.updateRoomFree(reqDto.getLeagueId(), roomDO.getId());
        }
    }

    @Override
    public Boolean messageRetDot(Long userId) {
        return sessionService.messageRetDot(userId);
    }
}
