package com.hisun.kugga.duke.chat.enums;

import com.hisun.kugga.framework.common.exception.ErrorCode;

public interface ErrorCodeConstantsChat {

    /**
     * token 会话过期
     */
    ErrorCode MessageTokenExp = new ErrorCode(100401, "MessageTokenExp");
    ErrorCode RoomExpTime = new ErrorCode(100402, "RoomExpTime");

    /**
     * others
     */
    ErrorCode InputIsNull = new ErrorCode(100200, "InputIsNull:[%s]");

    ErrorCode RoomNotExists = new ErrorCode(100201, "RoomNotExists");

    ErrorCode SameUsersChat = new ErrorCode(100202, "SameUsersChat");

    ErrorCode UserNotInRoom = new ErrorCode(100203, "UserNotInRoom");

    ErrorCode UserNotInSameLeague = new ErrorCode(100204, "UserNotInSameLeague");

    ErrorCode ExistsFreeRoom = new ErrorCode(100205, "ExistsFreeRoom");

    ErrorCode MustInviterUserPay = new ErrorCode(100206, "Must Inviter User Pay");

}
