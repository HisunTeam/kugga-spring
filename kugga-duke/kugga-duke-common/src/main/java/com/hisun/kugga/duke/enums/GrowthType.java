package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zhou_xiong
 */
@Getter
@AllArgsConstructor
public enum GrowthType {
    /**
     * 发帖
     */
    POST("post","发帖"),
    /**
     * 同意聊天邀请
     */
    CHAT_OTHER("chat_other","同意其他公会成员发起的聊天"),
    /**
     * 为其他公会成员撰写推荐报告
     */
    WRITE_RECOMMENDATION("write_recommendation","为其他公会成员撰写推荐报告"),
    /**
     * 为其他公会认证
     */
    ENDORSE_OTHER("endorse_other","为其他公会认证"),
    /**
     * 邀请其他公会认证
     */
    ENDORSEMENT("endorsement","邀请其他公会认证"),
    /**
     * 回帖
     */
    REPLY_POST("reply_post","回帖"),
    /**
     * 被回帖
     */
    BY_REPLY_POST("by_reply_post","被回帖"),
    /**
     * 简历首次完善
     */
    PROFILE("profile","简历首次完善");

    @EnumValue
    private String code;
    private String desc;
}
