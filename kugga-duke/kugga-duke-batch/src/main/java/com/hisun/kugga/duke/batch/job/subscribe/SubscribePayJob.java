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
 * @Description: 公会订阅-支付
 * @author： Lin
 * @Date 2022/10/20 15:09
 */
@Slf4j
@Component
public class SubscribePayJob implements JobHandler {

    /**
     * 余额不足错误码
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
     * 公会订阅的套餐，定时续费
     *
     * @param param 参数
     * @return
     * @throws Exception
     */
    @Override
    public String execute(String param) throws Exception {
        log.info(" SubscribePayJob 定时处理公会订阅支付数据 start:{}", param);

        // 当天跑过期时间为昨天的数据，  日期-1，status订阅状态为true,过期状态expireStatus=false的
        LocalDateTime now = LocalDateTime.now();
        String expireTime = DateUtil.format(now.minusDays(1L), yyyy_MM_dd);
        List<LeagueSubscribeDO> subscribes = subscribeMapper.selectSubscribes(expireTime, true, false);
        if (ObjectUtil.isEmpty(subscribes)) {
            return GlobalErrorCodeConstants.SUCCESS.getMsg();
        }

        for (LeagueSubscribeDO subscribe : subscribes) {
            // 根据流水记录判断是否退出当前循环
            if (!validateIsContinueOrderPay(subscribe)) {
                continue;
            }

            // 构建流水信息do
            LeagueSubscribeFlowDO flowDO = buildFlowDoInfo(subscribe);
            flowMapper.insert(flowDO);

            //如果订阅价格为0，那么只修改过期时间，不为0还需要支付
            if (ObjectUtil.notEqual(BigDecimal.ZERO, flowDO.getPrice())) {
                String uuid = innerCallHelper.genCert(flowDO.getId());
                // http 请求下单&支付
                String result = httpInvokeOrderAndPay(flowDO.getId(), uuid);

                JSONObject parseObj = JSONUtil.parseObj(result);
                int resultCode = (int) parseObj.get("code");
                //失败不更新流水表和订阅记录
                if (0 != resultCode) {
                    continue;
                }
            }

            // 查询流水状态 是 已支付或已分账的才续期
            flowDO = flowMapper.selectById(flowDO.getId());
            if (flowDO.getBusinessStatus() == PayStatusEnum.PAY.getValue() ||
                    flowDO.getBusinessStatus() == PayStatusEnum.SPLIT_ACCOUNT.getValue()) {
                // 下单成功后更新订单状态
                LocalDateTime expireDate = SubscribeUtil.getExpireTimeBySubscribeType(Objects.requireNonNull(LeagueSubscribeType.getByCode(subscribe.getSubscribeType())));

                subscribeMapper.subscriptionRenewal(subscribe.getId(), expireDate);
            }
        }

        log.info(" SubscribePayJob 定时处理公会订阅支付数据 end:{}", LocalDateTime.now());
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    /**
     * 根据流水信息判断是否继续执行
     *
     * @param subscribe
     * @return
     */
    private boolean validateIsContinueOrderPay(LeagueSubscribeDO subscribe) {
        // 查询是否存在流水记录
        String expireTime = DateUtil.format(LocalDateTime.now(), yyyy_MM_dd);
        LeagueSubscribeFlowDO existFlow = flowMapper.selectExistFlow(subscribe, expireTime);
        // 执行  无记录 || 有记录但是状态为失败&原因不等于 130003(余额不足)
        // 不执行  有记录且记录状态原因不为失败 ||  有记录且状态为失败且原因为130003
        boolean isExistFlow = ObjectUtil.isNotNull(existFlow);
        boolean isFailStatus = false;
        boolean isBalanceNoEnough = false;
        if (isExistFlow) {
            isFailStatus = existFlow.getBusinessStatus() == PayStatusEnum.FAIL.getValue();
            isBalanceNoEnough = ObjectUtil.equal(Insufficient_Balance_Error_Code, existFlow.getRemark());
        }

        /*if ((ObjectUtil.isNotNull(existFlow) && existFlow.getBusinessStatus() != PayStatusEnum.FAIL.getValue()) ||
                (ObjectUtil.isNotNull(existFlow) && existFlow.getBusinessStatus() == PayStatusEnum.FAIL.getValue() && ObjectUtil.notEqual("130003", existFlow.getRemark()))) {
            continue;
        }*/
        // 不执行
        if ((isExistFlow && !isFailStatus) || (isExistFlow && isFailStatus && isBalanceNoEnough)) {
            //如果是余额不足，那么修改订阅记录为false
            if (isExistFlow && isBalanceNoEnough) {
                // 把订阅状态设置为false
                subscribeMapper.updateSubscribeStatusToFalse(subscribe.getId());
                // 退出后消息通知
                this.sendMessage(subscribe);
            }
            return false;
        }
        return true;
    }


    private LeagueSubscribeFlowDO buildFlowDoInfo(LeagueSubscribeDO subscribe) {
        // 订阅流水表记录
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
     * http 请求下单&支付
     *
     * @return
     */
    private String httpInvokeOrderAndPay(Long flowId, String uuid) {
        //设置请求参数
        SubscriptionRenewalDTO renewalDTO = new SubscriptionRenewalDTO();
        renewalDTO.setFlowId(flowId);
        renewalDTO.setUuid(uuid);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        // headers.put("Authorization", "Bearer 22222");

        // 发送post请求
        // String str = "http://localhost:18081/app-api/league/subscribe/subscribeOrderPay";
        String result = HttpRequest.post(url).addHeaders(headers).body(JSONUtil.toJsonPrettyStr(renewalDTO)).execute().body();
        log.info("订阅下单支付结果打印{},{}", flowId, result);
        return result;
    }


    /**
     * 给用户发 退出公会消息
     *
     * @param subscribe
     */
    private void sendMessage(LeagueSubscribeDO subscribe) {
//        String content = messageService.getContent(MessageTemplateEnum.LEAGUE_SUBSCRIBE_NO_BALANCE_QUIT);
//        String messageParam = JSONUtil.toJsonPrettyStr(contentParamVo);
//        MessagesDO insert = new MessagesDO()
//                .setMessageKey(MessageTemplateEnum.LEAGUE_SUBSCRIBE_NO_BALANCE_QUIT.name())
//                .setScene(MessageSceneEnum.LEAGUE_SUBSCRIBE_NO_BALANCE_QUIT.getScene())
//                .setType(MessageTypeEnum.INVITE.getCode())
//                .setBusinessId(subscribe.getId())
//                .setInitiatorLeagueId(subscribe.getLeagueId())
//                .setReceiverId(subscribe.getUserId())
//                .setContent(content)
//                .setMessageParam(messageParam)
//                .setReadFlag(MessageReadStatusEnum.UNREAD.getCode())
//                .setDealFlag(MessageDealStatusEnum.NO_DEAL.getCode());
//        messagesMapper.insert(insert);
        ContentParamVo contentParamVo = new ContentParamVo()
                .setReceiverId(subscribe.getUserId())
                .setInitiatorId(0L)
                .setInitiatorLeagueId(subscribe.getLeagueId());

        //由于帐户余额不足，您对｛xxx｝公会的订阅已暂停。您将无法访问｛xxx｝公会及其福利。
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
