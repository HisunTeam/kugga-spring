package com.hisun.kugga.duke.pay.dal.dataobject.leagueaccount;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * 公会账户金额 DO
 *
 * @author zhou_xiong
 */
@TableName("duke_league_account")
@KeySequence("duke_league_account_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueAccountDO extends BaseDO {

    /**
     * 账户ID
     */
    @TableId
    private Long id;
    /**
     * 公会ID
     */
    private Long leagueId;
    /**
     * 钱包账户
     */
    private String accountId;
    /**
     * 账户金额
     */
    private BigDecimal account;

}
