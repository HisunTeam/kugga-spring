package com.hisun.kugga.duke.batch.job.league;

import com.hisun.kugga.duke.batch.dal.dataobject.league.LeagueInviteUrlBindDO;
import com.hisun.kugga.duke.batch.dal.dataobject.message.MessagesDO;
import com.hisun.kugga.duke.batch.dal.mysql.league.LeagueInviteUrlBindMapper;
import com.hisun.kugga.duke.batch.dal.mysql.message.MessagesMapper;
import com.hisun.kugga.duke.batch.service.MessageService;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.entity.ContentParamVo;
import com.hisun.kugga.duke.enums.LanguageEnum;
import com.hisun.kugga.duke.enums.message.*;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.quartz.core.handler.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Invite Connection Expiry Scheduled Task
 * This task handles the expiration of invitation links.
 * Author: zuo_cheng
 */
@Slf4j
@Component
public class InviteConnectionExpireJob implements JobHandler {

    @Resource
    private LeagueInviteUrlBindMapper leagueInviteUrlBindMapper;

    @Resource
    private MessagesMapper messagesMapper;

    @Resource
    private MessageService messageService;

    @Override
    public String execute(String param) throws Exception {
        log.info("Invite Connection Expiry Scheduled Task started: {}", LocalDateTime.now());
        List<LeagueInviteUrlBindDO> list = leagueInviteUrlBindMapper.selectShouldExpire(LocalDateTime.now().withNano(0));

        list.forEach(item -> {
            try {
                // Update to expired status and ignore all errors (one error should not affect others)
                LeagueInviteUrlBindDO update = new LeagueInviteUrlBindDO();
                update.setId(item.getId());
                update.setExpireStatus(true);
                leagueInviteUrlBindMapper.updateById(update);
                // Sleep for 1 second before each request (reduce pressure). Query up to 1000 records at a time. Theoretically, it can be completed in 16 minutes.
                Thread.sleep(1000);
                // Send a message to the user
                sendMessage(item.getId(), item.getUserId());
            } catch (Exception e) {
                log.error("Failed to send an invitation notification to user [{}] (link [Link ID: {}] has expired). Note: This error is only caught and does not interrupt the business (for easier problem verification).", item.getUserId(), item.getId(), e);
            }
        });

        log.info("Invite Connection Expiry Scheduled Task finished: {}", LocalDateTime.now());
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    /**
     * Send a message to the user
     *
     * @param userId User ID
     */
    private void sendMessage(Long bindId, Long userId) {
        SendMessageReqDTO message = new SendMessageReqDTO()
                .setMessageTemplate(MessageTemplateEnum.INVITE_LINK_EXPIRE_CREATE_LEAGUE)
                .setMessageScene(MessageSceneEnum.INVITE_LINK_EXPIRE)
                .setMessageType(MessageTypeEnum.INVITE)
                .setBusinessId(bindId)
                .setReceivers(Collections.singletonList(userId))
                .setMessageParam(new ContentParamVo().setInitiatorId(0L))
                .setLanguage(LanguageEnum.en_US)
                .setDealStatus(MessageDealStatusEnum.NO_DEAL);
        messageService.sendMessage(message);
    }
}
