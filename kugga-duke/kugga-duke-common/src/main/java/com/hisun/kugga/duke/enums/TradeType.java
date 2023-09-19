package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zhou_xiong
 */
@AllArgsConstructor
@Getter
public enum TradeType {
    /**
     * 充值
     */
    CHARGE("charge", "充值"),
    /**
     * 消费
     */
    CONSUME("consume", "消费"),
    /**
     * 退款
     */
    REFUND("refund", "退款"),
    /**
     * 提现
     */
    PAYOUT("payout", "提现"),
    ;

    @EnumValue
    String typeCode;
    String desc;
}
