package com.hisun.kugga.duke.chat.dal.mysql;

import com.hisun.kugga.duke.chat.controller.app.vo.roommember.RoomMemberPageReqVO;
import com.hisun.kugga.duke.chat.dal.dataobject.RoomMemberDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 聊天室成员 Mapper
 *
 * @author toi
 */
@Mapper
public interface RoomMemberMapper extends BaseMapperX<RoomMemberDO> {

    default int insertOne(RoomMemberDO entity) {
        return insert(entity);
    }

    default long checkUserIdRoom(Long userId, Long roomId) {
        return selectCount(new LambdaQueryWrapperX<RoomMemberDO>()
                .eq(RoomMemberDO::getRoomId, roomId)
                .eq(RoomMemberDO::getUserId, userId));
    }

    default List<RoomMemberDO> selectAllRoomsByUserId(Long userId) {
        return selectList(new LambdaQueryWrapperX<RoomMemberDO>()
                .eq(RoomMemberDO::getUserId, userId));
    }

    default RoomMemberDO selectRoomsByUserId(Long roomId, Long userId) {
        return selectOne(new LambdaQueryWrapperX<RoomMemberDO>()
                .eq(RoomMemberDO::getRoomId, roomId)
                .eq(RoomMemberDO::getUserId, userId));
    }

    default PageResult<RoomMemberDO> selectPage(RoomMemberPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RoomMemberDO>()
                .eqIfPresent(RoomMemberDO::getRoomId, reqVO.getRoomId())
                .eqIfPresent(RoomMemberDO::getUserId, reqVO.getUserId())
                .likeIfPresent(RoomMemberDO::getNickname, reqVO.getNickname())
                .eqIfPresent(RoomMemberDO::getRole, reqVO.getRole())
                .betweenIfPresent(RoomMemberDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(RoomMemberDO::getId));
    }

}
