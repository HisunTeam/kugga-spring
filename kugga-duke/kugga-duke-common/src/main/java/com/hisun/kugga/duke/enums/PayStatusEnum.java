package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 9:29
 * 任务类型枚举
 */
@Getter
@AllArgsConstructor
public enum PayStatusEnum {

    /**
     * 支付 金额状态
     */
    DEFAULT_STATUS(0, "默认状态 未初始化状态"),
    // 已下单 未支付
    NO_PAY(1, "未支付"),
    PAY(2, "已支付"),
    SPLIT_ACCOUNT(3, "已分账"),
    WAIT_REFUND(4, "待退款"),
    REFUND(5, "已退款"),
    FAIL(6, "失败"),
    ;
    @EnumValue
    int value;
    String desc;
}
