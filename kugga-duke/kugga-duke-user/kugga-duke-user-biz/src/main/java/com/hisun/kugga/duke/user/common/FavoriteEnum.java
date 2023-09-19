package com.hisun.kugga.duke.user.common;

import cn.hutool.core.util.ArrayUtil;
import lombok.Getter;

/**
 * @author 收藏枚举类
 */
@Getter
public enum FavoriteEnum {
    /**
     * 公会
     */
    LEAGUE("G", "公会"),
    /**
     * recommendation 推荐信
     */
    RECOMMENDATION("T", "推荐信"),
    /**
     * POSTS 贴子
     */
    POSTS("P", "贴子")

    ;

    private String code;
    private String message;

    FavoriteEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static FavoriteEnum getEnumByCode(String code) {
        return ArrayUtil.firstMatch(sceneEnum -> sceneEnum.getCode().equals(code),
                values());
    }
}
