package com.hisun.kugga.duke.forum.enums;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 板块枚举
 *
 * @author zuocheng
 */
@Getter
@AllArgsConstructor
public enum PlateEnums {
    /**
     * 公会板块
     */
    PLATE_0("0"),

    /**
     * 匿名板块
     */
    PLATE_1("1");

    /**
     * 加入类型
     */
    @EnumValue
    private final String type;

    public static PlateEnums valueOfType(String type) {
        return ArrayUtil.firstMatch(o -> o.getType().equals(type), values());
    }
}
