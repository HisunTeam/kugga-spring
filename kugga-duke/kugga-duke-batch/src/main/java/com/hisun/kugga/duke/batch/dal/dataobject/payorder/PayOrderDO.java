package com.hisun.kugga.duke.batch.dal.dataobject.payorder;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.AccountType;
import com.hisun.kugga.duke.enums.OrderType;
import com.hisun.kugga.duke.enums.PayChannel;
import com.hisun.kugga.duke.enums.PayOrderStatus;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * Order Data Object (DO)
 *
 * Author: zhou_xiong
 */
@TableName("duke_pay_order")
@KeySequence("duke_pay_order_seq") // Used for Oracle, PostgreSQL, Kingbase, DB2, H2 databases for auto-incrementing primary keys. Not needed for databases like MySQL.
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayOrderDO extends BaseDO {
    /**
     * ID
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
     * Order Type [1: Create Guild; 2: Guild Authentication; 3: Write Recommendation Report; 4: Initiate Chat]
     */
    private OrderType orderType;
    /**
     * Trader (User ID)
     */
    private Long payerId;
    /**
     * Payment Account Type
     */
    private AccountType accountType;
    /**
     * Wallet Account
     */
    private String accountId;
    /**
     * Payment Method
     */
    private PayChannel payChannel;
    /**
     * Transaction Amount
     */
    private BigDecimal payAmount;
    /**
     * Split Amount
     */
    private BigDecimal splitAmount;
    /**
     * Refunded Amount
     */
    private BigDecimal refundAmount;
    /**
     * Currency
     */
    private String currency;
    /**
     * Order Status: prepay (Prepayment); processing (Processing); success (Successful); failed (Transaction Failed); closed (Closed)
     */
    private PayOrderStatus status;
    /**
     * Remark
     */
    private String remark;

}
