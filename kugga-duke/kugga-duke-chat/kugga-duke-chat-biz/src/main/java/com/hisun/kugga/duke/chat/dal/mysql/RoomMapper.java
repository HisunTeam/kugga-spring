package com.hisun.kugga.duke.chat.dal.mysql;

import com.hisun.kugga.duke.chat.controller.app.vo.room.RoomPageReqVO;
import com.hisun.kugga.duke.chat.dal.dataobject.RoomDO;
import com.hisun.kugga.duke.chat.enums.PayTypeEnum;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * 聊天室 Mapper
 *
 * @author toi
 */
@Mapper
public interface RoomMapper extends BaseMapperX<RoomDO> {

    default int insertOne(RoomDO entry) {
        return insert(entry);
    }

    RoomDO checkUsersInSameRoom(@Param("userId") Long userId, @Param("receiveUserId") Long receiveUserId);

    default RoomDO selectByRoomId(Long id) {
        return selectById(id);
    }

    default PageResult<RoomDO> selectPage(RoomPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RoomDO>()
                .likeIfPresent(RoomDO::getName, reqVO.getName())
                .eqIfPresent(RoomDO::getDescription, reqVO.getDescription())
                .eqIfPresent(RoomDO::getAvatar, reqVO.getAvatar())
                .eqIfPresent(RoomDO::getRoomType, reqVO.getRoomType())
                .eqIfPresent(RoomDO::getPeopleLimit, reqVO.getPeopleLimit())
                .betweenIfPresent(RoomDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(RoomDO::getId));
    }

    default int updateRoomFree(Long leagueId, Long roomId) {
        return updateById(RoomDO.builder()
                .id(roomId)
                .leagueId(leagueId)
                .payType(PayTypeEnum.FREE_CHAT.getType())
                .updateTime(LocalDateTime.now())
                .build());
    }

    default int updateRoomExpireTime(Long roomId,
                                     long leagueId,
                                     Long inviterUserId,
                                     LocalDateTime expireTime,
                                     String payChatStatus) {
        return updateById(RoomDO.builder()
                .id(roomId)
                .leagueId(leagueId)
                .inviterUserId(inviterUserId)
                .expireTime(expireTime)
                .updateTime(LocalDateTime.now())
                .payChatStatus(payChatStatus)
                .build());
    }

    default int updatePayChatStatus(Long roomId,
                                    String payChatStatus,
                                    Long receiveLeagueId,
                                    Long inviterUserId) {
        return updateById(RoomDO.builder()
                .id(roomId)
                .payChatStatus(payChatStatus)
                .leagueId(receiveLeagueId)
                .inviterUserId(inviterUserId)
                .updateTime(LocalDateTime.now())
                .build());
    }

}
