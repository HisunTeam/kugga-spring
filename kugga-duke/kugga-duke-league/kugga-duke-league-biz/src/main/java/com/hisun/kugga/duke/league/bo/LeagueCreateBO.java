package com.hisun.kugga.duke.league.bo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zuocheng
 */
@Data
public class LeagueCreateBO {
    /**
     * 公会名称
     */
    private String leagueName;

    /**
     * 公会头像
     */
    private String leagueAvatar;

    /**
     * 公会描述
     */
    private String leagueDesc;

    /**
     * 用户ID(创建者)
     */
    private Long userId;

    /**
     * 创建公会扣费订单号
     */
    private String appOrderNo;

    /**
     * 创建公会扣费金额
     */
    private BigDecimal amount;

    /**
     * 是否付费 1付费，0免费
     */
    private Boolean payFlag;
}
