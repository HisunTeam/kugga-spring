package com.hisun.kugga.duke.batch.channel.wallet.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class PayDetailRspBody {
    private String account;
    private String actualAmount;
    private String amount;
    private String appOrderNo;
    private String appid;
    private String channelAccount;
    private String channelName;
    private String closeTime;
    private String consumeType;
    private String currency;
    private String dealTime;
    private String fee;
    /**
     * Wallet order number
     */
    private String orderNo;
    /**
     * Payment order number
     */
    private String payOrderNo;
    /**
     * Payment transaction number, provided by the third-party payment system
     */
    private String paySn;
    /**
     * Payment method: balance; paypal; bank_card;
     */
    private String payType;
    /**
     * Transaction status: prepay (Prepayment); processing (Processing); success (Successful); failed (Transaction Failed); closed (Closed)
     */
    private String remark;
    private String status;
}
