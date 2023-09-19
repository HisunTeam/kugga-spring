package com.hisun.kugga.duke.pay.dal.dataobject.payordersub;

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
 * 支付订单子集 DO
 *
 * @author 芋道源码
 */
@TableName("duke_pay_order_sub")
@KeySequence("duke_pay_order_sub_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayOrderSubDO extends BaseDO {
    /**
     * 订单子集id
     */
    @TableId
    private Long id;
    /**
     * 内部订单号
     */
    private String appOrderNo;
    /**
     * 钱包分账记录编号
     */
    private String splitNo;
    /**
     * 交易类型
     */
    private OrderType orderType;
    /**
     * 收款方id
     */
    private Long receiverId;
    /**
     * 收款方账户类型
     */
    private AccountType accountType;
    /**
     * 收款方钱包账户
     */
    private String accountId;
    /**
     * 收款金额
     */
    private BigDecimal amount;
    /**
     * 分账状态
     */
    private PayOrderSubStatus status;
    /**
     * 备注
     */
    private String remark;

}
