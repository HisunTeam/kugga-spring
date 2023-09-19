package com.hisun.kugga.duke.league.vo.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:37
 */
@Data
public class TaskCreateLeagueVO {
    @ApiModelProperty(value = "公会ID")
    private Long id;
    @ApiModelProperty(value = "公会金额")
    private BigDecimal amount;
}
