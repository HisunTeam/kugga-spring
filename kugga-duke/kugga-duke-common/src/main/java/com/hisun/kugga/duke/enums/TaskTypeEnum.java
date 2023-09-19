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
public enum TaskTypeEnum {
    TASK_TYPE_1(1, "推荐报告"),
    TASK_TYPE_2(2, "公会认证"),
    TASK_TYPE_3(3, "聊天"),
    TASK_TYPE_4(4, "发红包");
    @EnumValue
    int value;
    String desc;
}
