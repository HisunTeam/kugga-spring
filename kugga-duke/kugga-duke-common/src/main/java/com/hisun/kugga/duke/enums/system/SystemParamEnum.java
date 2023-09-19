package com.hisun.kugga.duke.enums.system;

import lombok.Getter;

/**
 * @Description: 系统参数表
 * @author： Lin
 * @Date 2022/9/23 15:48
 */
@Getter
public enum SystemParamEnum {

    /**
     * 任务 订单相关
     */
    TASK_AUTH("task", "auth", "公会认证 订单有效期 "),
    TASK_REPORT("task", "report", "写推荐信 订单有效期 "),
    TASK_CHAT("task", "chat", "聊天 订单有效期 "),
    TASK_JOIN_LEAGUE("task", "join_league", "加入公会 订单有效期 "),
    CREATE_LEAGUE_COST("league", "create_league_cost", "创建公会费用查询KEY"),
    LEAGUE_INVITE_URI_EFFECTIVE_UNIT("league", "league_invite_uri_effective_unit", "公会邀请短链失效单位配置查询KEY"),
    LEAGUE_INVITE_URI_EFFECTIVE_PERIOD("league", "league_invite_uri_effective_period", "公会邀请短链失效值配置查询KEY"),
    LEAGUE_INIT_AUTH_PRICE("league", "league_init_auth_price", "公会创建初始认证金额 查询KEY"),
    LEAGUE_INIT_REPORT_PRICE("league", "league_init_report_price", "公会创建初始写推荐报告金额查询KEY"),
    LEAGUE_INIT_CHAT_PRICE("league", "league_init_chat_price", "公会创建初始聊天价格查询KEY"),
    LEAGUE_INIT_USER_JOIN_PRICE("league", "league_init_user_join_price", "公会创建初始用户加入价格查询KEY"),
    ;

    /**
     * 编码
     */
    private final String type;
    /**
     * 描述
     */
    private final String paramKey;
    /**
     * 备注
     */
    private final String message;

    SystemParamEnum(String type, String paramKey, String message) {
        this.type = type;
        this.paramKey = paramKey;
        this.message = message;
    }
}
