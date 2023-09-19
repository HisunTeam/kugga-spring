package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 交易类型
 *
 * @author: zhou_xiong
 */
@Getter
@AllArgsConstructor
public enum OrderType {
    /**
     * 创建公会
     */
    CREATE_LEAGUE("1", "Created new guild", "创建公会"),
    /**
     * 公会认证
     */
    AUTH_LEAGUE("2", "Endorsement", "公会认证"),
    /**
     * 编写推荐报告
     */
    CREATE_RECOMMENDATION("3", "Recommendation", "编写推荐报告"),
    /**
     * 第一次加入公会 主动邀请别人加入公会 第一个加入的人
     */
    JOIN_LEAGUE("4", "Bonus of joining guild", "加入公会"),
    /**
     * 发起聊天
     */
    CHAT("5", "Communication invitation", "发起聊天"),
    /**
     * 接受聊天
     */
    ACCEPT_CHAT("6", "Bonus of communication", "接受聊天"),
    /**
     * 退款
     */
    REFUND("7", "Refund-", "退款"),
    /**
     * 充值
     */
    CHARGE("8", "Transfer in", "充值"),
    /**
     * 获得红包/发红包
     */
    BONUS("9", "Bonus", "获得红包/发红包"),
    /**
     * 提现
     */
    WITHDRAW("10", "Withdraw", "提现"),
    /**
     * 主动加入他人工会 申请人付费   后面改为订阅模式的加入公会
     */
    ACTIVE_JOIN_LEAGUE("11", "Guild application fee", "申请加入公会付费"),
    /**
     * 加入公会的分账 工会的创建者分钱
     */
    ACTIVE_JOIN_LEAGUE_SPLIT("12", "Bonus of new member", "加入公会的分账"),
    /**
     * 公会订阅
     */
    SUBSCRIPTION_LEAGUE("13", "Subscription", "公会订阅"),
    /**
     * 公会订阅分账
     */
    SUBSCRIPTION_LEAGUE_SPLIT("14", "Bonus of subscription", "公会订阅分账");


    @EnumValue
    String typeCode;
    String desc;
    String zh;

    @JsonValue
    public String getZh() {
        return zh;
    }
}
