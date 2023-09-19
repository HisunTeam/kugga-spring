package com.hisun.kugga.duke.league.dal.mysql;

import com.hisun.kugga.duke.league.dal.dataobject.LeagueGrowthLevelDO;
import com.hisun.kugga.duke.league.vo.rule.LeagueLevelRespVO;
import com.hisun.kugga.duke.league.api.dto.UserGrowthLevelDTO;
import com.hisun.kugga.duke.league.vo.rule.LeagueUserLevelRespVO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公会成长等级 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LeagueGrowthLevelMapper extends BaseMapperX<LeagueGrowthLevelDO> {


    List<LeagueLevelRespVO> selectLevelByLeagueId(@Param("leagueId") Long leagueId);


    UserGrowthLevelDTO getUserGrowthInfoByLeagueIdAndUserId(@Param("leagueId") Long leagueId, @Param("userId") Long userId);

    /**
     * 初始化原始公会等级
     * @param leagueId
     * @return
     */
    int insertInitInfo(@Param("leagueId") Long leagueId);

    /**
     * 查询个人信息等级页面
     * @param leagueId
     * @param userId
     * @return
     */
    LeagueUserLevelRespVO getUserLeagueGrowthInfos(@Param("leagueId") Long leagueId, @Param("userId") Long userId);
}
