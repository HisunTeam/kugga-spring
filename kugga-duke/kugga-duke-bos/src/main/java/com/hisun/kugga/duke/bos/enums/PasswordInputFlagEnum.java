package com.hisun.kugga.duke.bos.enums;

import lombok.Getter;

/**
 * @Description:
 * @author： lzt
 * @Date 2022/9/15 15:03
 */
@Getter
public enum PasswordInputFlagEnum {

    /**
     * 查询创建推荐报告授权密码组
     */
    not_entered("0", "密码未输入"),
    entered("1", "密码已输入");

    private String code;
    private String msg;

    PasswordInputFlagEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
