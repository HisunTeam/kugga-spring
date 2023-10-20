package com.hisun.kugga.duke.batch.job.league;

import com.hisun.kugga.duke.batch.dal.dataobject.league.TaskDO;
import com.hisun.kugga.duke.batch.dal.mysql.league.TaskMapper;
import com.hisun.kugga.duke.batch.service.LeagueTaskService;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.quartz.core.handler.JobHandler;
import com.hisun.kugga.framework.utils.RedissonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Scheduled Task for Passive Refund of League Certification
 * This task handles the passive refund of league certification tasks.
 * Author: zuo_cheng
 */
@Slf4j
@Component
@AllArgsConstructor
public class TaskLeagueAuthByRefundJob implements JobHandler {

    public static final String TASK_LOCK_LEAGUE_AUTH = "task:league_auth:";
    private TaskMapper taskMapper;
    private LeagueTaskService leagueTaskService;
    private RedissonUtils redissonUtils;

    /**
     * League Certification Passive Refund Scheduled Task
     * Process business logic before initiating refunds.
     * 1. Scan tasks with the type "league certification" and status "to be refunded."
     * 2. Acquire distributed locks with keys like "task:league_auth:" followed by the order record.
     * 3. Perform business refund operations:
     *    - Update task table status for the current task.
     *    - Update notice board status for the current task ID with type "league certification" (type 4) and status "to be refunded" (TASK_STATUS_4).
     *    - Query the league certification order table for the current task ID with status "paid" and sum the amounts to get the total amount.
     *    - Call the refund API with the total amount and task order record.
     *
     * @param param Parameter
     * @return
     * @throws Exception
     */
    @Override
    public String execute(String param) throws Exception {
        log.info("Scheduled Task for League Certification Passive Refund started: {}", LocalDateTime.now());
        List<TaskDO> list = taskMapper.queryLeagueAuthRefundList();
        list.stream().forEach(taskDO ->
                redissonUtils.tryLock(TASK_LOCK_LEAGUE_AUTH + taskDO.getOrderRecord(), () -> leagueTaskService.leagueAuthRefund(taskDO))
        );
        log.info("Scheduled Task for League Certification Passive Refund finished: {}", LocalDateTime.now());
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }
}
