package com.hisun.kugga.duke.batch.job.subscribe;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.hisun.kugga.duke.batch.dal.dataobject.leaguesubscribe.LeagueSubscribeDO;
import com.hisun.kugga.duke.batch.dal.dataobject.message.MessagesDO;
import com.hisun.kugga.duke.batch.dal.mysql.leaguesubscribe.LeagueSubscribeMapper;
import com.hisun.kugga.duke.batch.dal.mysql.message.MessagesMapper;
import com.hisun.kugga.duke.batch.service.MessageService;
import com.hisun.kugga.duke.common.CommonConstants;
import com.hisun.kugga.duke.common.IdentifierConstants;
import com.hisun.kugga.duke.dto.GeneralEmailInnerReqDTO;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.entity.ContentParamVo;
import com.hisun.kugga.duke.enums.LanguageEnum;
import com.hisun.kugga.duke.enums.email.EmailScene;
import com.hisun.kugga.duke.enums.message.*;
import com.hisun.kugga.duke.innercall.InnerCallHelper;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.quartz.core.handler.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.hisun.kugga.duke.common.IdentifierConstants.dd_MM_yyyy;
import static com.hisun.kugga.duke.common.IdentifierConstants.yyyy_MM_dd;

/**
 * @Description: League Subscription Notification Job
 * This job sends notifications to users regarding their league subscriptions.
 * Author: Lin
 * Date: 2022/10/20 15:09
 */
@Slf4j
@Component
public class SubscribeNoticeJob implements JobHandler {

    // Email calling address
    @Value("${duke.league.backed.subscribeEmailNotice:}")
    private String emailUrl;
    // Redirect to the personal center subscription page
    @Value("${duke.league.fronted.subscribeView:}")
    private String mySubscribeView;

    @Resource
    private MessageService messageService;
    @Resource
    private MessagesMapper messagesMapper;
    @Resource
    private LeagueSubscribeMapper subscribeMapper;
    @Resource
    private InnerCallHelper innerCallHelper;

    /**
     * League subscription packages, scheduled notifications
     *
     * @param param Parameters
     * @return
     * @throws Exception
     */
    @Override
    public String execute(String param) throws Exception {
        log.info("SubscribeNoticeJob: Processing scheduled subscription notification data start:{}", param);

        // Notify three days in advance, date +3, status is true, query all subscriptions in progress
        LocalDateTime now = LocalDateTime.now();
        String expireTime = DateUtil.format(now.plusDays(3L), yyyy_MM_dd);
        List<LeagueSubscribeDO> subscribes = subscribeMapper.selectSubscribes2(expireTime, true);
        if (ObjectUtil.isEmpty(subscribes)) {
            return GlobalErrorCodeConstants.SUCCESS.getMsg();
        }

        for (LeagueSubscribeDO subscribe : subscribes) {
            if (ObjectUtil.equal(BigDecimal.ZERO, subscribe.getPrice())) {
                continue;
            }
            // Send an internal message
            sendMessage(subscribe);

            // Send an HTTP request to send a message
            httpInvokeSendMessage(subscribe);

        }

        log.info("SubscribeNoticeJob: Processing scheduled subscription notification data end:{}", LocalDateTime.now());
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    /**
     * Send a message to the user about the expiration
     *
     * @param subscribe
     */
    private void sendMessage(LeagueSubscribeDO subscribe) {
        ContentParamVo contentParamVo = new ContentParamVo()
                .setReceiverId(subscribe.getUserId())
                .setInitiatorLeagueId(subscribe.getLeagueId())
                .setInitiatorId(0L)
                .setLeagueSubscribeVO(new ContentParamVo.LeagueSubscribeVO()
                        .setAmount(subscribe.getPrice())
                        .setExpireTime(subscribe.getExpireTime())
                );

        SendMessageReqDTO message = new SendMessageReqDTO()
                .setMessageTemplate(MessageTemplateEnum.LEAGUE_SUBSCRIBE_RENEWAL)
                .setMessageScene(MessageSceneEnum.LEAGUE_SUBSCRIBE_RENEWAL)
                .setMessageType(MessageTypeEnum.INVITE)
                .setBusinessId(subscribe.getId())
                .setReceivers(Collections.singletonList(subscribe.getUserId()))
                .setMessageParam(contentParamVo)
                .setLanguage(LanguageEnum.en_US)
                .setDealStatus(MessageDealStatusEnum.NO_DEAL);
        messageService.sendMessage(message);
    }

    /**
     * Send an HTTP request to send a message
     */
    private void httpInvokeSendMessage(LeagueSubscribeDO subscribe) {

        String uuid = innerCallHelper.genCert(subscribe.getId());

        ArrayList<String> replaceValues = new ArrayList<>();
        replaceValues.add(subscribe.getLeagueName());
        replaceValues.add(DateUtil.format(subscribe.getExpireTime(), dd_MM_yyyy));
        replaceValues.add(subscribe.getPrice().toString());
        replaceValues.add(mySubscribeView);

        GeneralEmailInnerReqDTO innerReqDTO = new GeneralEmailInnerReqDTO();
        innerReqDTO.setUuid(uuid);
        innerReqDTO.setBusinessId(subscribe.getId());
        innerReqDTO.setEmailScene(EmailScene.LEAGUE_SUBSCRIBE_RENEWAL);
        innerReqDTO.setTo(Arrays.asList(subscribe.getEmail()));
        innerReqDTO.setReplaceValues(replaceValues);
        innerReqDTO.setLocale(CommonConstants.EN_US);

        try {
            innerCallHelper.post(emailUrl, innerReqDTO, GeneralEmailInnerReqDTO.class);
        } catch (Exception exception) {
            log.error("Subscription renewal - email notification exception:", exception);
        }
    }
}
