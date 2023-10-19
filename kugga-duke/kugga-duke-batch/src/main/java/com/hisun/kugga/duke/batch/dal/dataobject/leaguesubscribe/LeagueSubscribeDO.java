package com.hisun.kugga.duke.batch.dal.dataobject.leaguesubscribe;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Guild Member Subscription Data Object
 *
 * @author 芋道源码
 */
@TableName("duke_league_subscribe")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueSubscribeDO extends BaseDO {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * User ID
     */
    private Long userId;
    /**
     * Guild ID
     */
    private Long leagueId;
    /**
     * Subscription Type
     */
    private String subscribeType;
    /**
     * Subscription Price
     */
    private BigDecimal price;
    /**
     * Subscription Expiration Time
     */
    private LocalDateTime expireTime;
    /**
     * Subscription Status: true (subscribed), false (unsubscribed)
     */
    private Boolean status;
    /**
     * Expiration Status
     * Possible values:
     * - Subscription Status true, Expiration Status false (Normal)
     * - Subscription Status false, Expiration Status false (Unsubscribed but not expired)
     * - Subscription Status false, Expiration Status true (Unsubscribed and expired)
     * - Status is false, but the expiration time has not yet been reached (false)
     */
    private Boolean expireStatus;

    @TableField(exist = false)
    private String email;
    @TableField(exist = false)
    private String leagueName;
}
