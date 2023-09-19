package com.hisun.kugga.duke.pay.api.wallet.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class PayByBalanceRspBody {
    /**
     * 到账金额
     */
    private Integer actualAmount;
    /**
     * 手续费
     */
    private Integer fee;
}
