package com.hisun.kugga.duke.batch.dal.dataobject.leaguejoin;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Guild Join Data Object
 *
 * @author 芋道源码
 */
@TableName("duke_task_league_join")
@KeySequence("duke_league_join_seq") // Used for primary key auto-increment in Oracle, PostgreSQL, Kingbase, DB2, H2 databases. Not needed for MySQL, etc.
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueJoinDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * User ID
     */
    private Long userId;
    /**
     * Joined Guild ID
     */
    private Long leagueId;
    /**
     * Business Status: 0 (Initialization), 1 (Approved), 2 (Rejected), 3 (Expired)
     */
    private Integer businessStatus;
    /**
     * Order Number
     */
    private String appOrderNo;
    /**
     * Amount
     */
    private BigDecimal amount;
    /**
     * Payment Status: 0 (Free), 1 (Unpaid), 2 (Paid)
     */
    private Integer payStatus;
    /**
     * Amount Status: 3 (Accounted), 5 (Refunded)
     */
    private Integer amountStatus;
    /**
     * Expiration Time
     */
    private LocalDateTime expireTime;
    /**
     * Join Reason
     */
    private String joinReason;
    /**
     * Guild Creator ID, used for splitting payments when joining with payment
     */
    private Long leagueCreateId;
    /**
     * Requires Admin Approval: true (yes), false (no)
     */
    private Boolean enabledAdminApproval;
    /**
     * Payment Flag: 1 (Paid), 0 (Free)
     */
    private Boolean payFlag;
}
