package com.hisun.kugga.duke.league.dal.dataobject;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 加入公会审批 DO
 *
 * @author 芋道源码
 */
@TableName("duke_task_league_join_approval")
@KeySequence("duke_league_join_approval_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueJoinApprovalDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 公会加入表id
     */
    private Long businessId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 所加入公会id
     */
    private Long leagueId;
    /**
     * 加入理由
     */
    private String joinReason;
    /**
     * 审批状态 0未审批、1已同意、2已拒绝、3已过期
     */
    private Integer status;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
}
