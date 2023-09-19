package com.hisun.kugga.duke.league.dal.mysql;

import com.hisun.kugga.duke.league.dal.dataobject.LeagueInviteUrlBindDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * <p>
 * 邀请链绑定表(绑定链的发起者与邀请类型) Mapper 接口
 * </p>
 *
 * @author zuocheng
 * @since 2022-07-26 19:25:54
 */
@Mapper
public interface LeagueInviteUrlBindMapper extends BaseMapperX<LeagueInviteUrlBindDO> {
    /**
     * 根据UUID查询邀请链绑定关系
     *
     * @param uuid
     * @return
     */
    default LeagueInviteUrlBindDO selectOneByUuid(String uuid) {
        return selectOne(new LambdaQueryWrapperX<LeagueInviteUrlBindDO>()
                .eq(LeagueInviteUrlBindDO::getUuid, uuid)
                .orderByDesc(LeagueInviteUrlBindDO::getId));
    }

    /**
     * 根据公会ID与用户ID查询是否存在生效的邀请链接
     *
     * @param leagueId
     * @param userId
     * @return
     */
    default LeagueInviteUrlBindDO selectOneByLeagueId(long leagueId, long userId) {
        return selectOne(new LambdaQueryWrapperX<LeagueInviteUrlBindDO>()
                .eq(LeagueInviteUrlBindDO::getLeagueId, leagueId)
                .eq(LeagueInviteUrlBindDO::getUserId, userId)
                .eq(LeagueInviteUrlBindDO::getExpireStatus, false)
                .ge(LeagueInviteUrlBindDO::getExpireTime, LocalDateTime.now().withNano(0))
        );
    }
}
