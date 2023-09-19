package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author: zhou_xiong
 */

@AllArgsConstructor
public enum WithdrawStatus {
    DRAFT("draft", "提现中"),
    SUCCESS("success", "提现成功"),
    FAILED("failed", "提现失败"),
    CLOSED("closed", "已关闭"),
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
