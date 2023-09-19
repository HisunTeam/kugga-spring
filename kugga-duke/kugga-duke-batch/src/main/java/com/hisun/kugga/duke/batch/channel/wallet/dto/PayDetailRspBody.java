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
     * 钱包订单号
     */
    private String orderNo;
    /**
     * 支付订单号
     */
    private String payOrderNo;
    /**
     * 支付流水号，使用第三方支付时为其返回的流水号
     */
    private String paySn;
    /**
     * 支付方式: balance; paypal; bank_card ;
     */
    private String payType;
    /**
     * 交易状态： prepay 预支付；processing 处理中；success 已成功；failed 交易失败；closed 已关闭
     */
    private String remark;
    private String status;
}
