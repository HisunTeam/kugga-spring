package com.hisun.kugga.duke.pay.api.leagueaccount;

import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
public interface LeagueAccountApi {
    BigDecimal getLeagueBalance(Long leagueId);

    int updateLeagueAccount(Long leagueId, String accountId);
}
