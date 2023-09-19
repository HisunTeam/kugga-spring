package com.hisun.kugga.duke.bos.enums;

import lombok.Getter;

/**
 * @Description:
 * @author： lzt
 * @Date 2022/9/15 15:03
 */
@Getter
public enum SerialPasswordSortIdEnum {

    /**
     * 查询创建推荐报告授权密码组
     */
    first_password(1, "第一个密码"),
    second_password(2, "第二个密码");

    private Integer code;
    private String msg;

    SerialPasswordSortIdEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
