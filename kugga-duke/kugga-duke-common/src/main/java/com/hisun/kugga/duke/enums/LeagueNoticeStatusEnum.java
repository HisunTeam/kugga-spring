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
public enum LeagueNoticeStatusEnum {
    /**
     * 公会规则枚举
     */
    NOTICE_STATUS_1(1, "未接单"),
    NOTICE_STATUS_2(2, "已接单"),
    NOTICE_STATUS_3(3, "已完成"),
    NOTICE_STATUS_4(4, "已失效");
    @EnumValue
    private int value;
    private String desc;
}
