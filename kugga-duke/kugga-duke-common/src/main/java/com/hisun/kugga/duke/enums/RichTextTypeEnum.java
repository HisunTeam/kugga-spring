package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 富文本内容类型枚举
 *
 * @author: zuoCheng
 */
@Getter
@AllArgsConstructor
public enum RichTextTypeEnum {
    /**
     * 文本
     */
    PARAGRAPH("paragraph"),
    /**
     * 用户
     */
    IMAGE("image"),
    /**
     * 平台
     */
    LIST("list"),
    ;

    @EnumValue
    String type;
}
