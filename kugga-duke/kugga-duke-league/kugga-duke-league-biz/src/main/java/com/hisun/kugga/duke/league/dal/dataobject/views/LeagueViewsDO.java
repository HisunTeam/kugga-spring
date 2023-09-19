package com.hisun.kugga.duke.league.dal.dataobject.views;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author zuocheng
 */
@Data
public class LeagueViewsDO {
    /**
     * 公会ID
     */
    private Long id;

    /**
     * 公会名称
     */
    private String leagueName;

    /**
     * 公会描述
     */
    private String leagueDesc;

    /**
     * 公会头像url
     */
    private String leagueAvatar;

    /**
     * 是否已认证 true:已认证 false:未认证
     */
    private Boolean authFlag;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 公会激活标识
     * true:已激活,false:未激活
     */
    private Boolean activationFlag;

    /**
     * 公会创建者
     */
    private Long userId;

    /**
     * 扣款订单号
     */
    private String deductOrderNo;

    /**
     * 扣款金额
     */
    private BigDecimal deductAmount;

    /**
     * 入账用户(第一个同意加入公会的成为入账用户)
     */
    private Long entryUserId;

    /**
     * 入账时间
     */
    private LocalDateTime entryTime;

    /**
     * 备注
     */
    private String rmk;
}
