package com.hisun.kugga.duke.batch.dal.dataobject.redpacketorderdetail;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.AccountType;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * Red Packet Order Detail Data Object (DO)
 *
 * Author: zhou_xiong
 */
@TableName("duke_red_packet_order_detail")
@KeySequence("duke_red_packet_order_detail_seq") // Used for Oracle, PostgreSQL, Kingbase, DB2, H2 databases for auto-incrementing primary keys. Not needed for databases like MySQL.
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedPacketOrderDetailDO extends BaseDO {

    /**
     * Order Detail ID
     */
    @TableId
    private Long id;
    /**
     * Internal Order Number
     */
    private String appOrderNo;
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
     * Remark
     */
    private String remark;
}
