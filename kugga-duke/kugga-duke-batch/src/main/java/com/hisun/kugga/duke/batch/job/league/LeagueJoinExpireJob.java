package com.hisun.kugga.duke.batch.job.league;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.batch.dal.dataobject.leaguejoin.LeagueJoinApprovalDO;
import com.hisun.kugga.duke.batch.dal.dataobject.leaguejoin.LeagueJoinDO;
import com.hisun.kugga.duke.batch.dal.dataobject.message.MessagesDO;
import com.hisun.kugga.duke.batch.dal.mysql.leaguejoin.LeagueJoinApprovalMapper;
import com.hisun.kugga.duke.batch.dal.mysql.leaguejoin.LeagueJoinMapper;
import com.hisun.kugga.duke.batch.dal.mysql.message.MessagesMapper;
import com.hisun.kugga.duke.batch.dto.RefundReqDTO;
import com.hisun.kugga.duke.batch.service.MessageService;
import com.hisun.kugga.duke.batch.service.PayOrderService;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.entity.ContentParamVo;
import com.hisun.kugga.duke.enums.LanguageEnum;
import com.hisun.kugga.duke.enums.LeagueJoinApprovalTypeEnum;
import com.hisun.kugga.duke.enums.PayStatusEnum;
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
 * Joining League Expiry Refund Scheduled Task
 * This task handles the refund for expired join requests to a league.
 * Author: Lin
 * Date: 2022/9/15 15:09
 */
@Slf4j
@Component
public class LeagueJoinExpireJob implements JobHandler {

    @Resource
    private LeagueJoinMapper joinMapper;
    @Resource
    private LeagueJoinApprovalMapper approvalMapper;
    @Resource
    private PayOrderService payOrderService;
    @Resource
    private MessageService messageService;
    @Resource
    private MessagesMapper messagesMapper;

    @Override
    public String execute(String param) throws Exception {
        log.info("League Join Expire Job started: {}", param);
        // Select join records that have business status as initialized (0), payment status as free (0) or paid (2) (not unpaid 1), and expiration time less than current time.

        List<LeagueJoinDO> expireRecords = joinMapper.selectExpireRecords();
        if (ObjectUtil.isEmpty(expireRecords)) {
            return GlobalErrorCodeConstants.SUCCESS.getMsg();
        }
        expireRecords.forEach(item -> {
            try {
                // Update join and approval records to expired status
                LeagueJoinDO joinDO = new LeagueJoinDO()
                        .setId(item.getId())
                        .setBusinessStatus(LeagueJoinApprovalTypeEnum.EXPIRE.getValue())
                        .setAmountStatus(item.getPayFlag() ? PayStatusEnum.REFUND.getValue() : null);
                joinMapper.updateById(joinDO);

                LeagueJoinApprovalDO approvalDO = new LeagueJoinApprovalDO()
                        .setBusinessId(joinDO.getId())
                        .setStatus(LeagueJoinApprovalTypeEnum.EXPIRE.getValue());
                LambdaQueryWrapper<LeagueJoinApprovalDO> wrapper = new LambdaQueryWrapper<LeagueJoinApprovalDO>().eq(LeagueJoinApprovalDO::getBusinessId, joinDO.getId());
                approvalMapper.update(approvalDO, wrapper);

                sendMessage(item);

                // Refund only for paid entries, free entries just need to update the order status
                if (item.getPayFlag()) {
                    // Sleep for 1 second before each request (reduce pressure). Query up to 1000 records at a time. Theoretically, it can be completed in 16 minutes.
                    Thread.sleep(1000);
                    payOrderService.refund(new RefundReqDTO()
                            .setAppOrderNo(item.getAppOrderNo()).setRefundAmount(item.getAmount()));
                }
            } catch (InterruptedException e) {
                log.error("League Join Expire Job data processing failed (ignoring this error) [{}]", item, e);
            }
        });
        log.info("League Join Expire Job finished: {}", LocalDateTime.now());
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    /**
     * Send an expiry message to the user
     *
     * @param joinDO LeagueJoinDO
     */
    private void sendMessage(LeagueJoinDO joinDO) {
        ContentParamVo contentParamVo = new ContentParamVo()
                .setInitiatorLeagueId(joinDO.getLeagueId())
                .setInitiatorId(0L);
        // Because this join request has expired, [{Backend Developer}] has declined your application to join the league
        SendMessageReqDTO message = new SendMessageReqDTO()
                .setMessageTemplate(MessageTemplateEnum.JOIN_LEAGUE_ACTIVE_EXPIRE)
                .setMessageScene(MessageSceneEnum.JOIN_LEAGUE_ACTIVE)
                .setMessageType(MessageTypeEnum.CALLBACK2)
                .setBusinessId(joinDO.getId())
                .setReceivers(Collections.singletonList(joinDO.getUserId()))
                .setMessageParam(contentParamVo)
                .setLanguage(LanguageEnum.en_US)
                .setDealStatus(MessageDealStatusEnum.NO_DEAL);
        messageService.sendMessage(message);
    }
}
