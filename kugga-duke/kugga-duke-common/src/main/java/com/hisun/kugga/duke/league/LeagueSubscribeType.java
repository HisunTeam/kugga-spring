package com.hisun.kugga.duke.league;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @Description: 加入公会订阅类型
 * @author： Lin
 * @Date 2022/10/19 10:38
 */
@Getter
public enum LeagueSubscribeType {

    /**
     * 订阅类型
     */
    SUBSCRIBE_MONTH("month", "按月订阅"),
    SUBSCRIBE_QUARTER("quarter", "按季度订阅"),
    SUBSCRIBE_YEAR("year", "按年订阅"),
    SUBSCRIBE_FOREVER("forever", "永久订阅 永久加入"),

    ;

    private String code;
    private String desc;

    LeagueSubscribeType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举对象
     *
     * @param code
     * @return
     */
    public static LeagueSubscribeType getByCode(String code) {
        if (StrUtil.isEmpty(code)) {
            return null;
        }
        for (LeagueSubscribeType subscribeEnum : LeagueSubscribeType.values()) {
            if (StrUtil.equals(code, subscribeEnum.getCode())) {
                return subscribeEnum;
            }
        }
        return null;
    }
}
