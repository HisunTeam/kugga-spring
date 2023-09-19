package com.hisun.kugga.duke.league.dal.mysql;

import com.hisun.kugga.duke.league.dal.dataobject.LeagueRuleDO;
import com.hisun.kugga.duke.league.vo.rule.LeagueRuleVO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 公会规则 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LeagueRuleMapper extends BaseMapperX<LeagueRuleDO> {

    default LeagueRuleDO getLeagueRuleInfoByLeagueId(Long leagueId) {
        return selectOne(new LambdaQueryWrapperX<LeagueRuleDO>()
                .eq(LeagueRuleDO::getLeagueId, leagueId)
        );
    }

    /**
     * 公会规则+公会名称头像
     *
     * @param leagueId
     * @return
     */
    LeagueRuleVO getLeagueRuleInfoByLeagueId2(@Param("leagueId") Long leagueId);
}
