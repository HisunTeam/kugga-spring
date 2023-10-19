package com.hisun.kugga.duke.batch.channel.wallet.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class Receiver {
    /**
     * Profit-sharing amount
     */
    private Integer profitSharingAmount;
    /**
     * Profit-sharing receiver's account
     */
    private String profitSharingReceiver;
    /**
     * Profit-sharing description
     */
    private String remark;
}
