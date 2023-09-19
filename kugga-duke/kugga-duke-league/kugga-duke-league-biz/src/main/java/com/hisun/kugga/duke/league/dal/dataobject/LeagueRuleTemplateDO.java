package com.hisun.kugga.duke.league.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 公会规则模板 DO
 *
 * @author 芋道源码
 */
@TableName("duke_league_rule_template")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueRuleTemplateDO {
    /**
     * 公会规则模板表ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
    private Boolean enabledUserJoin;

    /**
     * 更新人
     */
    private Long updateUserId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
