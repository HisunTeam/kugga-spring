package com.hisun.kugga.duke.batch.dal.dataobject.leaguejoin;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 公会加入 DO
 *
 * @author 芋道源码
 */
@TableName("duke_task_league_join")
@KeySequence("duke_league_join_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueJoinDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 所加入公会id
     */
    private Long leagueId;
    /**
     * 业务状态 0初始化、1已同意、2已拒绝、3已过期
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
     * 支付状态 0免费 1未支付 2已支付
     */
    private Integer payStatus;
    /**
     * 金额状态 3已分账 5已退款
     */
    private Integer amountStatus;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
    /**
     * 加入理由
     */
    private String joinReason;
    /**
     * 公会创建者id ，公会创建者id用于付费加入时去分账
     */
    private Long leagueCreateId;
    /**
     * 是否需要管理员审批  true:是 false:否
     */
    private Boolean enabledAdminApproval;
    /**
     * 是否付费 1付费，0免费
     */
    private Boolean payFlag;
}
