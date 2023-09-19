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
 * 公会成员订阅 Mapper
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
     * 查询某天即将过期的 订阅的套餐
     *
     * @param expireTime
     * @param status       订阅状态
     * @param expireStatus 真实订阅状态(过期状态)
     * @return
     */
    List<LeagueSubscribeDO> selectSubscribes(@Param("expireTime") String expireTime,
                                             @Param("status") boolean status,
                                             @Param("expireStatus") boolean expireStatus);

    /**
     * 查询3天后续定的 订阅的套餐
     * (比上面多了一个邮箱、公会名)
     *
     * @param expireTime
     * @param status
     * @return
     */
    List<LeagueSubscribeDO> selectSubscribes2(@Param("expireTime") String expireTime, @Param("status") boolean status);

    /**
     * 根据订阅类型查询公会当前最新价格
     *
     * @param leagueId
     * @param subscribeType
     * @return
     */
    BigDecimal getPriceByLeagueIdAndType(@Param("leagueId") Long leagueId, @Param("subscribeType") String subscribeType);

    /**
     * 根据id修改订阅状态  取消订阅
     *
     * @param id
     */
    default void quitSubscribe(Long id) {
        updateById(new LeagueSubscribeDO().setId(id).setStatus(false));
    }

    /**
     * 根据id修改订阅状态  取消订阅状态
     *
     * @param id
     */
    default void updateSubscribeStatusToFalse(Long id) {
        updateById(new LeagueSubscribeDO().setId(id).setStatus(false));
    }

    /**
     * 根据id修改订阅过期状态
     *
     * @param id
     */
    default void updateSubscribeExpireStatusToTrue(Long id) {
        updateById(new LeagueSubscribeDO().setId(id).setExpireStatus(true));
    }

    default void subscriptionRenewal(Long id, LocalDateTime expireDate) {
        updateById(new LeagueSubscribeDO().setId(id).setExpireTime(expireDate));
    }

    /**
     * 取消订阅后删除公会成员 -- 逻辑删除
     *
     * @param leagueId
     * @param userId
     */
    void deleteLeagueMember(@Param("leagueId") Long leagueId, @Param("userId") Long userId, @Param("quitTime") LocalDateTime quitTime);
}
