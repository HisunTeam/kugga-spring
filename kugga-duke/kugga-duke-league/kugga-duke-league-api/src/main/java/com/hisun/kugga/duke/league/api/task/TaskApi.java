package com.hisun.kugga.duke.league.api.task;

import com.hisun.kugga.duke.league.api.dto.task.LeagueTaskDTO;
import com.hisun.kugga.duke.league.api.dto.task.LeagueTaskFinishDTO;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-08-01 15:24
 */
public interface TaskApi {
    /**
     * 查询任务信息
     *
     * @param id
     * @return
     */
    LeagueTaskDTO queryTaskInfo(Long id);

    /**
     * 完成任务
     *
     * @param dto
     */
    void finish(LeagueTaskFinishDTO dto);
}
