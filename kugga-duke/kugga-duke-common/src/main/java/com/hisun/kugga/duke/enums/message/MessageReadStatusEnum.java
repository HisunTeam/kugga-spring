package com.hisun.kugga.duke.enums.message;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @Description: 消息读取状态
 * @author： Lin
 * @Date 2022/7/28 9:31
 */
@Getter
public enum MessageReadStatusEnum {
    // 已读未读/已处理标识 未处理
    /**
     * 未读
     */
    UNREAD("UR", "未读"),
    /**
     * 已读
     */
    READ("R", "已读"),
    ;

    private String code;
    private String desc;

    MessageReadStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举对象
     *
     * @param code
     * @return
     */
    public static MessageReadStatusEnum getByCode(String code) {
        if (StrUtil.isEmpty(code)) {
            return null;
        }
        for (MessageReadStatusEnum readStatusEnum : MessageReadStatusEnum.values()) {
            if (StrUtil.equals(code, readStatusEnum.getCode())) {
                return readStatusEnum;
            }
        }
        return null;
    }
}
