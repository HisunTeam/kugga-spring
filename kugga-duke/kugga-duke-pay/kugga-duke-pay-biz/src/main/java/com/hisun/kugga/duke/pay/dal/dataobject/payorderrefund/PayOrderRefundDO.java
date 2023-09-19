package com.hisun.kugga.duke.pay.dal.dataobject.payorderrefund;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.PayOrderRefundStatus;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付订单退款 DO
 *
 * @author 芋道源码
 */
@TableName("duke_pay_order_refund")
@KeySequence("duke_pay_order_refund_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayOrderRefundDO extends BaseDO {

    /**
     * 退款id
     */
    @TableId
    private Long id;
    /**
     * 内部订单号
     */
    private String appOrderNo;
    /**
     * 钱包退款记录编号
     */
    private String refundNo;
    /**
     * 退款金额
     */
    private BigDecimal amount;
    /**
     * 状态： preRefund 待分账；refundSuccess 退款成功；refundFailed 退款失败
     */
    private PayOrderRefundStatus status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 退款完成时间
     */
    private Date successTime;

}
