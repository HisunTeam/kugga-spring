package com.hisun.kugga.duke.forum.enums;

/**
 * @author zuocheng
 */

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 贴子排序规则枚举
 *
 * @author zuocheng
 */
@Getter
@AllArgsConstructor
public enum SortTypeEnums {
    /**
     * 创建时间
     */
    CREATE_TIME("0"),
    /**
     * 回复时间
     */
    REPLY_TIME("1"),
    /**
     * 热度
     */
    HOT("2");

    /**
     * 加入类型
     */
    @EnumValue
    private final String type;

    public static SortTypeEnums valueOfType(String type) {
        return ArrayUtil.firstMatch(o -> o.getType().equals(type), values());
    }
}
