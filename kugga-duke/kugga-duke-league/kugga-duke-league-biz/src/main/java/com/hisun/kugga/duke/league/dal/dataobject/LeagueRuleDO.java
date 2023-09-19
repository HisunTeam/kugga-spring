package com.hisun.kugga.duke.league.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 公会规则 DO
 *
 * @author 芋道源码
 */
@TableName("duke_league_rule")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueRuleDO {
    /**
     * 公会ID(PS:与duke_league表的ID一致)
     */
    @TableId(value = "league_id", type = IdType.INPUT)
    private Long leagueId;

    /**
     * 是否开通认证 true:是 false:否
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
     * 是否允许用户加入 true:是 false:否
     */
    private Boolean enabledUserJoin;

    /**
     * 是否需要管理员审批  true:是 false:否
     */
    private Boolean enabledAdminApproval;
    /**
     * 是否允许热贴检索开关  false:不允许 true:允许
     */
    private Boolean postsSearchSwitch;

    /**
     * 更新人
     */
    private Long updateUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 用户加入价格 Deprecated
     */
    private BigDecimal userJoinPrice;
    /**
     * 订阅选择状况 1生效，0不生效 如1_0_0_0
     * 按月、季、年、永久的顺序排列，下划线分割
     */
    private String subscribeSelect;
    /**
     * 按月订阅价格
     */
    private BigDecimal subscribeMonthPrice;
    /**
     * 按季度订阅价格
     */
    private BigDecimal subscribeQuarterPrice;
    /**
     * 按年订阅价格
     */
    private BigDecimal subscribeYearPrice;
    /**
     * 永久订阅价格
     */
    private BigDecimal subscribeForeverPrice;

}
