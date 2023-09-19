package com.hisun.kugga.duke.league.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公会加入类型枚举
 *
 * @author zuocheng
 */
@Getter
@AllArgsConstructor
public enum LeagueJoinTypeEnums {
    /**
     * 创建者
     */
    CREATE(0),
    /**
     * 邀请
     */
    INVITE(1),
    /**
     * 申请
     */
    APPLY(2),
    ;
    /**
     * 加入类型
     */
    private final Integer type;
}
