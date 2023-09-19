package com.hisun.kugga.duke.league.api.dto.task;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zc654
 */
@Data
public class LeagueRuleDTO {
    /**
     * 公会ID
     */
    private Long leagueId;
    /**
     * 是否开通认证
     */
    private Boolean enabledAuth;
    /**
     * 认证价格
     */
    private BigDecimal authPrice;
    /**
     * 写推荐报告价格
     */
    private BigDecimal reportPrice;
    /**
     * 聊天价格
     */
    private BigDecimal chatPrice;
    /**
     * 是否允许用户加入
     */
    private Boolean enabledUserJoin;
    /**
     * 是否需要管理员审批
     */
    private Boolean enabledAdminApproval;
    /**
     * 用户加入价格
     */
    private BigDecimal userJoinPrice;
    /**
     * 是否允许热贴检索开关 （false:不允许 true:允许）
     */
    private Boolean postsSearchSwitch;
}
