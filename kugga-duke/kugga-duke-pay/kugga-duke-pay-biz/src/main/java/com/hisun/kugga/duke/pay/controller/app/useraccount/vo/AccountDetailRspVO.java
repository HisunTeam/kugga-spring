package com.hisun.kugga.duke.pay.controller.app.useraccount.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
@Data
@ApiModel("用户账户详情 Response VO")
public class AccountDetailRspVO {
    @ApiModelProperty(value = "账户余额")
    private BigDecimal balance;

    @ApiModelProperty(value = "提现费率")
    private BigDecimal feeRate;
}
