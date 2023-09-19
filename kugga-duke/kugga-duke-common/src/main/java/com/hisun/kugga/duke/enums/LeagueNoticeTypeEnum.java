package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 9:29
 */
@Getter
@AllArgsConstructor
public enum LeagueNoticeTypeEnum {
    /**
     * 公会规则枚举
     */
    NOTICE_TYPE_1(1, "张三（99duke*）邀请公会成员编写推荐报告，可为公会获得￥100的收益 去编写>"),
    NOTICE_TYPE_2(2, "张三为李四（99duke*）写了推荐报告，公会获得￥100的收益"),

    NOTICE_TYPE_3(3, "布鲁斯（99duke*）邀请做公会认证，可为公会获得￥100的收益 去处理 >"),
    NOTICE_TYPE_4(4, "布鲁斯（99duke*）为【产品经理公会】做公会认证，公会获得￥100的收益"),

    NOTICE_TYPE_5(5, "布鲁斯（99duke*）邀请与张三（99duke*）聊天 ，可为公会获得￥100的收益 去处理 >"),
    NOTICE_TYPE_6(6, "张三已同意与李四（99duke*）聊天，公会获得￥100的收益"),
    NOTICE_TYPE_7(7, "张三已同意与李四（99duke*）聊天，公会获得￥100的收益"),
    NOTICE_TYPE_8(8, "公会发放红包￥100");
    @EnumValue
    private int value;
    private String desc;
}
