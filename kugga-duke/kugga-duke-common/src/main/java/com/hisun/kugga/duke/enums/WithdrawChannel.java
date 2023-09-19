package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zhou_xiong
 */
@Getter
@AllArgsConstructor
public enum WithdrawChannel {
    BANK("bank", "银行"),
    PAYPAL("paypal", "paypal"),
    ;
    @EnumValue
    String typeCode;
    String desc;
}
