package com.hisun.kugga.duke.league.annotation;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-08-11 11:30
 * 测试专用
 */
@AllArgsConstructor
public enum DayEnum {
    DAY_1(1, "周1"),
    DAY_2(2, "周2"),
    DAY_3(3, "周3"),
    DAY_4(4, "周4"),
    DAY_5(5, "周5"),
    DAY_6(6, "周6"),
    DAY_7(7, "周7");

    /**
     * @EnumKey 枚举工具类专用
     * @EnumValue 数据库DO对象可以直接用枚举字段，自动存value的值
     * @JsonValue 会给前端返回desc 的值
     */
    @EnumKey
    @EnumValue
    private int value;
    @JsonValue
    private String desc;
}
