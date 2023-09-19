package com.hisun.kugga.duke.enums;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 充值提现枚举 运管系统
 * @author： lzt
 * @Date 2022/10/15 15:03
 */
@Getter
@AllArgsConstructor
public enum ChargeWithdrawEnum {

    /**
     * 查询
     */
    charge("charge", "充值"),
    withdraw("withdraw", "提现")
    ;
    @EnumValue
    private String code;
    @JsonValue
    private String msg;

    public static ChargeWithdrawEnum getEnumByCode(String code) {
        return ArrayUtil.firstMatch(sceneEnum -> sceneEnum.getCode().equals(code),
                values());
    }

}
