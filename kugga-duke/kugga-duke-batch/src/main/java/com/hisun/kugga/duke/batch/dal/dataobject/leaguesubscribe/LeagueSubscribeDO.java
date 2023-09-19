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
 * 公会成员订阅 DO
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
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 订阅类型
     */
    private String subscribeType;
    /**
     * 订阅价格
     */
    private BigDecimal price;
    /**
     * 订阅过期时间
     */
    private LocalDateTime expireTime;
    /**
     * 订阅状态 true 订阅中 false取消订阅
     */
    private Boolean status;
    /**
     * 过期状态
     * 可能存在
     * 订阅状态 true过期状态false  正常状态
     * 订阅状态 false过期状态false 取消订阅但是未过期
     * 订阅状态 false过期状态true   取消订阅已过期
     * status为false，但是过期时间还没到，此时为false
     */
    private Boolean expireStatus;

    @TableField(exist = false)
    private String email;
    @TableField(exist = false)
    private String leagueName;
}
