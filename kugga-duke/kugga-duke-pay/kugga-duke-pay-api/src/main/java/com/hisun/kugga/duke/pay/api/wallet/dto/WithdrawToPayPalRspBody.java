package com.hisun.kugga.duke.pay.api.wallet.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class WithdrawToPayPalRspBody {
    private String orderNo;
    private Integer fee;
}
