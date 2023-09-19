package com.hisun.kugga.duke.league.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 公会订阅流水 DO
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
     * 订阅流水id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 订阅id
     */
    private Long subscribeId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 公会id
     */
    private Long leagueId;
    /**
     * 订阅类型
     */
    private String subscribeType;
    /**
     * 订阅价格
     */
    private BigDecimal price;
    /**
     * 订阅类型
     */
    private String appOrderNo;
    /**
     * 订阅时间
     */
    private LocalDateTime subscribeTime;
    /**
     * 业务状态 (0初始化、1已下单、2已支付、3已分账、6失败)
     */
    private Integer businessStatus;
    /**
     * 备注(如果是失败，可以记录失败原因)
     */
    private String remark;
}
