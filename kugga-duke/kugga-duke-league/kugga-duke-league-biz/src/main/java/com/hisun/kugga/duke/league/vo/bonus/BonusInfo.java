package com.hisun.kugga.duke.league.vo.bonus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
@ApiModel("红包结果 Response VO")
@Data
public class BonusInfo {
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "姓")
    private String firstName;
    @ApiModelProperty(value = "名")
    private String lastName;
    @ApiModelProperty(value = "红包金额")
    private BigDecimal amount;

}
