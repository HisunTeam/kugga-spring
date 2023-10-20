package com.hisun.kugga.duke.batch.job.subscribe;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.hisun.kugga.duke.batch.dal.dataobject.leaguesubscribe.LeagueSubscribeDO;
import com.hisun.kugga.duke.batch.dal.dataobject.message.MessagesDO;
import com.hisun.kugga.duke.batch.dal.mysql.leaguesubscribe.LeagueSubscribeFlowMapper;
import com.hisun.kugga.duke.batch.dal.mysql.leaguesubscribe.LeagueSubscribeMapper;
import com.hisun.kugga.duke.batch.dal.mysql.message.MessagesMapper;
import com.hisun.kugga.duke.batch.service.MessageService;
import com.hisun.kugga.duke.entity.ContentParamVo;
import com.hisun.kugga.duke.enums.message.*;
import com.hisun.kugga.duke.innercall.InnerCallHelper;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.quartz.core.handler.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.hisun.kugga.duke.common.IdentifierConstants.yyyy_MM_dd;

/**
 * @Description: League Subscription - Quit League
 * This job handles the process of members quitting a league when their subscription expires.
 * Author: Lin
 * Date: 2022/10/20 15:09
 */
@Slf4j
@Component
public class SubscribeQuitJob implements JobHandler {

    /**
     * Insufficient Balance Error Code
     */
    private static final String Insufficient_Balance_Error_Code = "130003";

    @Value("${duke.league.backed.subscribeOrderPay:}")
    private String url;
    @Resource
    private LeagueSubscribeMapper subscribeMapper;
    @Resource
    private LeagueSubscribeFlowMapper flowMapper;
    @Resource
    private InnerCallHelper innerCallHelper;
    @Resource
    private MessageService messageService;
    @Resource
    private MessagesMapper messagesMapper;

    /**
     * Handle league subscription cancellations.
     *
     * @param param Parameters
     * @return
     * @throws Exception
     */
    @Override
    public String execute(String param) throws Exception {
        log.info("SubscribePayJob: Processing league subscription cancellations start:{}", param);

        /*
        SubscribePayJob runs scheduled tasks to renew subscriptions that expired on nowDate-1,
            with subscription status status=true and expireStatus=false.
        SubscribeQuitJob runs scheduled tasks to process cancellations of subscriptions that expired on nowDate-1,
            with status=false (members waiting to quit the league) and expireStatus=false (not already processed).
            It updates expireStatus=true, removes members from the league, and sends a cancellation message.
        */

        LocalDateTime now = LocalDateTime.now();
        String expireTime = DateUtil.format(now.minusDays(1L), yyyy_MM_dd);
        List<LeagueSubscribeDO> subscribes = subscribeMapper.selectSubscribes(expireTime, false, false);
        if (ObjectUtil.isEmpty(subscribes)) {
            return GlobalErrorCodeConstants.SUCCESS.getMsg();
        }

        for (LeagueSubscribeDO subscribe : subscribes) {
            doQuitSubscribe(subscribe);
        }

        log.info("SubscribePayJob: Processing league subscription cancellations end:{}", LocalDateTime.now());
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    /**
     * Handle subscription cancellation.
     *
     * @param subscribe
     */
    private void doQuitSubscribe(LeagueSubscribeDO subscribe) {
        // Update the expiration status, remove league members, and send a notification
        subscribeMapper.updateSubscribeExpireStatusToTrue(subscribe.getId());
        subscribeMapper.deleteLeagueMember(subscribe.getLeagueId(), subscribe.getUserId(), LocalDateTime.now());
    }
}
