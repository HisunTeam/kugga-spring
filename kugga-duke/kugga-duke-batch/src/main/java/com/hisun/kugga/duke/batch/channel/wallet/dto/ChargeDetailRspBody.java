package com.hisun.kugga.duke.batch.channel.wallet.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class ChargeDetailRspBody {
    private String account;
    private Integer actualAmount;
    private String orderNo;
    private Integer fee;
    private String receivedTime;
    /**
     * Transaction Status: prepay (Prepayment); processing (Processing); success (Successful); failed (Transaction Failed); closed (Closed)
     */
    private String status;
}
