package com.hisun.kugga.duke.bos.enums;

import lombok.Getter;

/**
 * @Description:
 * @author： lzt
 * @Date 2022/9/15 15:03
 */
@Getter
public enum SerialPasswordEnum {

    /**
     * 查询创建推荐报告授权密码组
     */
    create_recommendation_log("create_recommendation_log", "查询创建推荐报告授权密码组");

    private String code;
    private String msg;

    SerialPasswordEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
