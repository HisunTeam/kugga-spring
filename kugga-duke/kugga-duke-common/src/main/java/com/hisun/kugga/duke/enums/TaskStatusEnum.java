package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 9:29
 */
@Getter
@AllArgsConstructor
public enum TaskStatusEnum {
    /**
     * 公会规则枚举
     */
    TASK_STATUS_0(0, "待支付   初始化状态，已下单待支付"),
    TASK_STATUS_1(1, "未接单   已支付"),
    TASK_STATUS_2(2, "已接单   任务层面抢单"),
    TASK_STATUS_3(3, "已完成   任务完成"),
    TASK_STATUS_4(4, "待退款 "),
    TASK_STATUS_5(5, "已退款 先去退款再去改状态"),
    ;

    @EnumValue
    private int value;
    private String desc;
}
