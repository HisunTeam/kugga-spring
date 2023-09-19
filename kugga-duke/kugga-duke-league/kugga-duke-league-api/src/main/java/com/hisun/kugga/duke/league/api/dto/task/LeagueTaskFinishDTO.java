package com.hisun.kugga.duke.league.api.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:37
 */
@Data
public class LeagueTaskFinishDTO {

    /**
     * 任务表ID
     */
    private Long id;

    /**
     * 公会公告栏ID
     */
    private Long leagueNoticeId;

    /**
     * 任务类型
     * 1 推荐报告 2 公会认证 3 聊天
     */
    private LeagueTaskTypeEnum type;

    /**
     * 推荐人公会ID（写推荐报告 获得收益的公会ID）
     */
    private Long leagueId;

    @Getter
    @AllArgsConstructor
    public enum LeagueTaskTypeEnum {
        TASK_TYPE_1(1, "推荐报告"),
        TASK_TYPE_2(2, "公会认证"),
        TASK_TYPE_3(3, "聊天");
        int value;
        String desc;
    }
}
