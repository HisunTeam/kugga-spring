package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 分账枚举类
 *
 * @author admin
 */
@Getter
@AllArgsConstructor
public enum SplitAccountEnum {

    /**     111.11  0.5 55.555   55.55
     *              0.3 33.333   33.33
     *              0.2 22.222   22.23
     *              精度问题都先算出个人和公会的，平台金额=总金额-其两个相加
     *       111.1  0.5 55.55
     *              0.3 33.33
     *              0.2 22.22
     * 推荐信、聊天按用户付款时的5:3:2比例分成，5成到达另一方用户
     * 公会认证 此时另一方用户单纯的点击，没有收益，公会平台8：2分
     */
    /**
     * 分账比例
     */
    SPLIT_ACCOUNT_ONE("5:3:2", "个人:公会:平台分账比例"),
    /**
     * 111.11  0.8 88.888  保留两位    88.88
     * 0.2 22.222             22.23
     */
    SPLIT_ACCOUNT_TWO("8:2", "个人:公会:平台分账比例"),

    ;
    @EnumValue
    String rule;
    String desc;
}
