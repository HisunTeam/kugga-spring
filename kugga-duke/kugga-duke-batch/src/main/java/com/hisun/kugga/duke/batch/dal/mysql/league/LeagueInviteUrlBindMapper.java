package com.hisun.kugga.duke.batch.dal.mysql.league;

import com.hisun.kugga.duke.batch.dal.dataobject.league.LeagueInviteUrlBindDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * Mapper Interface for the Invitation Link Binding Table (Binding Initiator and Invitation Type)
 * </p>
 *
 * @author zuocheng
 * @since 2022-07-26 19:25:54
 */
@Mapper
public interface LeagueInviteUrlBindMapper extends BaseMapperX<LeagueInviteUrlBindDO> {
    /**
     * Query data that has reached the expiration time but is still in a non-expired state
     * To avoid excessive data pressure, only query data within a certain time frame (here, two weeks), up to 1000 records, to prevent data from becoming too large
     *
     * @param nowTime
     * @return
     */
    default List<LeagueInviteUrlBindDO> selectShouldExpire(LocalDateTime nowTime) {
        return selectList(new LambdaQueryWrapperX<LeagueInviteUrlBindDO>()
                .betweenIfPresent(LeagueInviteUrlBindDO::getCreateTime, nowTime.minusWeeks(2), nowTime)
                .eq(LeagueInviteUrlBindDO::getExpireStatus, false)
                .le(LeagueInviteUrlBindDO::getExpireTime, nowTime)
                .orderByAsc(LeagueInviteUrlBindDO::getCreateTime)
                .last("limit 1000"));
    }
}
