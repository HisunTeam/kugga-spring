package com.hisun.kugga.duke.batch.dal.mysql.league;

import com.hisun.kugga.duke.batch.dal.dataobject.league.LeagueInviteUrlBindDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

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
     * 查询已到失效时间,但状态还处于未失效的状态的数据
     * 未避免数据压力过大,只查询一断时间内的数据（这里也写两周）,最多查询1000条,避免数据太大
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
