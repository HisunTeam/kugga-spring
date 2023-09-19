package com.hisun.kugga.duke.common;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/7 15:37
 */
public class RedisKeyPrefixConstants {
    /**
     * 密码输入时的随机数token key (支付、登录)及其修改
     */
    public static final String SECRET_RANDOM_LOGIN_KEY = "secret:random:login:%s";
    public static final String SECRET_RANDOM_LOGIN_UPDATE_KEY = "secret:random:login_update:%s";
    public static final String SECRET_RANDOM_PAY_KEY = "secret:random:pay:%s";
    public static final String SECRET_RANDOM_PAY_UPDATE_KEY = "secret:random:pay_update:%s";

    /**
     * order模块的订单随机数 幂等限制
     */
    public static final String ORDER_LEAGUE_JOIN_KEY = "order:joinLeague:random:%s";
    /**
     * 订单 工会订阅
     */
    public static final String ORDER_LEAGUE_SUBSCRIBE = "order:leagueSubscribe:%s";


    /**
     * task 任务锁前缀
     * 公会认证
     * 写推荐信
     * 聊天
     */
    public static final String TASK_LOCK_LEAGUE_AUTH = "task:league_auth:";
    public static final String TASK_LOCK_RECOMMENDATION = "task:recommendation:";
    public static final String TASK_LOCK_CHAT = "task:chat:";

    /**
     * 论坛贴子赞
     */
    public static final String FORUM_POSTS_PRAISE = "forum:posts:praise:";
    /**
     * 论坛楼层赞
     */
    public static final String FORUM_FLOOR_PRAISE = "forum:floor:praise:";
    /**
     * 论坛讨论赞
     */
    public static final String FORUM_COMMENT_PRAISE = "forum:comment:praise:";
    /**
     * 论坛贴子踩
     */
    public static final String FORUM_POSTS_TRAMPLE = "forum:posts:trample:";
    /**
     * 论坛楼层踩
     */
    public static final String FORUM_FLOOR_TRAMPLE = "forum:floor:trample:";
    /**
     * 论坛讨论踩
     */
    public static final String FORUM_COMMENT_TRAMPLE = "forum:comment:trample:";
    /**
     * 论坛贴子收藏
     */
    public static final String FORUM_POSTS_COLLECTION = "forum:posts:collection:";
}
