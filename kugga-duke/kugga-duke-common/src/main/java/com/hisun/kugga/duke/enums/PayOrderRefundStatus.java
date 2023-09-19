package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author: zhou_xiong
 */

@AllArgsConstructor
public enum PayOrderRefundStatus {
    PRE_REFUND("preRefund", "待退款"),
    REFUND_SUCCESS("refundSuccess", "退款成功"),
    REFUND_FAILED("refundFailed", "退款失败"),
    ;
    @EnumValue
    private String key;
    private String desc;

    public String getKey() {
        return key;
    }

    @JsonValue
    public String getDesc() {
        return desc;
    }
}
