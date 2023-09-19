package com.hisun.kugga.duke.league.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 待完成公会 DO
 *
 * @author 芋道源码
 */
@TableName("duke_league_unfinished")
@Data
@EqualsAndHashCode()
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueUnfinishedDO {

    /**
     * 公会ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 预创建状态：true创建完成、 false:未创建
     */
    private Boolean preStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建者
     */
    private Long userId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 公会创建是否已完成: false:未完成 true:已完成
     */
    private Boolean activationStatus;

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

    /**
     * 删除状态 true:已删除 false:未删除
     */
    @TableLogic
    private Boolean deleted;
}
