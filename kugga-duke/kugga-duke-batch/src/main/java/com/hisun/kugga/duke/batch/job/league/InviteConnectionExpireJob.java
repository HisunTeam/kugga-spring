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
 * 邀请连接失效定时任务
 *
 * @author: zuo_cheng
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
        log.info(" inviteConnectionExpire 定时处理邀请链接失效数据 start:{}", LocalDateTime.now());
        List<LeagueInviteUrlBindDO> list = leagueInviteUrlBindMapper.selectShouldExpire(LocalDateTime.now().withNano(0));

        list.forEach(item -> {
            try {
                //更新成失效，忽略所有报错(一个报错,不影响其它数据)
                LeagueInviteUrlBindDO update = new LeagueInviteUrlBindDO();
                update.setId(item.getId());
                update.setExpireStatus(true);
                leagueInviteUrlBindMapper.updateById(update);
                //每次请求前sleep 1秒钟(减小下压力),每次查询至多1000条数据,理论上16分钟可以发完
                Thread.sleep(1000);
                //向用户发送消息
                sendMessage(item.getId(), item.getUserId());
            } catch (Exception e) {
                log.error("向用户[{}]发起邀请(链接[链接ID:{}]已失效)的通知失败.PS:此错误仅做捕捉,不中断业务(方便问题核实)", item.getUserId(), item.getId(), e);
            }
        });

        log.info("inviteConnectionExpire 定时处理邀请链接失效数据 end:{}", LocalDateTime.now());
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }


    /**
     * 给用户发消息
     *
     * @param userId
     */
    private void sendMessage(Long bindId, Long userId) {
//        String content = messageService.getContent(MessageTemplateEnum.INVITE_LINK_EXPIRE_CREATE_LEAGUE);
//        MessagesDO insert = new MessagesDO()
//                .setMessageKey(MessageTemplateEnum.INVITE_LINK_EXPIRE_CREATE_LEAGUE.name())
//                .setScene(MessageSceneEnum.INVITE_LINK_EXPIRE.getScene())
//                .setType(MessageTypeEnum.INVITE.getCode())
//                .setInitiatorId(0L)
//                .setReceiverId(userId)
//                .setContent(content)
//                .setMessageParam("{\"initiatorId\": 0}")
//                .setReadFlag(MessageReadStatusEnum.UNREAD.getCode())
//                .setDealFlag(MessageDealStatusEnum.DEAL.getCode());
//
//        messagesMapper.insert(insert);

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
