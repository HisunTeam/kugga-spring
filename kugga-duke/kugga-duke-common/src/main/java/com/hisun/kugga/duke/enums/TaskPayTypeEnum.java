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
public enum TaskPayTypeEnum {
    FREE(0, "免费"),
    PAY(1, "付费"),
    ;
    @EnumValue
    int value;
    String desc;
}
