package com.hisun.kugga.duke.pay.controller.app.withdraw.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
@Data
@ApiModel("提现订单状态 Response VO")
public class WithdrawDetailRspVO {
    @ApiModelProperty(value = "钱包订单号")
    private String orderNo;
    @ApiModelProperty(value = "提现状态 draft 等待到账；success 已成功；failed 交易失败；closed 已关闭")
    private String status;
    @ApiModelProperty(value = "到账金额")
    private BigDecimal actualAmount;
}
