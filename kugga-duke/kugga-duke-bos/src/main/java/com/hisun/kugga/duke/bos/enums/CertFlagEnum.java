package com.hisun.kugga.duke.bos.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @Description: 审核状态枚举
 * @author： lzt
 * @Date 2022/10/15 15:03
 */
@Getter
public enum CertFlagEnum {

    /**
     * 查询
     */
    cert_waiting("0", "待审核"),
    cert_pass("1", "审核通过"),
    cert_reject("2", "审核拒绝");
    @EnumValue
    private String code;
    @JsonValue
    private String msg;

    CertFlagEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
