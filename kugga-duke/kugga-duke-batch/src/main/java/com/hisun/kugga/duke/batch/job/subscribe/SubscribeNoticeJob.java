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
 * @Description: 公会订阅-通知
 * @author： Lin
 * @Date 2022/10/20 15:09
 */
@Slf4j
@Component
public class SubscribeNoticeJob implements JobHandler {

    //邮件调用地址
    @Value("${duke.league.backed.subscribeEmailNotice:}")
    private String emailUrl;
    // 跳转个人中心订阅页面 https://www.hisunpay66.com:8020/mine/subscribeView
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
     * 公会订阅的套餐，定时通知
     *
     * @param param 参数
     * @return
     * @throws Exception
     */
    @Override
    public String execute(String param) throws Exception {
        log.info(" SubscribeNoticeJob 定时处理订阅消息通知数据 start:{}", param);

        // 提前三天通知，  日期+3，status订阅状态为true  查询所有订阅中的数据
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
            // 站内信消息
            sendMessage(subscribe);

            httpInvokeSendMessage(subscribe);

        }

        log.info(" SubscribeNoticeJob 定时处理订阅消息通知数据 end:{}", LocalDateTime.now());
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    /**
     * 给用户发 过期消息
     *
     * @param subscribe
     */
    private void sendMessage(LeagueSubscribeDO subscribe) {
//        String content = messageService.getContent(MessageTemplateEnum.LEAGUE_SUBSCRIBE_RENEWAL);

//        String messageParam = JSONUtil.toJsonPrettyStr(contentParamVo);
//        //设置需要插入的数据   // 您在{后端开发工程师公会}的订阅将于 2022年11月11日 以 10$/月的价格自动续期，清保持账户余额充足  10/20/2022
//        MessagesDO insert = new MessagesDO()
//                .setMessageKey(MessageTemplateEnum.LEAGUE_SUBSCRIBE_RENEWAL.name())
//                .setScene(MessageSceneEnum.LEAGUE_SUBSCRIBE_RENEWAL.getScene())
//                .setType(MessageTypeEnum.INVITE.getCode())
//                .setInitiatorLeagueId(subscribe.getLeagueId())
//                .setReceiverId(subscribe.getUserId())
//                .setContent(content)
//                .setMessageParam(messageParam)
//                .setBusinessId(subscribe.getId())
//                .setReadFlag(MessageReadStatusEnum.UNREAD.getCode())
//                .setDealFlag(MessageDealStatusEnum.NO_DEAL.getCode());
//        messagesMapper.insert(insert);

        ContentParamVo contentParamVo = new ContentParamVo()
                .setReceiverId(subscribe.getUserId())
                .setInitiatorLeagueId(subscribe.getLeagueId())
                .setInitiatorId(0L)
                .setLeagueSubscribeVO(new ContentParamVo.LeagueSubscribeVO()
                        .setAmount(subscribe.getPrice())
                        .setExpireTime(subscribe.getExpireTime())
                );

        // 您在{后端开发工程师公会}的订阅将于{2022年11月11日}以{10}$/月的价格自动续期，清保持账户余额充足
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
     * http 发送邮件
     *
     * @return
     */
    private void httpInvokeSendMessage(LeagueSubscribeDO subscribe) {

        String uuid = innerCallHelper.genCert(subscribe.getId());

        //自2022年10月20日起，您在后台公会师公会的订阅将以10$/月的价格自动续期，
        // 为谐免扣款时账户余额不足，请您及时前往钱包充值，以免因余额不足导致取消订阅。若要了解详细信息或取漪订阅请前往个人中心-订阅查看
        ArrayList<String> replaceValues = new ArrayList<>();
        replaceValues.add(subscribe.getLeagueName());
        replaceValues.add(DateUtil.format(subscribe.getExpireTime(), dd_MM_yyyy));
        replaceValues.add(subscribe.getPrice().toString());
        // 订阅跳转url
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
            log.error("订阅续期-邮件通知异常：",exception);
        }
    }
}
