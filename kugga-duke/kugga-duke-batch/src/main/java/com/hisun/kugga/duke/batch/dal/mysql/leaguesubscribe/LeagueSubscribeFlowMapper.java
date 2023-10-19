package com.hisun.kugga.duke.batch.dal.mysql.leaguesubscribe;

import com.hisun.kugga.duke.batch.dal.dataobject.leaguesubscribe.LeagueSubscribeDO;
import com.hisun.kugga.duke.batch.dal.dataobject.leaguesubscribe.LeagueSubscribeFlowDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Guild Subscription Transactions Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LeagueSubscribeFlowMapper extends BaseMapperX<LeagueSubscribeFlowDO> {

    /**
     * Check for the existence of transaction records
     *
     * @param subscribe
     * @return
     */
    LeagueSubscribeFlowDO selectExistFlow(@Param("subscribe") LeagueSubscribeDO subscribe, @Param("expireTime") String expireTime);
}
