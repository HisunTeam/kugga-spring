package com.hisun.kugga.duke.pay.api.wallet.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class WithdrawDetailRspBody {
    private Integer actualAmount;
    private Integer amount;
    private String receivedTime;
    private String status;
    private Integer fee;
}
