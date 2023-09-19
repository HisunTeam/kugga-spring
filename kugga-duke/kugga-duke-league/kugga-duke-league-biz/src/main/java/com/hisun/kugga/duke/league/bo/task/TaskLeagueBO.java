package com.hisun.kugga.duke.league.bo.task;

import com.hisun.kugga.duke.enums.TaskPayTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:37
 */
@Data
public class TaskLeagueBO {
    @ApiModelProperty(value = "公会ID")
    private Long id;
    @ApiModelProperty(value = "公会金额")
    private BigDecimal amount;
    @ApiModelProperty(value = "付费类型", hidden = true)
    private TaskPayTypeEnum payType;
    @ApiModelProperty(value = "订单编号", hidden = true)
    private String orderNo;
}
