package com.hisun.kugga.duke.league.api;


import com.hisun.kugga.duke.dto.GrowthDTO;
import com.hisun.kugga.duke.league.api.dto.LeagueDTO;
import com.hisun.kugga.duke.league.api.dto.UserGrowthLevelDTO;
import com.hisun.kugga.duke.league.api.dto.leaguemember.BonusUserDTO;
import com.hisun.kugga.duke.league.api.dto.task.LeagueRuleDTO;

import java.util.List;

/**
 * @author zuocheng
 */
public interface LeagueApi {
    /**
     * 检查用户是否为公会成员
     *
     * @param leagueId
     * @param userId
     * @return
     */
    Boolean isLeagueMember(Long leagueId, Long... userId);

    /**
     * 检查用户是否为公会管理员
     *
     * @param leagueId
     * @param userId
     * @return
     */
    Boolean isLeagueAdmin(Long leagueId, Long userId);

    /**
     * 查询公会规则
     *
     * @param leagueId
     * @return
     */
    LeagueRuleDTO queryRule(Long leagueId);

    /**
     * 获取加入公会未审批数
     *
     * @return
     */
    Long getApprovalCount();

    /**
     * 根据公会ID查询,公会信息
     *
     * @param leagueId
     * @return
     */
    LeagueDTO queryLeagueById(Long leagueId);

    /**
     * 查询用户所有加入公会的id
     * @param userId
     * @return
     */
    List<Long> queryUserAllJoinLeagueId(Long userId);

    /**
     * 获取有资格收红包的公会用户
     *
     * @param leagueId
     * @return
     */
    List<BonusUserDTO> canBonus(Long leagueId);

    /**
     * 增加成长值，并同步升级等级
     *
     * @param growthDTO
     */
    void growthThenLevel(GrowthDTO growthDTO);


    /**
     * 根据用户id和公会id查询 在该公会的等级和名称
     * @param leagueId
     * @param userId
     * @return
     */
    UserGrowthLevelDTO getUserGrowthInfo(Long leagueId, Long userId);
}
