package com.hisun.kugga.duke.enums.message;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @Description: 消息模板
 * @author： Lin
 * @Date 2022/7/28 9:31
 */
@Getter
public enum MessageTemplateEnum {
    /**
     * 四位数编码
     * 00 公会相关
     * 01 推荐信相关
     * 02 聊天相关
     * 03 系统通知
     * 04 到账通知
     * 05 加入公会审批
     */
    /**
     * 公会相关 00
     */
    LEAGUE_AUTH_INVITE("0001", "公会认证邀请", "{张三}邀请为[{后端开发工程师}]做公会认证"),
    LEAGUE_AUTH_CALLBACK("0002", "公会认证回调给发起人", "{李四}为[{后端开发工程师}]做公会认证 "),

    LEAGUE_JOIN_INVITE("0003", "公会加入-邀请加入公会", "你被邀请加入[XXX]公会."),

//    LEAGUE_CREATE_CALLBACK("0004","公会创建-回调给发起人","{张三}接受了我创建公会的邀请，公会已正式成立，快去完成公会认证吧"),
//    LEAGUE_CREATE_AUTH_CALLBACK("0005","公会创建-认证设置公会规则","【{后端开发工程师}】公会已完成公会认证，快去设置公会规则吧"),

    LEAGUE_FIRST_JOIN_INVITE("0006", "公会加入-第一个人加入公会", "您是第一个受邀加入公会后端开发工程师的会员，并获得{10}美元的奖金。"),
    LEAGUE_FIRST_JOIN_INVITE_FREE("0007", "公会加入-第一个人加入公会-免费", "您是第一个受邀加入公会后端开发工程师的会员"),

    LEAGUE_CREATE_AUTH_INVITE("0008", "创建公会-通知进行公会认证", "{张三}接受了我创建公会的邀请，公会已正式成立，快去完成公会认证吧"),
    LEAGUE_CREATE_RULE_INVITE("0009", "创建公会-通知进行规则设置", "【{后端开发工程师}】公会已完成公会认证，快去设置公会规则吧"),

    LEAGUE_AUTHENTICATION_CALLBACK_RECEIVER("0010", "公会认证-回调接收方", "[产品经理]公会张三已完成公会认证"),

    /**
     * 推荐信相关 01
     */
    RECOMMENDATION_INVITE("0101", "邀请写推荐信", "{张三}邀请写推荐报告，您将获得${50}的收益"),
    RECOMMENDATION_INVITE_FREE("0102", "邀请写推荐信-免费", "{张三}邀请写推荐报告"),
    RECOMMENDATION_CALLBACK("0103", "写推荐信回调给发起人", "{李四}为我写了推荐报告"),
    // 推荐信写完后  您撰写推荐报告，到账${50}  见 REACH_ACCOUNT_RECOMMENDATION
    // RECOMMENDATION_CALLBACK_RECEIVER("0104","写推荐信回调接收方","您撰写推荐报告，到账${50}"),
    RECOMMENDATION_CALLBACK_RECEIVER2("0105", "写推荐信回调接收方", "我为{张三}写了推荐报告"),

    /**
     * 聊天相关 02
     */
    CHAT_INVITE("0201", "聊天-邀请", "{张三}邀请与我聊天，您将获得${50}的收益"),
    CHAT_INVITE_FREE("0202", "聊天-邀请免费", "{张三}邀请与我聊天"),
    CHAT_CALLBACK("0203", "聊天-回调给邀请人", "李四同意与我聊天"),
    //这条见 REACH_ACCOUNT_CHAT
    // CHAT_CALLBACK_RECEIVER("0204","聊天-回调给接受人","您同意与他人聊天，到账$50"),
    CHAT_CALLBACK_RECEIVER2("0205", "聊天-回调给接受人免费", "您同意与他人聊天"),

    /**
     * 03 系统通知
     */
    SYSTEM_NOTICE_PLATFORM_UPDATE("0301", "系统通知-平台更新", "Duke全平台{2022-10-1 12:00}至{2022-10-2 12:00}进行系统升级"),
    /**
     * 04 到账通知
     */
    REACH_ACCOUNT_AUTH("0401", "到账通知-公会认证到账", "您为公会做公会认证，到账${50}"),
    REACH_ACCOUNT_RECOMMENDATION("0402", "到账通知-推荐信到账", "您撰写推荐报告，到账${50}"),
    REACH_ACCOUNT_CHAT("0403", "到账通知-聊天到账", "您同意与他人聊天，到账${50}"),

    /**
     * 05 加入公会审批
     * xxx公会拒绝您的加入申请；xxx公会同意您的加入申请；因为本次加入申请过期，xxx公会已经拒绝您的申请。
     */
    JOIN_LEAGUE_ACTIVE_AGREE("0501", "主动加入-审批同意申请", "[{后端开发工程师}]公会拒绝您的加入申请"),
    JOIN_LEAGUE_ACTIVE_REJECT("0502", "主动加入-审批拒绝申请", "[{后端开发工程师}]公会同意您的加入申请"),
    JOIN_LEAGUE_ACTIVE_EXPIRE("0503", "主动加入-过期拒绝申请", "因为本次加入申请过期，[{后端开发工程师}]公会已经拒绝您的申请"),

    /**
     * 06 其他
     */
    INVITE_LINK_EXPIRE_CREATE_LEAGUE("0601", "邀请链接失效通知", "创建公会的邀请链接已失效 "),

    /**
     * 07 公会订阅
     */
    LEAGUE_SUBSCRIBE_RENEWAL("0701", "公会订阅续期", "您在{后端开发工程师公会}的订阅将于{2022年11月11日}以{10}$/月的价格自动续期，清保持账户余额充足 "),
    LEAGUE_SUBSCRIBE_NO_BALANCE_QUIT("0702", "订阅续期余额不足消息通知", "由于帐户余额不足，您对｛xxx｝公会的订阅已暂停。您将无法访问｛xxx｝公会及其福利。"),

    /**
     * 08 公会发红包 (公会分红)  share out bonus
     */
    LEAGUE_RED_PACKAGE("0801", "公会发红包(分红)", "获得后端开发工程师公会100$的红包"),
    ;

    /**
     * 编码
     */
    private final String code;
    /**
     * 描述
     */
    private final String desc;
    /**
     * 消息内容{} 中是待格式化参数
     */
    private final String message;

    MessageTemplateEnum(String code, String desc, String message) {
        this.code = code;
        this.desc = desc;
        this.message = message;
    }


    /**
     * 根据code获取枚举对象
     *
     * @param code
     * @return
     */
    public static MessageTemplateEnum getByCode(String code) {
        if (StrUtil.isEmpty(code)) {
            return null;
        }
        for (MessageTemplateEnum messageTypeEnum : MessageTemplateEnum.values()) {
            if (StrUtil.equals(code, messageTypeEnum.getCode())) {
                return messageTypeEnum;
            }
        }
        return null;
    }
}
