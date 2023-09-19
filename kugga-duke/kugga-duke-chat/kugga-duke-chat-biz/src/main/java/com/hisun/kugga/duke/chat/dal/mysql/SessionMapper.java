package com.hisun.kugga.duke.chat.dal.mysql;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hisun.kugga.duke.chat.controller.app.vo.session.SessionRespVO;
import com.hisun.kugga.duke.chat.dal.dataobject.SessionDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

/**
 * 聊天会话列 Mapper
 *
 * @author toi
 */
@Mapper
public interface SessionMapper extends BaseMapperX<SessionDO> {

    default int insertOne(SessionDO entry) {
        return insert(entry);
    }

    /**
     * 更新用户的会话列表消息信息，同时更新非本用户的会话的未读消息
     */
    int updateNewMessageByRoomId(@Param("roomId") Long roomId,
                                 @Param("userId") Long userId,
                                 @Param("unread") Boolean unread,
                                 @Param("sessionDO") SessionDO sessionDO);


    default SessionDO getUserSelfSession(Long id, Long userId) {
        return selectOne(new LambdaQueryWrapperX<SessionDO>()
                .eq(SessionDO::getRoomId, id)
                .eq(SessionDO::getUserId, userId));
    }

    IPage<SessionRespVO> selectPageWithUserName(IPage<SessionDO> page, @Param("userId") Long userId);

    default int read(Long id, Long userId) {
        return update(SessionDO.builder()
                .unread(0)
                .readTime(LocalDateTime.now())
                .build(), new LambdaQueryWrapperX<SessionDO>()
                .eq(SessionDO::getId, id)
                .eq(SessionDO::getUserId, userId)
        );
    }

    @Select("select 1 from chat_session where unread = 1 and user_id = #{userId} limit 1")
    Integer redDot(@Param("userId") Long userId);

    default int updateSessionVisible(Long userId, Long roomId) {
        return update(SessionDO.builder()
                .visible(Boolean.TRUE)
                .updateTime(LocalDateTime.now())
                .build(), new LambdaQueryWrapperX<SessionDO>()
                .eq(SessionDO::getUserId, userId)
                .eq(SessionDO::getRoomId, roomId));
    }

}
