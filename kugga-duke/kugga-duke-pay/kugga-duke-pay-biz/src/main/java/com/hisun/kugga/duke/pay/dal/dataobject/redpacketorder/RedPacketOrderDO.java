package com.hisun.kugga.duke.pay.dal.dataobject.redpacketorder;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.AccountType;
import com.hisun.kugga.duke.enums.RedPacketOrderStatus;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * 红包订单 DO
 *
 * @author zhou_xiong
 */
@TableName("duke_red_packet_order")
@KeySequence("duke_red_packet_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedPacketOrderDO extends BaseDO {

    /**
     * 订单id
     */
    @TableId
    private Long id;
    /**
     * 内部订单号
     */
    private String appOrderNo;
    /**
     * 钱包订单号
     */
    private String walletOrderNo;
    /**
     * 付款方id
     */
    private Long payerId;
    /**
     * 付款方账户类型
     */
    private AccountType accountType;
    /**
     * 钱包账户
     */
    private String accountId;
    /**
     * 支付方式【balance：余额支付，paypal：paypal支付】
     */
    private String payChannel;
    /**
     * 红包总金额
     */
    private BigDecimal amount;
    /**
     * 手续费
     */
    private BigDecimal fee;
    /**
     * 币种
     */
    private String currency;
    /**
     * 状态： draft 待发放；processing 处理中；partial_success 部分发放成功；full_success 已全部发放成功；failed 已退款；
     */
    private RedPacketOrderStatus status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 完成时间
     */
    private String completeTime;
}
