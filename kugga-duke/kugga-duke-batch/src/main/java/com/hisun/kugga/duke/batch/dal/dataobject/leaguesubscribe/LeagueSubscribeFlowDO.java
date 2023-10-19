package com.hisun.kugga.duke.batch.dal.dataobject.leaguesubscribe;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Guild Subscription Flow Data Object
 *
 * @author 芋道源码
 */
@TableName("duke_league_subscribe_flow")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueSubscribeFlowDO extends BaseDO {

    /**
     * Subscription Flow ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * Subscription ID
     */
    private Long subscribeId;
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
     * Order Number
     */
    private String appOrderNo;
    /**
     * Subscription Time
     */
    private LocalDateTime subscribeTime;
    /**
     * Business Status (0 initialized, 1 placed, 2 paid, 3 split, 6 failed)
     */
    private Integer businessStatus;
    /**
     * Remark (can record the reason if it's a failure)
     */
    private String remark;
}

