package com.hisun.kugga.duke.enums.email;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;

/**
 * @author zhou_xiong
 */
public enum EmailType {
    /**
     * 简单邮件
     */
    SIMPLE_MAIL,
    /**
     * 附件邮件   暂不支持
     */
    ATTACHMENTS_MAIL,
    /**
     * Html 和图像邮件 暂不支持
     */
    HTML_AND_IMAGE_MAIL,
    /**
     * Html 邮件
     */
    SIMPLE_HTML_MAIL;

    public static EmailType getByValue(String value) {
        return Arrays.stream(values()).filter(v -> StrUtil.equals(v.name(), value))
                .findFirst().orElse(null);
    }

}
