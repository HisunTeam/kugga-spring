package com.hisun.kugga.duke.league.service;

import com.hisun.kugga.duke.league.vo.rule.LeagueLevelReqVO;
import com.hisun.kugga.duke.league.vo.rule.LeagueLevelRespVO;
import com.hisun.kugga.duke.league.api.dto.UserGrowthLevelDTO;
import com.hisun.kugga.duke.league.vo.rule.LeagueUserLevelRespVO;

import java.util.List;

/**
 * 公会加入 Service 接口
 *
 * @author 芋道源码
 */
public interface LeagueGrowthLevelService {
    /**
     *
     * @param reqVO
     * @return
     */
    List<LeagueLevelRespVO> getLeagueLevel(LeagueLevelReqVO reqVO);

    /**
     *
     * @param updateVo
     */
    void updateLeagueLevelName(LeagueLevelReqVO updateVo);

    /**
     * 根据用户id和公会id查询 在该公会的等级和名称
     * @param leagueId
     * @param userId
     * @return
     */
    UserGrowthLevelDTO getUserGrowthInfo(Long leagueId, Long userId);

    /**
     * 查询个人信息等级页面
     * @param leagueId
     * @param userId
     * @return
     */
    LeagueUserLevelRespVO getUserLeagueGrowthInfos(Long leagueId, Long userId);
}
