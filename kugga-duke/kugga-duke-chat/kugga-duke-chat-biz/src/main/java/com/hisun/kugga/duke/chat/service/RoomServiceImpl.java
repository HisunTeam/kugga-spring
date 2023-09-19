package com.hisun.kugga.duke.chat.service;

import com.hisun.kugga.duke.chat.controller.app.vo.room.RequestChatReqVO;
import com.hisun.kugga.duke.chat.controller.app.vo.room.RequestChatRespVO;
import com.hisun.kugga.duke.chat.dal.dataobject.RoomDO;
import com.hisun.kugga.duke.chat.dal.mysql.SessionMapper;
import com.hisun.kugga.duke.chat.enums.PayTypeEnum;
import com.hisun.kugga.duke.chat.service.bo.ChatRoomBo;
import com.hisun.kugga.duke.chat.service.chat.ChatRoomService;
import com.hisun.kugga.duke.league.api.LeagueApi;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.hisun.kugga.duke.chat.enums.ErrorCodeConstantsChat.*;

/**
 * 聊天室 Service 实现类
 *
 * @author toi
 */
@Service
@Validated
public class RoomServiceImpl implements RoomService {

    @Resource
    LeagueApi leagueApi;
    @Resource
    private ChatRoomService chatRoomService;
    @Resource
    private SessionMapper sessionMapper;

    /**
     * 发起聊天请求 创建聊天
     * <p>
     * 主要用于创建免费聊天情况 通过工会列表进入
     */
    @Override
    public RequestChatRespVO createRoom(RequestChatReqVO reqVO) {

        Optional.ofNullable(reqVO.getLeagueId()).orElseThrow(() -> new ServiceException(InputIsNull, "leagueId"));
        Optional.ofNullable(reqVO.getReceiveUserId()).orElseThrow(() -> new ServiceException(InputIsNull, "receiveUserId"));
        Long userId = SecurityFrameworkUtils.getLoginUserId();

        if (reqVO.getReceiveUserId().equals(userId)) {
            ServiceException.throwServiceException(SameUsersChat);
        }

        // 判断两个用户是否在同一个工会
        Boolean isLeagueMember = leagueApi.isLeagueMember(reqVO.getLeagueId(), userId, reqVO.getReceiveUserId());

        //first check sendUserId and receiveUserId in sameRoom
        RoomDO roomDO = chatRoomService.checkUserInPrivateRoom(userId, reqVO.getReceiveUserId());
        RoomDO newRoom = roomDO;
        PayTypeEnum payTypeEnum = null;
        if (roomDO == null) {
            if (!isLeagueMember) {
                // 从来未聊天过，且不在一个工会不允许聊天
                ServiceException.throwServiceException(UserNotInSameLeague);
            }
            //if users not contact before, create relationship with two users
            newRoom = chatRoomService.createPrivateChatRoom(ChatRoomBo.builder()
                    .leagueId(reqVO.getLeagueId())
                    .payTypeEnum(PayTypeEnum.FREE_CHAT)
                    .userId(userId)
                    .receiveUserId(reqVO.getReceiveUserId())
                    .build());
            //  todo 调用接口交易，无法获取用户的client 可能会存在老页面无法加入房间的情况，需要手动刷新页面加入房间
        } else {
            // 房间存在的情况，判断房间是否生效
            // 付费聊天，房间失效的情况，且两个人在一个工会则使用新的工会，重置为工会免费聊天
            if (PayTypeEnum.PAY_CHAT == PayTypeEnum.valueOf(roomDO.getPayType())
                    && isLeagueMember
                    && LocalDateTime.now().isAfter(roomDO.getExpireTime())
            ) {
                payTypeEnum = PayTypeEnum.FREE_CHAT;
                chatRoomService.updateRoomFree(reqVO.getLeagueId(), roomDO.getId());
            }

            //房间存在的情况，且用户未聊天 存在被发起方显示状态为隐藏的情况，主动设置状态为显示，更新时间戳置顶
            int i = sessionMapper.updateSessionVisible(userId, roomDO.getId());
        }
        payTypeEnum = payTypeEnum == null ? PayTypeEnum.valueOf(Optional.ofNullable(roomDO).map(RoomDO::getPayType).orElse(newRoom.getPayType())) : payTypeEnum;
        return RequestChatRespVO.builder()
                .leagueId(reqVO.getLeagueId())
                .roomId(Optional.ofNullable(roomDO).map(RoomDO::getId).orElse(newRoom.getId()))
                .payType(payTypeEnum)
                .build();
    }

}
