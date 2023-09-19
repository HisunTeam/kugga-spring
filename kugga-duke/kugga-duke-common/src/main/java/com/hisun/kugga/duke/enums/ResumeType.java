package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zhou_xiong
 */
@AllArgsConstructor
@Getter
public enum ResumeType {
    /**
     * 学习经历
     */
    STUDY("0", "学习经历"),
    /**
     * 工作经历
     */
    WORK("1", "工作经历");

    @EnumValue
    String typeCode;
    String desc;
}
