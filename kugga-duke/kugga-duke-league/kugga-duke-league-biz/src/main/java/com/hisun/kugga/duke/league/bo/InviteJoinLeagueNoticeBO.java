package com.hisun.kugga.duke.league.bo;

import lombok.Data;

/**
 * @author zuocheng
 */
@Data
public class InviteJoinLeagueNoticeBO {
    /**
     * 公会ID
     */
    private Long leagueId;
    /**
     * 公会名称
     */
    private String leagueName;
    /**
     * 邀请人用户ID
     */
    private Long inviteUserId;
    /**
     * 邀请连接
     */
    private String inviteUrl;

    /**
     * 被邀请人用户ID
     */
    private Long userId;
}
