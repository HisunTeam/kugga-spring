package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zhou_xiong
 */
@Getter
@AllArgsConstructor
public enum PayChannel {
    BALANCE("balance", "余额支付"),
    PAYPAL("paypal", "paypal支付"),
    ;
    @EnumValue
    String typeCode;
    String desc;

    @JsonValue
    public String getDesc() {
        return desc;
    }
}
