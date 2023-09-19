package com.hisun.kugga.duke.pay.controller.app.useraccount.vo;

import com.hisun.kugga.duke.enums.TradeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
@ApiModel("交易手续费计算 Request VO")
@Data
public class TradeFeeReqVO {
    @ApiModelProperty(value = "交易类型 CHARGE-充值 CONSUME-消费 REFUND-退款 PAYOUT-提现", required = true)
    @NotNull(message = "tradeType cannot be empty")
    private TradeType tradeType;

    @ApiModelProperty(value = "交易金额", required = true)
    @NotNull(message = "amount cannot be empty")
    private BigDecimal amount;
}
