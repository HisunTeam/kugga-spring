package com.hisun.kugga.duke.pay.api.order.dto;

import com.hisun.kugga.duke.enums.AccountType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
@Data
public class ReceiverInfo {
    /**
     * 收款方id
     */
    private Long receiverId;
    /**
     * 收款方类型
     */
    private AccountType accountType;
    /**
     * 收款金额
     */
    private BigDecimal amount;
}
