package com.hisun.kugga.duke.league.bo.task;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-28 20:02
 */
@Data
public class TaskLeagueAuthenticationBO {
    private Long id;
    private BigDecimal amount;
    private Integer type;
    private String arg2;
}
