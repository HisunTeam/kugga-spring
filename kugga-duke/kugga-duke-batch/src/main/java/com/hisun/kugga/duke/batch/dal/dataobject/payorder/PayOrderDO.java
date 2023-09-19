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
 * 订单 DO
 *
 * @author zhou_xiong
 */
@TableName("duke_pay_order")
@KeySequence("duke_pay_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayOrderDO extends BaseDO {
    /**
     * id
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
     * 订单类型【1、创建公会；2、公会认证；3、写推荐报告；4、发起聊天】
     */
    private OrderType orderType;
    /**
     * 交易人(用户ID)
     */
    private Long payerId;
    /**
     * 付款账户类型
     */
    private AccountType accountType;
    /**
     * 钱包账户
     */
    private String accountId;
    /**
     * 支付方式
     */
    private PayChannel payChannel;
    /**
     * 交易金额
     */
    private BigDecimal payAmount;
    /**
     * 已分账金额
     */
    private BigDecimal splitAmount;
    /**
     * 已退款金额
     */
    private BigDecimal refundAmount;
    /**
     * 交易币种
     */
    private String currency;
    /**
     * 订单状态: prepay 预支付；processing 处理中；success 已成功；failed 交易失败；closed 已关闭
     */
    private PayOrderStatus status;
    /**
     * 备注
     */
    private String remark;

}
