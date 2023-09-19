package com.hisun.kugga.duke.user.common;

import cn.hutool.core.util.ArrayUtil;
import lombok.Getter;

/**
 * @author 收藏枚举类
 */
@Getter
public enum UserRelationEnum {

    /**
     * 同公会
     */
    SAME("0", "同公会"),

    /**
     * 不同公会
     */
    DIFFERENT("1", "不同公会"),

    ;

    private String code;
    private String message;

    UserRelationEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static UserRelationEnum getEnumByCode(String code) {
        return ArrayUtil.firstMatch(sceneEnum -> sceneEnum.getCode().equals(code),
                values());
    }
}
