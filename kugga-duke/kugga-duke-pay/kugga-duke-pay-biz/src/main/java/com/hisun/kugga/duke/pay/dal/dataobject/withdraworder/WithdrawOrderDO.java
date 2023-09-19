package com.hisun.kugga.duke.pay.dal.dataobject.withdraworder;

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
 * 提现订单 DO
 *
 * @author 芋道源码
 */
@TableName("duke_withdraw_order")
@KeySequence("duke_withdraw_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawOrderDO extends BaseDO {

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
     * 用户id
     */
    private Long userId;
    /**
     * 钱包账号
     */
    private String accountId;
    /**
     * 提现金额，单位：元
     */
    private BigDecimal amount;
    /**
     * 实际到账金额
     */
    private BigDecimal actualAmount;
    /**
     * 手续费，单位：元
     */
    private BigDecimal fee;
    /**
     * 提现方式 paypal
     */
    private WithdrawChannel withdrawChannel;
    /**
     * 提现卡号
     */
    private String cardNo;
    /**
     * 交易币种
     */
    private String currency;
    /**
     * 提现状态 draft 等待到账；success 已成功；failed 交易失败；
     */
    private WithdrawStatus status;
    /**
     * 到账时间
     */
    private Date receivedTime;

}
