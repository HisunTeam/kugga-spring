package com.hisun.kugga.duke.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zhou_xiong
 */
@Getter
@AllArgsConstructor
public enum EnterType {
    /**
     * 用户
     */
    USER("U", "用户"),
    /**
     * 公会
     */
    LEAGUE("L", "公会");

    String typeCode;
    String desc;
}
