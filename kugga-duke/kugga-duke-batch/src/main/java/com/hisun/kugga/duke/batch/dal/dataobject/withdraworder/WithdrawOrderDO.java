package com.hisun.kugga.duke.batch.dal.dataobject.withdraworder;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.WithdrawChannel;
import com.hisun.kugga.duke.enums.WithdrawStatus;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Withdrawal Order DO
 *
 * @author 芋道源码
 */
@TableName("duke_withdraw_order")
@KeySequence("duke_withdraw_order_seq") // Used for Oracle, PostgreSQL, Kingbase, DB2, H2 databases for auto-incrementing primary keys. Not needed for databases like MySQL.
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawOrderDO extends BaseDO {

    /**
     * Order ID
     */
    @TableId
    private Long id;
    /**
     * Internal Order Number
     */
    private String appOrderNo;
    /**
     * Wallet Order Number
     */
    private String walletOrderNo;
    /**
     * User ID
     */
    private Long userId;
    /**
     * Wallet Account
     */
    private String accountId;
    /**
     * Withdrawal Amount, in units: yuan
     */
    private BigDecimal amount;
    /**
     * Actual Amount Received
     */
    private BigDecimal actualAmount;
    /**
     * Fee, in units: yuan
     */
    private BigDecimal fee;
    /**
     * Withdrawal Method: paypal
     */
    private WithdrawChannel withdrawChannel;
    /**
     * Withdrawal Card Number
     */
    private String cardNo;
    /**
     * Currency
     */
    private String currency;
    /**
     * Withdrawal Status: draft (Awaiting Credit); success (Successful); failed (Transaction Failed)
     */
    private WithdrawStatus status;
    /**
     * Credit Time
     */
    private Date receivedTime;
}
