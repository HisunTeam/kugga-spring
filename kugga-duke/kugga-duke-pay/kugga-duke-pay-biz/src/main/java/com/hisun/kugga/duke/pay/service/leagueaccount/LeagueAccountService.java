package com.hisun.kugga.duke.pay.service.leagueaccount;

import com.hisun.kugga.duke.pay.dal.dataobject.leagueaccount.LeagueAccountDO;

import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
public interface LeagueAccountService {
    /**
     * 更新公会账户金额
     *
     * @param leagueId
     * @param amount
     */
    void updateAccount(Long leagueId, BigDecimal amount);

    /**
     * 通过leagueId找
     *
     * @param leagueId
     * @return
     */
    LeagueAccountDO getLeagueAccountByLeagueId(Long leagueId);

    /**
     * 查询公会余额
     *
     * @param leagueId
     * @return
     */
    BigDecimal getLeagueBalance(Long leagueId);

    int updateLeagueAccount(Long leagueId, String accountId);
}
