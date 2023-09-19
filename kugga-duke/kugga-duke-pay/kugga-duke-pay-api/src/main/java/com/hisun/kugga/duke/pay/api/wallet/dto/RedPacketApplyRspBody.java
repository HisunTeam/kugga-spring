package com.hisun.kugga.duke.pay.api.wallet.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class RedPacketApplyRspBody {
    /**
     * 红包订单号
     */
    private String orderNo;
    private String status;
}
