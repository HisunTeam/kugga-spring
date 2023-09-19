package com.hisun.kugga.duke.league.bo.rule;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:37
 */
@Data
public class LeagueRuleBO {
    /**
     * 公会ID
     */
    private Long leagueId;
    /**
     * 是否开通认证
     */
    private Boolean authFlag;
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
    private Boolean isUserJoin;
    /**
     * 更新人
     */
    private Long updateUserId;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建时间
     */
    private Date createTime;
}
