package com.hisun.kugga.duke.league.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.entity.ContentParamVo;
import com.hisun.kugga.duke.enums.LanguageEnum;
import com.hisun.kugga.duke.enums.message.MessageDealStatusEnum;
import com.hisun.kugga.duke.enums.message.MessageSceneEnum;
import com.hisun.kugga.duke.enums.message.MessageTemplateEnum;
import com.hisun.kugga.duke.enums.message.MessageTypeEnum;
import com.hisun.kugga.duke.league.bo.InviteJoinLeagueNoticeBO;
import com.hisun.kugga.duke.league.dal.dataobject.views.LeagueViewsDO;
import com.hisun.kugga.duke.league.service.LeagueAsyncService;
import com.hisun.kugga.duke.league.service.LeagueService;
import com.hisun.kugga.duke.system.api.message.SendMessageApi;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.user.api.oauth2.DukeUserApi;
import com.hisun.kugga.duke.user.api.oauth2.dto.UserInfoRespDTO;
import com.hisun.kugga.duke.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;

import static com.hisun.kugga.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

/**
 * @author zuocheng
 */
@Service
@Slf4j
public class LeagueAsyncServiceImpl implements LeagueAsyncService {

    /**
     * 邀请加入公会通知锁名
     */
    private final static String LEAGUE_INVITE_NOTICE_LOCK = "league_invite_notice_lock";

    /**
     * 邀请加入公会通知上锁时间 24小时
     */
    private final static long LEAGUE_INVITE_NOTICE_LOCK_TIME_OUT = 24 * 60 * 60;

    @Resource
    private SendMessageApi sendMessageApi;

    @Resource
    private DukeUserApi dukeUserApi;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private LeagueService leagueService;

    @Override
    public void firstJoinLeagueNotice(Long joinUserId, LeagueViewsDO leagueViewsDO) {

        UserInfoRespDTO user = dukeUserApi.getUserById(getLoginUserId());
        String userName = user.getFirstName() + user.getLastName();

        //发送公会创建成功通知给公会创建人
        //设置此通知模板需要添加的值
        ArrayList<String> templateParams = new ArrayList<>();
        templateParams.add(userName);
        templateParams.add(leagueViewsDO.getLeagueName());

        ArrayList<Long> receivers = new ArrayList<>();
        //设置收信人
        receivers.add(leagueViewsDO.getUserId());

        /*
        张三接受了我创建公会的邀请，公会已正式成立，快去完成公会认证吧
        改为
        张三接受了我创建公会的邀请，公会已正式成立，快去完成公会认证吧
         */
        SendMessageReqDTO sendMessageReqDTO = new SendMessageReqDTO()
                .setMessageTemplate(MessageTemplateEnum.LEAGUE_CREATE_AUTH_INVITE)
                .setMessageScene(MessageSceneEnum.LEAGUE_CREATE_AUTH)
                .setMessageType(MessageTypeEnum.INVITE)
                .setLanguage(LanguageEnum.en_US)
                .setDealStatus(MessageDealStatusEnum.DEAL)
                .setReceivers(receivers)
                .setMessageParam(
                        new ContentParamVo()
                                .setInitiatorId(joinUserId)
                                .setJoinLeagueMessageVO(
                                        new ContentParamVo.JoinLeagueMessageVO()
                                                .setLeagueId(leagueViewsDO.getId())
                                                .setJoinUserId(joinUserId)
                                )
                );

        sendMessageApi.sendMessage(sendMessageReqDTO);
    }


    @Override
    public void firstJoinLeagueLedgerNotice(Long joinUserId, LeagueViewsDO leagueViewsDO) {
        //设置此通知模板需要添加的值
        ArrayList<String> templateParams2 = new ArrayList<>();
        templateParams2.add(leagueViewsDO.getLeagueName());
        templateParams2.add(leagueViewsDO.getDeductAmount().stripTrailingZeros().toPlainString());

        ArrayList<Long> receivers2 = new ArrayList<>();
        //设置收信人
        receivers2.add(joinUserId);

        MessageTemplateEnum template = MessageTemplateEnum.LEAGUE_FIRST_JOIN_INVITE;
        if (ObjectUtil.equal(BigDecimal.ZERO, leagueViewsDO.getDeductAmount())) {
            template = MessageTemplateEnum.LEAGUE_FIRST_JOIN_INVITE_FREE;
        }

        // 第一次加入公会,您是第一个受邀加入公会[{后端开发工程师}]的会员，并获得{10}美元的奖金。
        SendMessageReqDTO sendMessageReqDTO2 = new SendMessageReqDTO()
                .setMessageTemplate(template)
                .setMessageScene(MessageSceneEnum.JOIN_LEAGUE_FIRST)
                .setMessageType(MessageTypeEnum.INVITE)
                .setLanguage(LanguageEnum.en_US)
                .setDealStatus(MessageDealStatusEnum.NO_DEAL)
                .setReceivers(receivers2)
                .setMessageParam(
                        new ContentParamVo()
                                .setInitiatorId(joinUserId)
                                .setInitiatorLeagueId(leagueViewsDO.getId())
                                .setJoinLeagueMessageVO(
                                        new ContentParamVo.JoinLeagueMessageVO()
                                                .setLeagueId(leagueViewsDO.getId())
                                                .setJoinUserId(joinUserId)
                                                .setAmount(leagueViewsDO.getDeductAmount().stripTrailingZeros())
                                )
                );

        sendMessageApi.sendMessage(sendMessageReqDTO2);
    }

    @Override
    public void inviteJoinLeagueNotice(InviteJoinLeagueNoticeBO reqBO) {
        //锁名前缀+邀请人+邀请公会+被邀请人一定时间内只能邀请一次
        String lockTimeKey = redisUtils.buildRedisKey(LEAGUE_INVITE_NOTICE_LOCK, reqBO.getInviteUserId().toString(), reqBO.getLeagueId().toString(), reqBO.getUserId().toString());
        //检查锁如果存在的话，跳过处理下一条
        if (redisUtils.exist(lockTimeKey)) {
            log.info("用户[{}]今日已向用户[{}]发送过加入[{}-{}]公会的邀请", reqBO.getInviteUserId(), reqBO.getUserId(), reqBO.getLeagueId(), reqBO.getLeagueName());
            return;
        }

        ArrayList<Long> receivers = new ArrayList<>();
        //设置收信人
        receivers.add(reqBO.getUserId());

        SendMessageReqDTO message = new SendMessageReqDTO()
                .setMessageTemplate(MessageTemplateEnum.LEAGUE_JOIN_INVITE)
                .setMessageScene(MessageSceneEnum.LEAGUE_INVITE)
                .setMessageType(MessageTypeEnum.INVITE2)
                .setLanguage(LanguageEnum.en_US)
                .setDealStatus(MessageDealStatusEnum.DEAL)
                .setReceivers(receivers)
                .setMessageParam(
                        new ContentParamVo()
                                .setInitiatorId(reqBO.getInviteUserId())
                                .setInitiatorLeagueId(reqBO.getLeagueId())
                                .setInviteJoinLeagueVO(
                                        new ContentParamVo.InviteJoinLeagueVO()
                                                .setLeagueId(reqBO.getLeagueId())
                                                .setLeagueName(reqBO.getLeagueName())
                                                .setInviteUrl(reqBO.getInviteUrl())
                                )
                );
        try {
            sendMessageApi.sendMessage(message);
        } catch (Exception e) {
            log.error("发送邀请通知失败(业务忽略报错,日志打印方便核查问题)", e);
            return;
        }

        //发送消息成功后添加锁
        redisUtils.setForTimeSec(lockTimeKey, 0, LEAGUE_INVITE_NOTICE_LOCK_TIME_OUT);
    }

}
