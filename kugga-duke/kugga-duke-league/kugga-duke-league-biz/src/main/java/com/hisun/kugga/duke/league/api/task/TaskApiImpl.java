package com.hisun.kugga.duke.league.api.task;

import com.hisun.kugga.duke.league.api.dto.task.LeagueTaskDTO;
import com.hisun.kugga.duke.league.api.dto.task.LeagueTaskFinishDTO;
import com.hisun.kugga.duke.league.bo.task.TaskFinishBO;
import com.hisun.kugga.duke.league.dal.dataobject.TaskDO;
import com.hisun.kugga.duke.league.dal.mysql.TaskMapper;
import com.hisun.kugga.duke.league.service.TaskService;
import com.hisun.kugga.framework.common.util.json.JsonUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-08-01 15:25
 */
@Service
public class TaskApiImpl implements TaskApi {

    @Resource
    TaskService taskService;
    @Resource
    TaskMapper taskMapper;

    @Override
    public LeagueTaskDTO queryTaskInfo(Long id) {
        TaskDO task = taskMapper.selectById(id);
        return new LeagueTaskDTO().setId(task.getId()).setExpiresTime(task.getExpiresTime());
    }

    @Override
    public void finish(LeagueTaskFinishDTO dto) {
        TaskFinishBO bo = JsonUtils.parseObject(JsonUtils.toJsonString(dto), TaskFinishBO.class);
        taskService.finish(bo);
    }
}
