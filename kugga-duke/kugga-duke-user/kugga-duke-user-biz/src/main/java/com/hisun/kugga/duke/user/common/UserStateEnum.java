package com.hisun.kugga.duke.user.common;

import cn.hutool.core.util.ArrayUtil;
import lombok.Getter;

/**
 * @author 用户状态枚举类
 */
@Getter
public enum UserStateEnum {
    /**
     * 用户正常
     */
    NORMAL(0, "用户正常"),
    /**
     * 用户拉黑
     */
    BLOCK(1, "用户拉黑"),

    ;

    private Integer code;
    private String message;

    UserStateEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static UserStateEnum getEnumByCode(Integer code) {
        return ArrayUtil.firstMatch(userStateEnum -> userStateEnum.getCode().equals(code),
                values());
    }
}
