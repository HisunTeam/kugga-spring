package com.hisun.kugga.duke.pay.api.wallet.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class PrepayRspBody {
    /**
     * 钱包订单号
     */
    private String orderNo;
    /**
     * 手续费
     */
    private Integer fee;
}
