package com.hisun.kugga.duke.league.dal.mysql;


import com.hisun.kugga.duke.league.dal.dataobject.LeagueUnfinishedDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 待完成公会 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LeagueUnfinishedMapper extends BaseMapperX<LeagueUnfinishedDO> {
    /**
     * 根据公会名称查询公会信息
     *
     * @param name
     * @return
     */
    default LeagueUnfinishedDO selectByLeagueName(String name) {
        return selectOne(new LambdaQueryWrapperX<LeagueUnfinishedDO>()
                .eq(LeagueUnfinishedDO::getLeagueName, name)
        );
    }

    /**
     * 根据用户ID与公会名称,查询是否已存在待创建的公会
     *
     * @param userId
     * @param leagueName
     * @return
     */
    default LeagueUnfinishedDO selectByUserAndName(Long userId, String leagueName) {
        return selectOne(new LambdaQueryWrapperX<LeagueUnfinishedDO>()
                .eq(LeagueUnfinishedDO::getLeagueName, leagueName)
                .eq(LeagueUnfinishedDO::getUserId, userId)
                .eq(LeagueUnfinishedDO::getActivationStatus, false)
                .eq(LeagueUnfinishedDO::getPreStatus, false)
        );
    }

}
