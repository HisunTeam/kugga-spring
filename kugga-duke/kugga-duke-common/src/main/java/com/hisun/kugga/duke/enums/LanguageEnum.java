package com.hisun.kugga.duke.enums;

import lombok.Getter;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/7/29 15:03
 */
@Getter
public enum LanguageEnum {

    /**
     * 中文
     */
    zh_CN("zh-CN", "中文"),

    en_US("en-US", "英文");

    private String code;
    private String msg;

    LanguageEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
