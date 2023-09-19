package com.hisun.kugga.duke.utils.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/14 14:48
 */
@Data
public class SplitAccountVo {
    /**
     * 个人分账金额
     */
    private BigDecimal personAmount;
    /**
     * 公会分账金额
     */
    private BigDecimal leagueAmount;
    /**
     * 平台分账金额
     */
    private BigDecimal platformAmount;
}
