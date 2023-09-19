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
 * 公会加入 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LeagueJoinMapper extends BaseMapperX<LeagueJoinDO> {

    /**
     * 查询当前是否存在有效申请记录 ,只有支付了才算有效的
     *
     * @param userId
     * @param status
     * @param now
     * @return
     */
    LeagueJoinDO getCurrentValidApproval(@Param("userId") Long userId,
                                         @Param("leagueId") Long leagueId,
                                         @Param("status") int status,
                                         @Param("now") LocalDateTime now);



    /*
    -- 过期申请记录
    select * from duke_task_league_join
    where business_status = 0
      and pay_status != 1
      and expire_time < '2022-10-15 16:46:32'
      and deleted = false;
     */

    /**
     * 查询申请过期记录
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
