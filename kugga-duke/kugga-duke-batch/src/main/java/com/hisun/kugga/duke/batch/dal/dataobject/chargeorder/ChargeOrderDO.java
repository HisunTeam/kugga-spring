package com.hisun.kugga.duke.batch.dal.dataobject.chargeorder;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值订单 DO
 *
 * @author 芋道源码
 */
@TableName("duke_charge_order")
@KeySequence("duke_charge_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargeOrderDO extends BaseDO {

    /**
     * Order ID
     */
    @TableId
    private Long id;
    /**
     * Internal order number
     */
    private String appOrderNo;
    /**
     * Wallet order number
     */
    private String walletOrderNo;
    /**
     * User ID
     */
    private Long userId;
    /**
     * Wallet account
     */
    private String accountId;
    /**
     * Recharge amount, in cents
     */
    private BigDecimal amount;
    /**
     * Transaction currency
     */
    private String currency;
    /**
     * Recharge status: init (Initialization); success (Recharge Payment Successful); failed (Transaction Failed); expired (Payment Link has Expired)
     */
    private String status;
    /**
     * Arrival time
     */
    private Date receivedTime;

}

