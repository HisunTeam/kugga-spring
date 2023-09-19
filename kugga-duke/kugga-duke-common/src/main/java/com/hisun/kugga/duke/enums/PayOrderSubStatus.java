package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author: zhou_xiong
 */

@AllArgsConstructor
public enum PayOrderSubStatus {
    PRE_SPLIT("preSplit", "待分账"),
    SPLIT_SUCCESS("splitSuccess", "分账成功"),
    SPLIT_FAILED("splitFailed", "分账失败"),
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
