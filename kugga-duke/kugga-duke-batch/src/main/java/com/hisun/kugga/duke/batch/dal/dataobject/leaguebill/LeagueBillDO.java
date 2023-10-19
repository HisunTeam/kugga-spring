package com.hisun.kugga.duke.batch.dal.dataobject.leaguebill;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * Guild Bill Data Object
 *
 * @author zhou_xiong
 */
@TableName("duke_league_bill")
@KeySequence("duke_league_bill_seq") // Used for primary key auto-increment in Oracle, PostgreSQL, Kingbase, DB2, H2 databases. Not needed for MySQL, etc.
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueBillDO extends BaseDO {

    /**
     * Transaction ID
     */
    @TableId
    private Long id;
    /**
     * Bill Number
     */
    private String billNo;
    /**
     * Wallet Order Number
     */
    private String WalletOrderNo;
    /**
     * Guild ID
     */
    private Long leagueId;
    /**
     * Transaction Amount
     */
    private BigDecimal amount;
    /**
     * Bill Status: U (Initial), W (In Transit), S (Success), F (Failure)
     */
    private String status;
    /**
     * Remark
     */
    private String remark;

}
