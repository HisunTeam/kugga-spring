package com.hisun.kugga.duke.enums.message;

import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Description: 消息场景
 * @author： Lin
 * @Date 2022/7/28 9:31
 */
@Getter
@AllArgsConstructor
public enum MessageSceneEnum implements IntArrayValuable {
    //场景(推荐信、聊天、认证、邀请加入公会、系统通知)
    /**
     * 公会认证 authentication
     */
    LEAGUE_AUTHENTICATION(1, "auth", "公会认证"),
    /**
     * 推荐信 recommendation
     */
    RECOMMENDATION(2, "recom", "推荐信"),
    /**
     * 公会邀请 invite
     */
    LEAGUE_INVITE(3, "league_invite", "公会邀请"),
    /**
     * 聊天
     */
    CHAT(4, "chat", "用户聊天"),
    /**
     * 系统通知
     */
    SYSTEM_NOTICE(5, "system_notice", "系统通知"),
    /**
     * 加入公会通知
     */
    JOIN_LEAGUE(6, "join", "加入公会"),
    /**
     * 加入公会通知
     */
    JOIN_LEAGUE_FIRST(7, "join_league_first", "加入公会"),
    /**
     *
     */
    INVITE_LINK_EXPIRE(8, "invite_expire", "邀请连接失效"),

    LEAGUE_CREATE_AUTH(9, "create_league_auth", "创建公会后进行公会认证"),
    LEAGUE_CREATE_RULE(10, "create_league_rule", "创建公会后设置公会规则"),

    JOIN_LEAGUE_ACTIVE(11, "join_league_active", "主动加入公会"),
    /**
     * 公会订阅续期
     */
    LEAGUE_SUBSCRIBE_RENEWAL(12, "league_subscribe_renewal", "公会订阅续期消息通知"),
    LEAGUE_SUBSCRIBE_NO_BALANCE_QUIT(13, "league_subscribe_no_balance_quit", "公会订阅续期余额不足退出公会通知"),
    /**
     * 公会发红包 (公会分红)  share out bonus
     */
    LEAGUE_RED_PACKAGE(12, "league_red_package", "获得后端开发工程师公会100$的红包"),


    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(MessageSceneEnum::getCode).toArray();

    /**
     * 场景编号
     */
    private final Integer code;
    /**
     * 验证场景
     */
    private final String scene;
    /**
     * 描述
     */
    private final String description;

    @Override
    public int[] array() {
        return ARRAYS;
    }

//    public static MessageSceneEnum getCodeByScene(Integer scene) {
//        return ArrayUtil.firstMatch(sceneEnum -> sceneEnum.getScene().equals(scene),
//                values());
//    }

    public static MessageSceneEnum getByScene(String scene) {
        if (StrUtil.isEmpty(scene)) {
            return null;
        }
        for (MessageSceneEnum sceneEnum : MessageSceneEnum.values()) {
            if (StrUtil.equals(scene, sceneEnum.getScene())) {
                return sceneEnum;
            }
        }
        return null;
    }
}
