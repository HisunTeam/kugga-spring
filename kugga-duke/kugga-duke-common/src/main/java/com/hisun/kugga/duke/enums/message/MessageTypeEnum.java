package com.hisun.kugga.duke.enums.message;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @Description: 消息状态
 * @author： Lin
 * @Date 2022/7/28 9:31
 */
@Getter
public enum MessageTypeEnum {

    /**
     * 邀请 发起通知
     * 免费的统一用2
     */
    INVITE("I", "邀请"),
    INVITE2("I2", "邀请"),
    /**
     * 回调通知 这个默认回调为发起者  免费的统一用2
     */
    CALLBACK("C", "回调"),
    CALLBACK2("C2", "回调2"),
    /**
     * 回调给接收方通知
     * receiver  免费的统一用2
     */
    CALLBACK_RECEIVER("CR", "回调接收方"),
    CALLBACK_RECEIVER2("CR2", "回调接收方2"),
    ;

    private String code;
    private String desc;

    MessageTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举对象
     *
     * @param code
     * @return
     */
    public static MessageTypeEnum getByCode(String code) {
        if (StrUtil.isEmpty(code)) {
            return null;
        }
        for (MessageTypeEnum messageTypeEnum : MessageTypeEnum.values()) {
            if (StrUtil.equals(code, messageTypeEnum.getCode())) {
                return messageTypeEnum;
            }
        }
        return null;
    }
}
