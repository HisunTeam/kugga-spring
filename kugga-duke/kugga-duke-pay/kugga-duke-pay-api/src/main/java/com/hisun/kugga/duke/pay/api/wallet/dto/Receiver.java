package com.hisun.kugga.duke.pay.api.wallet.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class Receiver {
    /**
     * 分账金额
     */
    private Integer profitSharingAmount;
    /**
     * 分账接收方账户
     */
    private String profitSharingReceiver;
    /**
     * 分账描述
     */
    private String remark;
}
