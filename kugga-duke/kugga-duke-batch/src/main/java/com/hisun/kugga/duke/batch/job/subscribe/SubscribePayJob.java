package com.hisun.kugga.duke.batch.job.subscribe;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hisun.kugga.duke.batch.dal.dataobject.leaguesubscribe.LeagueSubscribeDO;
import com.hisun.kugga.duke.batch.dal.dataobject.leaguesubscribe.LeagueSubscribeFlowDO;
import com.hisun.kugga.duke.batch.dal.dataobject.message.MessagesDO;
import com.hisun.kugga.duke.batch.dal.mysql.leaguesubscribe.LeagueSubscribeFlowMapper;
import com.hisun.kugga.duke.batch.dal.mysql.leaguesubscribe.LeagueSubscribeMapper;
import com.hisun.kugga.duke.batch.dal.mysql.message.MessagesMapper;
import com.hisun.kugga.duke.batch.service.MessageService;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.dto.SubscriptionRenewalDTO;
import com.hisun.kugga.duke.entity.ContentParamVo;
import com.hisun.kugga.duke.enums.LanguageEnum;
import com.hisun.kugga.duke.enums.PayStatusEnum;
import com.hisun.kugga.duke.enums.message.*;
import com.hisun.kugga.duke.innercall.InnerCallHelper;
import com.hisun.kugga.duke.league.LeagueSubscribeType;
import com.hisun.kugga.duke.utils.SubscribeUtil;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.quartz.core.handler.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.hisun.kugga.duke.common.IdentifierConstants.yyyy_MM_dd;

/**
 * @Description: League Subscription Payment Job
 * This job is responsible for handling payments for league subscriptions.
 * Author: Lin
 * Date: 2022/10/20 15:09
 */
@Slf4j
@Component
public class SubscribePayJob implements JobHandler {

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
     * Handle scheduled payments for league subscriptions.
     *
     * @param param Parameters
     * @return
     * @throws Exception
     */
    @Override
    public String execute(String param) throws Exception {
        log.info("SubscribePayJob: Processing scheduled league subscription payment data start:{}", param);

        // Process data with expiration time from yesterday, status as true, and expireStatus as false
        LocalDateTime now = LocalDateTime.now();
        String expireTime = DateUtil.format(now.minusDays(1L), yyyy_MM_dd);
        List<LeagueSubscribeDO> subscribes = subscribeMapper.selectSubscribes(expireTime, true, false);
        if (ObjectUtil.isEmpty(subscribes)) {
            return GlobalErrorCodeConstants.SUCCESS.getMsg();
        }

        for (LeagueSubscribeDO subscribe : subscribes) {
            // Validate if payment should continue based on flow records
            if (!validateIsContinueOrderPay(subscribe)) {
                continue;
            }

            // Build flow information
            LeagueSubscribeFlowDO flowDO = buildFlowDoInfo(subscribe);
            flowMapper.insert(flowDO);

            // If the subscription price is not zero, it needs to be paid
            if (ObjectUtil.notEqual(BigDecimal.ZERO, flowDO.getPrice())) {
                String uuid = innerCallHelper.genCert(flowDO.getId());
                // HTTP request for order and payment
                String result = httpInvokeOrderAndPay(flowDO.getId(), uuid);

                JSONObject parseObj = JSONUtil.parseObj(result);
                int resultCode = (int) parseObj.get("code");
                // If the payment fails, skip the record
                if (0 != resultCode) {
                    continue;
                }
            }

            // Query the flow status, only renew if the status is paid or split account
            flowDO = flowMapper.selectById(flowDO.getId());
            if (flowDO.getBusinessStatus() == PayStatusEnum.PAY.getValue() ||
                    flowDO.getBusinessStatus() == PayStatusEnum.SPLIT_ACCOUNT.getValue()) {
                // If the order is successful, update the subscription status
                LocalDateTime expireDate = SubscribeUtil.getExpireTimeBySubscribeType(Objects.requireNonNull(LeagueSubscribeType.getByCode(subscribe.getSubscribeType())));
                subscribeMapper.subscriptionRenewal(subscribe.getId(), expireDate);
            }
        }

        log.info("SubscribePayJob: Processing scheduled league subscription payment data end:{}", LocalDateTime.now());
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    /**
     * Validate whether to continue with the payment based on the flow information.
     *
     * @param subscribe LeagueSubscribeDO
     * @return True if payment should continue, false otherwise.
     */
    private boolean validateIsContinueOrderPay(LeagueSubscribeDO subscribe) {
        // Check if there is a flow record
        String expireTime = DateUtil.format(LocalDateTime.now(), yyyy_MM_dd);
        LeagueSubscribeFlowDO existFlow = flowMapper.selectExistFlow(subscribe, expireTime);

        boolean isExistFlow = ObjectUtil.isNotNull(existFlow);
        boolean isFailStatus = false;
        boolean isBalanceNoEnough = false;
        if (isExistFlow) {
            isFailStatus = existFlow.getBusinessStatus() == PayStatusEnum.FAIL.getValue();
            isBalanceNoEnough = ObjectUtil.equal(Insufficient_Balance_Error_Code, existFlow.getRemark());
        }

        // If there is no flow record, or there is a record but the status is not failed or the reason is not 130003 (insufficient balance)
        // Or, if there is a record and the status is failed, but the reason is not 130003, continue the payment
        // Otherwise, skip the payment
        if ((isExistFlow && !isFailStatus) || (isExistFlow && isFailStatus && isBalanceNoEnough)) {
            // If the reason for failure is insufficient balance, set the subscription status to false
            if (isExistFlow && isBalanceNoEnough) {
                // Set the subscription status to false
                subscribeMapper.updateSubscribeStatusToFalse(subscribe.getId());
                // Send a notification message
                this.sendMessage(subscribe);
            }
            return false;
        }
        return true;
    }

    /**
     * Build LeagueSubscribeFlowDO information.
     *
     * @param subscribe LeagueSubscribeDO
     * @return LeagueSubscribeFlowDO
     */
    private LeagueSubscribeFlowDO buildFlowDoInfo(LeagueSubscribeDO subscribe) {
        LeagueSubscribeFlowDO flowDo = new LeagueSubscribeFlowDO();
        flowDo.setSubscribeId(subscribe.getId());
        flowDo.setUserId(subscribe.getUserId());
        flowDo.setLeagueId(subscribe.getLeagueId());
        flowDo.setSubscribeType(subscribe.getSubscribeType());
        flowDo.setPrice(subscribe.getPrice());
        flowDo.setSubscribeTime(LocalDateTime.now());
        flowDo.setBusinessStatus(PayStatusEnum.DEFAULT_STATUS.getValue());
        if (ObjectUtil.equal(subscribe.getPrice(), BigDecimal.ZERO)) {
            flowDo.setBusinessStatus(PayStatusEnum.PAY.getValue());
        }
        return flowDo;
    }

    /**
     * Send an HTTP request for order and payment.
     *
     * @param flowId Flow ID
     * @param uuid   UUID
     * @return Result of the order and payment
     */
    private String httpInvokeOrderAndPay(Long flowId, String uuid) {
        // Set request parameters
        SubscriptionRenewalDTO renewalDTO = new SubscriptionRenewalDTO();
        renewalDTO.setFlowId(flowId);
        renewalDTO.setUuid(uuid);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        // headers.put("Authorization", "Bearer 22222");

        // Send a POST request
        String result = HttpRequest.post(url).addHeaders(headers).body(JSONUtil.toJsonPrettyStr(renewalDTO)).execute().body();
        log.info("Subscription order and payment result for {}: {}", flowId, result);
        return result;
    }

    /**
     * Send a message to the user about quitting the league due to insufficient balance.
     *
     * @param subscribe LeagueSubscribeDO
     */
    private void sendMessage(LeagueSubscribeDO subscribe) {
        ContentParamVo contentParamVo = new ContentParamVo()
                .setReceiverId(subscribe.getUserId())
                .setInitiatorId(0L)
                .setInitiatorLeagueId(subscribe.getLeagueId());

        SendMessageReqDTO message = new SendMessageReqDTO()
                .setMessageTemplate(MessageTemplateEnum.LEAGUE_SUBSCRIBE_NO_BALANCE_QUIT)
                .setMessageScene(MessageSceneEnum.LEAGUE_SUBSCRIBE_NO_BALANCE_QUIT)
                .setMessageType(MessageTypeEnum.INVITE)
                .setBusinessId(subscribe.getId())
                .setReceivers(Collections.singletonList(subscribe.getUserId()))
                .setMessageParam(contentParamVo)
                .setLanguage(LanguageEnum.en_US)
                .setDealStatus(MessageDealStatusEnum.NO_DEAL);
        messageService.sendMessage(message);
    }

}
