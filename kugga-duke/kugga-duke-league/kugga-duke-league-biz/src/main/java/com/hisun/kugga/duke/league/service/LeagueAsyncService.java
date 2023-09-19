package com.hisun.kugga.duke.league.service;

import com.hisun.kugga.duke.league.bo.InviteJoinLeagueNoticeBO;
import com.hisun.kugga.duke.league.dal.dataobject.views.LeagueViewsDO;

/**
 * @author zuocheng
 * PS:为什么类名要用 Async呢,主要是想这里的方法以后异步调用(毕竟向发送通知这种,肯定不能让主流程失败不)
 */
public interface LeagueAsyncService {
    /**
     * 首次加么公会,向公会创建者发送公会通知
     *
     * @param joinUserId    加入公会者的用户ID
     * @param leagueViewsDO
     */
    void firstJoinLeagueNotice(Long joinUserId, LeagueViewsDO leagueViewsDO);

    /**
     * 首个加入公会的分账通知
     *
     * @param joinUserId
     * @param leagueViewsDO
     */
    void firstJoinLeagueLedgerNotice(Long joinUserId, LeagueViewsDO leagueViewsDO);

    /**
     * 公会邀请,给注册用户发送邀请通知
     *
     * @param reqBO
     */
    void inviteJoinLeagueNotice(InviteJoinLeagueNoticeBO reqBO);
}
