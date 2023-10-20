package com.hisun.kugga.duke.batch.job.league;

import com.hisun.kugga.duke.batch.dal.dataobject.league.LeagueNoticeDO;
import com.hisun.kugga.duke.batch.dal.dataobject.message.MessagesDO;
import com.hisun.kugga.duke.batch.dal.mysql.league.LeagueNoticeMapper;
import com.hisun.kugga.duke.batch.dal.mysql.message.MessagesMapper;
import com.hisun.kugga.duke.batch.service.LeagueTaskService;
import com.hisun.kugga.duke.enums.LeagueNoticeStatusEnum;
import com.hisun.kugga.duke.enums.message.MessageDealStatusEnum;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.quartz.core.handler.JobHandler;
import com.hisun.kugga.framework.utils.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Scheduled Task for Expired Recommendation Reports
 * This task handles the expiration of recommendation reports and updates their status.
 * Author: zuo_cheng
 */
@Slf4j
@Component
public class ReportExpireJob implements JobHandler {
    public static final String TASK_LOCK_LEAGUE_NOTICE = "task:leagueNotice:";

    @Resource
    private RedissonUtils redissonUtils;

    @Resource
    private LeagueTaskService leagueTaskService;

    @Resource
    private LeagueNoticeMapper leagueNoticeMapper;
    @Resource
    private MessagesMapper messagesMapper;


    /**
     * @param param Parameter
     * @return
     * @throws Exception
     */
    @Override
    public String execute(String param) throws Exception {
        // Query data that is not accepted and already accepted
        List<LeagueNoticeStatusEnum> status = new ArrayList<>(2);
        status.add(LeagueNoticeStatusEnum.NOTICE_STATUS_1);
        status.add(LeagueNoticeStatusEnum.NOTICE_STATUS_2);

        log.info("Scheduled Task for Report Expiration started: {}", LocalDateTime.now());
        List<LeagueNoticeDO> list = leagueNoticeMapper.queryExpire(status);
        list.forEach(item -> redissonUtils.tryLock(TASK_LOCK_LEAGUE_NOTICE + item.getId(), () -> leagueTaskService.reportExpire(item)));

        // Expire messages
        dealMessageExpire(list);

        log.info("Scheduled Task for Report Expiration finished: {}", LocalDateTime.now());
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    private void dealMessageExpire(List<LeagueNoticeDO> list) {
        // Change messages to expired after the tasks expire

        for (LeagueNoticeDO noticeDO : list) {
            MessagesDO messagesDO = new MessagesDO().setDealFlag(MessageDealStatusEnum.EXPIRE.getCode());
            messagesMapper.updateExpireByBusinessId(messagesDO, noticeDO.getId());
        }
    }
}
