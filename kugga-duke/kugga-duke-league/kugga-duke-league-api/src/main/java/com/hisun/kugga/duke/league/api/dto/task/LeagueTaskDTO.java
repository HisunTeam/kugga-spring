package com.hisun.kugga.duke.league.api.dto.task;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:37
 */
@Data
public class LeagueTaskDTO {

    /**
     * 任务表ID
     */
    private Long id;

    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;
}
