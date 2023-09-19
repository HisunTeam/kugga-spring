package com.hisun.kugga.duke.batch.dal.dataobject.redpacketorderdetail;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.AccountType;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * 红包订单详情 DO
 *
 * @author zhou_xiong
 */
@TableName("duke_red_packet_order_detail")
@KeySequence("duke_red_packet_order_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedPacketOrderDetailDO extends BaseDO {

    /**
     * 订单明细id
     */
    @TableId
    private Long id;
    /**
     * 内部订单号
     */
    private String appOrderNo;
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
     * 备注
     */
    private String remark;

}
