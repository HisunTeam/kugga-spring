package com.hisun.kugga.duke.league.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 创建公会订单表 DO
 *
 * @author 芋道源码
 */
@TableName("duke_task_create_league")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreateLeagueDO {
    /**
     * 公会ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 公会id
     */
    private Long leagueId;
    /**
     * 业务状态 0初始化、1创建成功
     */
    private Integer businessStatus;
    /**
     * 订单号
     */
    private String appOrderNo;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 支付状态 0未支付 1已支付
     */
    private Integer payStatus;
    /**
     * 金额状态 0默认值 2已分账 3已退款
     */
    private Integer amountStatus;
    /**
     * 是否付费 1付费，0免费
     */
    private Boolean payFlag;
    /**
     * 被分账的用户
     */
    private Long ledgerUserId;
    /**
     * 分账时间
     */
    private LocalDateTime ledgerTime;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 删除状态 true:已删除 false:未删除
     */
    @TableLogic
    private Boolean deleted;
}
