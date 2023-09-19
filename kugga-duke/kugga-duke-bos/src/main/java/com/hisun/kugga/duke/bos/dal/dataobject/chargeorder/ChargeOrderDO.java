package com.hisun.kugga.duke.bos.dal.dataobject.chargeorder;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.ChargeOrderStatus;
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
     * 支付方式
     */
    private String chargeChannel;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 钱包账号
     */
    private String accountId;
    /**
     * 充值金额，单位：分
     */
    private BigDecimal amount;
    /**
     * 交易币种
     */
    private String currency;
    /**
     * 充值状态init 初始化；success 充值支付成功；failed 交易失败；expired 支付链接已失效
     */
    private ChargeOrderStatus status;
    /**
     * 到账时间
     */
    private Date receivedTime;

}
