package com.hisun.kugga.duke.league.service;

import com.hisun.kugga.duke.league.vo.rule.LeagueJoinRuleVO;
import com.hisun.kugga.duke.league.vo.rule.LeagueRuleVO;
import com.hisun.kugga.framework.common.pojo.CommonResult;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:43
 */
public interface LeagueRuleService {

    /**
     * 根据公会ID查询公会规则
     */
    LeagueRuleVO get(Long id);

    /**
     * 修改公会规则
     */
    CommonResult update(LeagueRuleVO vo);

    String updateAvatar(Long id, byte[] bytes, String originalFilename, String contentType);

    /**
     * getLeagueJoinInfo
     *
     * @param leagueId
     * @return
     */
    LeagueRuleVO getLeagueRuleInfo(Long leagueId);

    LeagueJoinRuleVO getLeagueJoinRule(Long leagueId);
}
