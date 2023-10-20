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
 * Chat Expiry Scheduled Task
 * This task handles the expiration of chats.
 * Author: zuo_cheng
 */
@Slf4j
@Component
public class ChatExpireJob implements JobHandler {

    public static final String TASK_LOCK_CHAT = "task:chat:";

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private RedissonUtils redissonUtils;

    @Resource
    private LeagueTaskService leagueTaskService;

    @Resource
    private MessagesMapper messagesMapper;

    /**
     * Chat Expiry and Refund Scheduled Task
     * This task performs the following steps:
     * 1. Scan tasks of type "chat" with a status of "paid" and an expired time.
     * 2. Acquire a distributed lock with a key of "task:chat:" + the order number (TaskDO.orderRecord).
     * 3. Refund the amount:
     *    - Update the task status to "refund completed."
     *    - Change the status of announcements associated with the task ID to "refund completed" if their type is "chat" (type 5) and their status is "published."
     *    - Query the task chat order table for records associated with the current task ID with a status of "paid" and get their amount.
     *    - Call the refund API with the amount and the order record.
     * 4. Change the status of messages to "expired" after the task has expired.
     *
     * @param param Parameters
     * @return Result message
     * @throws Exception
     */
    @Override
    public String execute(String param) throws Exception {
        log.info("Chat Expiry Scheduled Task started: {}", LocalDateTime.now());
        List<TaskDO> list = taskMapper.queryChatExpire();
        list.forEach(item -> redissonUtils.tryLock(TASK_LOCK_CHAT + item.getOrderRecord(), () -> leagueTaskService.chatExpire(item)));

        dealMessageExpire(list);

        log.info("Chat Expiry Scheduled Task finished: {}", LocalDateTime.now());
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
