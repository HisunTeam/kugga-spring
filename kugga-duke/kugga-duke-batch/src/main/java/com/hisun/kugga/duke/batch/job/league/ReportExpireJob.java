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
 * 推荐报告失效定时任务
 *
 * @author: zuo_cheng
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
     * @param param 参数
     * @return
     * @throws Exception
     */
    @Override
    public String execute(String param) throws Exception {
        //查询未接单和已接单的数据
        List<LeagueNoticeStatusEnum> status = new ArrayList<>(2);
        status.add(LeagueNoticeStatusEnum.NOTICE_STATUS_1);
        status.add(LeagueNoticeStatusEnum.NOTICE_STATUS_2);

        log.info(" ReportExpire 定时处理推荐报告过期 start:{}", LocalDateTime.now());
        List<LeagueNoticeDO> list = leagueNoticeMapper.queryExpire(status);
        list.forEach(item -> redissonUtils.tryLock(TASK_LOCK_LEAGUE_NOTICE + item.getId(), () -> leagueTaskService.reportExpire(item)));

        // 消息过期
        dealMessageExpire(list);

        log.info("ReportExpire 定时处理推荐报告过期 end:{}", LocalDateTime.now());
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    private void dealMessageExpire(List<LeagueNoticeDO> list) {
        //任务失效后把消息改为 已过期

        for (LeagueNoticeDO noticeDO : list) {
            MessagesDO messagesDO = new MessagesDO().setDealFlag(MessageDealStatusEnum.EXPIRE.getCode());
            messagesMapper.updateExpireByBusinessId(messagesDO, noticeDO.getId());
        }
    }
}
