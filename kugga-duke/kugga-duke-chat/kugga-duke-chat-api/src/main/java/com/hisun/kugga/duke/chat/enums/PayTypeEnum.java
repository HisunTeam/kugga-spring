package com.hisun.kugga.duke.chat.enums;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 聊天室支付类型
 */
@Getter
@AllArgsConstructor
public enum PayTypeEnum {

    /**
     * 工会内部免费聊天
     */
    FREE_CHAT(0),
    /**
     * 工会外部付费聊天
     */
    PAY_CHAT(1);

    @EnumValue
    private final Integer type;

    public static PayTypeEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(typeEnum -> typeEnum.getType().equals(value), PayTypeEnum.values());
    }
}
