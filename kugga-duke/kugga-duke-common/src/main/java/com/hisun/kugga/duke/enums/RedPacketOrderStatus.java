package com.hisun.kugga.duke.enums;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author: zhou_xiong
 */
@Getter
@AllArgsConstructor
public enum RedPacketOrderStatus {
    DRAFT("draft", "待发放"),
    PROCESSING("processing", "处理中"),
    SUCCESS("success", "发放成功"),
    FAILED("failed", "发放失败"),
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

    public static RedPacketOrderStatus getByKey(String key) {
        return Arrays.stream(RedPacketOrderStatus.values())
                .filter(redPacketOrderStatus -> StrUtil.equals(redPacketOrderStatus.getKey(), key))
                .findFirst().orElse(null);
    }
}
