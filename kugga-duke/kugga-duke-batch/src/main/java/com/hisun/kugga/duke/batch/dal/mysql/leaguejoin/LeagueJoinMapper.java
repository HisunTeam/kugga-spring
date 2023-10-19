package com.hisun.kugga.duke.batch.dal.mysql.leaguejoin;


import com.hisun.kugga.duke.batch.dal.dataobject.leaguejoin.LeagueJoinDO;
import com.hisun.kugga.duke.enums.LeagueJoinApprovalTypeEnum;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Joining a Guild Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LeagueJoinMapper extends BaseMapperX<LeagueJoinDO> {

    /**
     * Query if there is a current valid application record, only considered valid if it's paid
     *
     * @param userId
     * @param leagueId
     * @param status
     * @param now
     * @return
     */
    LeagueJoinDO getCurrentValidApproval(@Param("userId") Long userId,
                                         @Param("leagueId") Long leagueId,
                                         @Param("status") int status,
                                         @Param("now") LocalDateTime now);

    /*
    -- Expired application records
    select * from duke_task_league_join
    where business_status = 0
      and pay_status != 1
      and expire_time < '2022-10-15 16:46:32'
      and deleted = false;
     */

    /**
     * Query expired application records
     *
     * @return
     */
    default List<LeagueJoinDO> selectExpireRecords() {
        return selectList(new LambdaQueryWrapperX<LeagueJoinDO>()
                .between(LeagueJoinDO::getCreateTime, LocalDateTime.now().minusDays(7L), LocalDateTime.now())
                .eq(LeagueJoinDO::getBusinessStatus, LeagueJoinApprovalTypeEnum.INIT.getValue())
                .ne(LeagueJoinDO::getPayStatus, 1)
                .le(LeagueJoinDO::getExpireTime, LocalDateTime.now())
        );
    }
}
