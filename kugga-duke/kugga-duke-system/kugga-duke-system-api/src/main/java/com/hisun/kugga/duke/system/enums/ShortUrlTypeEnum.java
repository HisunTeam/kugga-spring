package com.hisun.kugga.duke.system.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短链类型
 *
 * @author zuocheng
 */

@Getter
@AllArgsConstructor
public enum ShortUrlTypeEnum {
    /**
     * 默认短链
     */
    TYPE_R("R"),
    /**
     * 公会邀请短链
     */
    TYPE_G("G");

    private String code;

    /**
     * 根据code获取枚举对象
     *
     * @param code
     * @return
     */
    public static ShortUrlTypeEnum getByCode(String code) {
        if (StrUtil.isEmpty(code)) {
            return null;
        }
        for (ShortUrlTypeEnum typeEnum : ShortUrlTypeEnum.values()) {
            if (StrUtil.equals(code, typeEnum.getCode())) {
                return typeEnum;
            }
        }
        return null;
    }
}
