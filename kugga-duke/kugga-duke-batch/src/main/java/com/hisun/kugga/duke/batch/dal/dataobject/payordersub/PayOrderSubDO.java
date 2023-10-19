package com.hisun.kugga.duke.batch.dal.dataobject.payordersub;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.AccountType;
import com.hisun.kugga.duke.enums.OrderType;
import com.hisun.kugga.duke.enums.PayOrderSubStatus;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * Payment Order Subset DO
 *
 * @author 芋道源码
 */
@TableName("duke_pay_order_sub")
@KeySequence("duke_pay_order_sub_seq") // Used for Oracle, PostgreSQL, Kingbase, DB2, H2 databases for auto-incrementing primary keys. Not needed for databases like MySQL.
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayOrderSubDO extends BaseDO {
    /**
     * Order Subset ID
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
     * Wallet Split Record Number
     */
    private String splitNo;
    /**
     * Transaction Type
     */
    private OrderType orderType;
    /**
     * Receiver ID
     */
    private Long receiverId;
    /**
     * Receiver Account Type
     */
    private AccountType accountType;
    /**
     * Receiver Wallet Account
     */
    private String accountId;
    /**
     * Received Amount
     */
    private BigDecimal amount;
    /**
     * Split Status
     */
    private PayOrderSubStatus status;
    /**
     * Remark
     */
    private String remark;
}
