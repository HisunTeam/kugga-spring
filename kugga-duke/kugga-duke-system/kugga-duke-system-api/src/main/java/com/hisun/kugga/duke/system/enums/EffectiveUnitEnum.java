package com.hisun.kugga.duke.system.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * 失效时间单位枚举
 *
 * @author zuocheng
 */
@Getter
public enum EffectiveUnitEnum {

    /**
     * 秒
     */
    s("s", "秒"),
    /**
     * 分
     */
    m("m", "分"),
    /**
     * 时
     */
    H("H", "时"),
    /**
     * 日
     */
    D("D", "日"),
    /**
     * 周
     */
    W("W", "周"),
    /**
     * 月
     */
    M("M", "月"),
    /**
     * 年
     */
    Y("Y", "年"),
    ;

    private String code;
    private String desc;

    EffectiveUnitEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举对象
     *
     * @param code
     * @return
     */
    public static EffectiveUnitEnum getByCode(String code) {
        if (StrUtil.isEmpty(code)) {
            return null;
        }
        for (EffectiveUnitEnum effectiveUnitEnum : EffectiveUnitEnum.values()) {
            if (StrUtil.equals(code, effectiveUnitEnum.getCode())) {
                return effectiveUnitEnum;
            }
        }
        return null;
    }
}
