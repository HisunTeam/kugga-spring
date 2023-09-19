package com.hisun.kugga.duke.pay.api.leagueaccount;

import com.hisun.kugga.duke.pay.service.leagueaccount.LeagueAccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
@Service
public class LeagueAccountApiImpl implements LeagueAccountApi {
    @Resource
    private LeagueAccountService leagueAccountService;

    @Override
    public BigDecimal getLeagueBalance(Long leagueId) {
        return leagueAccountService.getLeagueBalance(leagueId);
    }

    @Override
    public int updateLeagueAccount(Long leagueId, String accountId) {
        return leagueAccountService.updateLeagueAccount(leagueId, accountId);
    }
}
