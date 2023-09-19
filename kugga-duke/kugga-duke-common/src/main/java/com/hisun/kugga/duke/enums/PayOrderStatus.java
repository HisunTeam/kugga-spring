package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author: zhou_xiong
 */

@AllArgsConstructor
public enum PayOrderStatus {
    PREPAY("prepay", "待支付"),
    CLOSED("closed", "支付关闭"),
    PAY_SUCCESS("paySuccess", "支付成功"),
    PAY_FAILED("payFailed", "支付失败"),
    SPLIT_SUCCESS("splitSuccess", "分账成功"),
    SPLIT_FAILED("splitFailed", "分账失败"),
    REFUND_SUCCESS("refundSuccess", "退款成功"),
    REFUND_FAILED("refundFailed", "退款失败"),
    PART_REFUND("partRefund", "部分退款"),
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
