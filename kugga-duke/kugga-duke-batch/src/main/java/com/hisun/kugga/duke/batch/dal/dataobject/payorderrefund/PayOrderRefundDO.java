package com.hisun.kugga.duke.batch.dal.dataobject.payorderrefund;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.PayOrderRefundStatus;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Payment Order Refund DO
 *
 * @author 芋道源码
 */
@TableName("duke_pay_order_refund")
@KeySequence("duke_pay_order_refund_seq") // Used for Oracle, PostgreSQL, Kingbase, DB2, H2 databases for auto-incrementing primary keys. Not needed for databases like MySQL.
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayOrderRefundDO extends BaseDO {

    /**
     * Refund ID
     */
    @TableId
    private Long id;
    /**
     * Internal Order Number
     */
    private String appOrderNo;
    /**
     * Wallet Refund Record Number
     */
    private String refundNo;
    /**
     * Refund Amount
     */
    private BigDecimal amount;
    /**
     * Status: preRefund (Pending Split); refundSuccess (Refund Successful); refundFailed (Refund Failed)
     */
    private PayOrderRefundStatus status;
    /**
     * Remark
     */
    private String remark;
    /**
     * Refund Completion Time
     */
    private Date successTime;

}
