package com.hisun.kugga.duke.enums;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/7 14:46
 */
@Getter
@AllArgsConstructor
public enum SecretTypeEnum {
    // 注册登录时密码用LOGIN，设置重置、校验支付密码用PAY
    //修改登录密码用LOGIN_UPDATE,修改支付密码用PAY_UPDATE
    /**
     *
     */
    LOGIN(0, "注册登录"),
    LOGIN_UPDATE(1, "用户账户密码修改"),
    /**
     *
     */
    PAY(2, "支付"),
    PAY_UPDATE(3, "支付密码修改");

    private final Integer code;
    private final String desc;

    public static SecretTypeEnum getEnumByCode(Integer code) {
        return ArrayUtil.firstMatch(typeEnum -> typeEnum.getCode().equals(code),
                values());
    }
}
