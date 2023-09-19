package com.hisun.kugga.duke.forum.enums;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 讨论类型
 *
 * @author zuocheng
 */
@Getter
@AllArgsConstructor
public enum MsgTypeEnums {
    /**
     * 0:贴子
     */
    MSG_TYPE_0("0", "PP1000"),

    /**
     * 0:楼层
     */
    MSG_TYPE_1("1", "FP2000"),

    /**
     * 1:讨论
     */
    MSG_TYPE_2("2", "CP3000");

    /**
     * 消息类型
     */
    @EnumValue
    private final String type;
    /**
     * 消息ID前缀（可以根据前缀来区分消息类型）
     */
    private final String prefix;

    /**
     * 根据类型获取枚举类型
     *
     * @param type
     * @return
     */
    public static MsgTypeEnums valueOfType(String type) {
        return ArrayUtil.firstMatch(o -> o.getType().equals(type), values());
    }

    /**
     * 根据前缀获取枚举类型；
     *
     * @param prefix
     * @return
     */
    public static MsgTypeEnums valueOfPrefix(String prefix) {
        return ArrayUtil.firstMatch(o -> o.getPrefix().equals(prefix), values());
    }
}
