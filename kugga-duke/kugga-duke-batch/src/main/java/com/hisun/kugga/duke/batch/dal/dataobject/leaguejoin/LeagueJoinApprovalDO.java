package com.hisun.kugga.duke.batch.dal.dataobject.leaguejoin;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Guild Join Approval Data Object
 *
 * @author 芋道源码
 */
@TableName("duke_task_league_join_approval")
@KeySequence("duke_league_join_approval_seq") // Used for primary key auto-increment in Oracle, PostgreSQL, Kingbase, DB2, H2 databases. Not needed for MySQL, etc.
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueJoinApprovalDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * Guild Join Table ID
     */
    private Long businessId;
    /**
     * User ID
     */
    private Long userId;
    /**
     * Joined Guild ID
     */
    private Long leagueId;
    /**
     * Join Reason
     */
    private String joinReason;
    /**
     * Approval Status: 0 (Not Approved), 1 (Approved), 2 (Rejected), 3 (Expired)
     */
    private Integer status;
    /**
     * Expiration Time
     */
    private LocalDateTime expireTime;
}
