package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author: zhou_xiong
 */

@AllArgsConstructor
public enum RedPacketStatus {
    INIT("0","初始化"),
    ORDERED("1","已下单"),
    SUCCESS("2","成功"),
    FAILED("3","失败"),
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
