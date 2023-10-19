package com.hisun.kugga.duke.batch.dal.mysql.leaguesubscribe;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.batch.dal.dataobject.leaguesubscribe.LeagueSubscribeDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Guild Member Subscription Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LeagueSubscribeMapper extends BaseMapperX<LeagueSubscribeDO> {

    default LeagueSubscribeDO getByLeagueIdAndUserId(Long leagueId, Long userId) {
        return selectOne(new LambdaQueryWrapper<LeagueSubscribeDO>()
                .eq(LeagueSubscribeDO::getLeagueId, leagueId)
                .eq(LeagueSubscribeDO::getUserId, userId)
        );
    }

    /**
     * Query subscription packages that are about to expire on a certain day
     *
     * @param expireTime
     * @param status       Subscription status
     * @param expireStatus Real subscription status (expired status)
     * @return
     */
    List<LeagueSubscribeDO> selectSubscribes(@Param("expireTime") String expireTime,
                                             @Param("status") boolean status,
                                             @Param("expireStatus") boolean expireStatus);

    /**
     * Query subscription packages that will be renewed in 3 days
     * (Includes additional fields like email and guild name compared to the above method)
     *
     * @param expireTime
     * @param status
     * @return
     */
    List<LeagueSubscribeDO> selectSubscribes2(@Param("expireTime") String expireTime, @Param("status") boolean status);

    /**
     * Get the current latest price for a guild's subscription type
     *
     * @param leagueId
     * @param subscribeType
     * @return
     */
    BigDecimal getPriceByLeagueIdAndType(@Param("leagueId") Long leagueId, @Param("subscribeType") String subscribeType);

    /**
     * Update subscription status to cancel subscription by ID
     *
     * @param id
     */
    default void quitSubscribe(Long id) {
        updateById(new LeagueSubscribeDO().setId(id).setStatus(false));
    }

    /**
     * Update subscription status to false by ID
     *
     * @param id
     */
    default void updateSubscribeStatusToFalse(Long id) {
        updateById(new LeagueSubscribeDO().setId(id).setStatus(false));
    }

    /**
     * Update subscription expire status to true by ID
     *
     * @param id
     */
    default void updateSubscribeExpireStatusToTrue(Long id) {
        updateById(new LeagueSubscribeDO().setId(id).setExpireStatus(true));
    }

    /**
     * Renew a subscription by ID with a new expiration date
     *
     * @param id
     * @param expireDate
     */
    default void subscriptionRenewal(Long id, LocalDateTime expireDate) {
        updateById(new LeagueSubscribeDO().setId(id).setExpireTime(expireDate));
    }

    /**
     * Delete guild member after canceling subscription - logical deletion
     *
     * @param leagueId
     * @param userId
     * @param quitTime
     */
    void deleteLeagueMember(@Param("leagueId") Long leagueId, @Param("userId") Long userId, @Param("quitTime") LocalDateTime quitTime);
}
