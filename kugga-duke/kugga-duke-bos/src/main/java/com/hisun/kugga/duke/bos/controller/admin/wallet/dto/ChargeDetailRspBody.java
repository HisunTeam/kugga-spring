package com.hisun.kugga.duke.bos.controller.admin.wallet.dto;

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
     * 交易状态： prepay 预支付；processing 处理中；success 已成功；failed 交易失败；closed 已关闭
     */
    private String status;
}
