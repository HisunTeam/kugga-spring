package com.hisun.kugga.duke.pay.api.order.dto;

import com.hisun.kugga.duke.enums.AccountType;
import com.hisun.kugga.duke.enums.OrderType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
@Data
public class OrderCreateReqDTO {
    /**
     * 交易类型(用于生成用户账单时的交易类型)
     */
    @NotNull(message = "orderType cannot be empty")
    private OrderType orderType;
    /**
     * 付款方ID
     */
    @NotNull(message = "payerId cannot be empty")
    private Long payerId;
    /**
     * 付款方类型
     */
    @NotNull(message = "accountType cannot be empty")
    private AccountType accountType;
    /**
     * 付款金额 单位：元
     */
    @NotNull(message = "amount cannot be empty")
    private BigDecimal amount;
}
