package com.hisun.kugga.duke.batch.dal.dataobject.leaguebill;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * 公会账单 DO
 *
 * @author zhou_xiong
 */
@TableName("duke_league_bill")
@KeySequence("duke_league_bill_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueBillDO extends BaseDO {

    /**
     * 交易id
     */
    @TableId
    private Long id;
    /**
     * 账单号
     */
    private String billNo;
    /**
     * 钱包订单号
     */
    private String walletOrderNo;
    /**
     * 公会ID
     */
    private Long leagueId;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * 账单状态: U-初始,W-在途,S-成功,F-失败
     */
    private String status;
    /**
     * 备注
     */
    private String remark;

}
