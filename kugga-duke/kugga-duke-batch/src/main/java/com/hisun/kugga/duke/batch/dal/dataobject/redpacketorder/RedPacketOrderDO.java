package com.hisun.kugga.duke.batch.dal.dataobject.redpacketorder;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.AccountType;
import com.hisun.kugga.duke.enums.RedPacketOrderStatus;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * Red Packet Order Data Object (DO)
 *
 * Author: zhou_xiong
 */
@TableName("duke_red_packet_order")
@KeySequence("duke_red_packet_order_seq") // Used for Oracle, PostgreSQL, Kingbase, DB2, H2 databases for auto-incrementing primary keys. Not needed for databases like MySQL.
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedPacketOrderDO extends BaseDO {

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
     * Payer ID
     */
    private Long payerId;
    /**
     * Payer Account Type
     */
    private AccountType accountType;
    /**
     * Wallet Account
     */
    private String accountId;
    /**
     * Payment Method [balance: Balance Payment, paypal: PayPal Payment]
     */
    private String payChannel;
    /**
     * Total Red Packet Amount
     */
    private BigDecimal amount;
    /**
     * Handling Fee
     */
    private BigDecimal fee;
    /**
     * Currency
     */
    private String currency;
    /**
     * Status: draft (Pending Distribution); processing (Processing); partial_success (Partial Distribution Success); full_success (All Distributed Successfully); failed (Refunded)
     */
    private RedPacketOrderStatus status;
    /**
     * Remark
     */
    private String remark;
    /**
     * Completion Time
     */
    private String completeTime;
}
