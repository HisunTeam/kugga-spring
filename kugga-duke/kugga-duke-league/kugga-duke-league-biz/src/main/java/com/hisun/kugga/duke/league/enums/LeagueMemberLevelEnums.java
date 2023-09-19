package com.hisun.kugga.duke.league.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公会成员级别
 *
 * @author zuocheng
 */
@Getter
@AllArgsConstructor
public enum LeagueMemberLevelEnums {
    /**
     * 超级管理员
     */
    SUPER_ADM(0),
    /**
     * 管理员
     */
    ADM(1),
    /**
     * 普通成员
     */
    MEMBER(2),
    ;
    /**
     * 加入类型
     */
    private final Integer type;
}
