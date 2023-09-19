package com.hisun.kugga.duke.league.service;

import com.hisun.kugga.duke.dto.GrowthDTO;
import com.hisun.kugga.duke.league.dal.dataobject.BonusUserDO;

import com.hisun.kugga.duke.league.dal.dataobject.LeagueMemberDO;
import com.hisun.kugga.duke.league.vo.LeagueMemberJoinVO;

import java.util.List;

/**
 * @author: zhou_xiong
 */
public interface LeagueMemberService {
    /**
     * 判断用户是否是公会管理员
     *
     * @param userId
     * @param leagueId
     * @return
     */
    boolean isAdmin(Long userId, Long leagueId);

    /**
     * 用户加入的所有公会
     *
     * @param userId
     * @return
     */
    List<Long> getUserJoinLeague(Long userId);

    /**
     * 根据公会id获取公会创建者id
     *
     * @param leagueId
     * @return
     */
    Long getAdminIdByLeagueId(Long leagueId);

    /**
     * 根据公会ID查询公会里所有管理员
     *
     * @param leagueId
     * @return
     */
    List<Long> getLeagueAdmin(long leagueId);

    /**
     * 根据公会id和用户id查询
     *
     * @param leagueId
     * @param userId
     * @return
     */
    LeagueMemberDO getByLeagueIdAndUserId(Long leagueId, Long userId);

    /**
     * 用户加入公会统一接口
     *
     * @param leagueMemberJoinVO
     * @return
     */
    Integer joinLeague(LeagueMemberJoinVO leagueMemberJoinVO);

    /**
     * 符合发放红包规则的用户
     *
     * @param leagueId
     * @return
     */
    List<BonusUserDO> canBonus(Long leagueId);

    /**
     * 增加成长值，并同步升级等级
     *
     * @param growthDTO
     */
    void growthThenLevel(GrowthDTO growthDTO);
}
