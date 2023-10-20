package com.hisun.kugga.duke.batch.job.league;

import com.hisun.kugga.duke.batch.dal.dataobject.league.TaskDO;
import com.hisun.kugga.duke.batch.dal.dataobject.message.MessagesDO;
import com.hisun.kugga.duke.batch.dal.mysql.league.TaskMapper;
import com.hisun.kugga.duke.batch.dal.mysql.message.MessagesMapper;
import com.hisun.kugga.duke.batch.service.LeagueTaskService;
import com.hisun.kugga.duke.enums.message.MessageDealStatusEnum;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.quartz.core.handler.JobHandler;
import com.hisun.kugga.framework.utils.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Authentication Expiry Scheduled Task
 * This task handles the expiration of authentication for leagues.
 * Author: zuo_cheng
 */
@Slf4j
@Component
public class AuthExpireJob implements JobHandler {

    public static final String TASK_LOCK_LEAGUE_AUTH = "task:league_auth:";

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private LeagueTaskService leagueTaskService;

    @Resource
    private RedissonUtils redissonUtils;
    @Resource
    private MessagesMapper messagesMapper;

    /**
     * League Authentication Expiry and Refund Scheduled Task
     * This task performs the following steps:
     * 1. Scan tasks of type "public league authentication" with a status of "paid" and an expired time.
     * 2. Acquire a distributed lock with a key of "task:league_auth:" + the ID of the authenticated league.
     * 3. Update the announcement table by changing the status of announcements associated with the task ID to "refund completed" if their type is "public league authentication" (type 3) and their status is "published."
     * 4. Query the league authentication order table for records associated with the current task ID with a status of "paid" and sum up their amounts to calculate the total amount.
     * 5. Use the total amount and the "order_record" field of the task to call the refund API.
     *
     * @param param Parameters
     * @return Result message
     * @throws Exception
     */
    @Override
    public String execute(String param) throws Exception {
        log.info("Authentication Expiry Scheduled Task started: {}", LocalDateTime.now());
        List<TaskDO> list = taskMapper.queryAuthExpire();
        list.forEach(item ->
                redissonUtils.tryLock(TASK_LOCK_LEAGUE_AUTH + item.getOrderRecord(), () -> leagueTaskService.authExpire(item))
        );

        dealMessageExpire(list);

        log.info("Authentication Expiry Scheduled Task finished: {}", LocalDateTime.now());
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    private void dealMessageExpire(List<TaskDO> list) {
        // Change message status to "expired" after the task has expired
        for (TaskDO taskDO : list) {
            MessagesDO messagesDO = new MessagesDO().setDealFlag(MessageDealStatusEnum.EXPIRE.getCode());
            messagesMapper.updateExpireByBusinessId(messagesDO, taskDO.getId());
        }
    }
}
