package com.hisun.kugga.duke.enums.message;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @Description: 消息处理状态
 * @author： Lin
 * @Date 2022/7/28 9:31
 */
@Getter
public enum MessageDealStatusEnum {
    // 需要处理，无需处理、已处理
    /**
     * 未读
     */
    DEAL("D", "需处理"),
    /**
     * 已读
     */
    NO_DEAL("ND", "无需处理"),
    /**
     * 已处理
     */
    ALREADY_DEAL("AD", "已处理"),
    /**
     * 已过期
     */
    EXPIRE("EX", "已过期"),
    ;

    private String code;
    private String desc;

    MessageDealStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举对象
     *
     * @param code
     * @return
     */
    public static MessageDealStatusEnum getByCode(String code) {
        if (StrUtil.isEmpty(code)) {
            return null;
        }
        for (MessageDealStatusEnum dealStatusEnum : MessageDealStatusEnum.values()) {
            if (StrUtil.equals(code, dealStatusEnum.getCode())) {
                return dealStatusEnum;
            }
        }
        return null;
    }
}
