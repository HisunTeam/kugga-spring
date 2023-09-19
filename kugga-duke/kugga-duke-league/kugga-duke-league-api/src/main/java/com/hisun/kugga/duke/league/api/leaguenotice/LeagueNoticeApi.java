package com.hisun.kugga.duke.league.api.leaguenotice;

import com.hisun.kugga.duke.league.api.dto.leaguenotice.LeagueNoticeDTO;

/**
 * @author: zhou_xiong
 */
public interface LeagueNoticeApi {
    /**
     * 发公告栏消息
     *
     * @param leagueNoticeDTO
     */
    void notice(LeagueNoticeDTO leagueNoticeDTO);
}
