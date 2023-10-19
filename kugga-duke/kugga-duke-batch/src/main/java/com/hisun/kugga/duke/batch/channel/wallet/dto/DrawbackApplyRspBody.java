package com.hisun.kugga.duke.batch.channel.wallet.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class DrawbackApplyRspBody {
    /**
     * Refund Status
     */
    private String drawbackStatus;
    /**
     * Wallet Order Number
     */
    private String orderNo;
}
