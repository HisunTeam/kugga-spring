package com.hisun.kugga.duke.batch.dal.dataobject.userbill;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * User Bill Data Object (DO)
 *
 * Author: zhou_xiong
 */
@TableName("duke_user_bill")
@KeySequence("duke_user_bill_seq") // Used for Oracle, PostgreSQL, Kingbase, DB2, H2 databases for auto-incrementing primary keys. Not needed for databases like MySQL.
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBillDO extends BaseDO {

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
    private String walletOrderNo;
    /**
     * User ID
     */
    private Long userId;
    /**
     * Bill Amount
     */
    private BigDecimal amount;
    /**
     * Fee
     */
    private BigDecimal fee;
    /**
     * Bill Status: I - Initial, W - In Transit, S - Success, F - Failure
     */
    private String status;
    /**
     * Remark
     */
    private String remark;
}
