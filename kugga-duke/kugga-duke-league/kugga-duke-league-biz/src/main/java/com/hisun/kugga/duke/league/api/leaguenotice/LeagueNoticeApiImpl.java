package com.hisun.kugga.duke.league.api.leaguenotice;

import com.hisun.kugga.duke.league.api.dto.leaguenotice.LeagueNoticeDTO;
import com.hisun.kugga.duke.league.service.LeagueNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: zhou_xiong
 */
@Service
public class LeagueNoticeApiImpl implements LeagueNoticeApi {
    @Resource
    private LeagueNoticeService leagueNoticeService;

    @Override
    public void notice(LeagueNoticeDTO leagueNoticeDTO) {
        leagueNoticeService.insert(leagueNoticeDTO);
    }
}
