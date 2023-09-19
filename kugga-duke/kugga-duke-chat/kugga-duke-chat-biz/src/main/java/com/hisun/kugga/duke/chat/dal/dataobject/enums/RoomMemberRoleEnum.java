package com.hisun.kugga.duke.chat.dal.dataobject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 */
@Getter
@AllArgsConstructor
public enum RoomMemberRoleEnum {

    /**
     * 普通用户
     */
    NORMAL(0),
    /**
     * 管理员
     */
    ADMIN(1);

    private final Integer role;

}
