package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zhou_xiong
 */
@Getter
@AllArgsConstructor
public enum AccountType {
    /**
     * 用户
     */
    USER("user", "用户"),
    /**
     * 公会
     */
    LEAGUE("league", "公会"),
    /**
     * 平台
     */
    PLATFORM("platform", "平台"),
    ;

    @EnumValue
    String typeCode;
    String desc;
}
