package com.hisun.kugga.duke.league.handler.notice;

import com.hisun.kugga.duke.dto.GrowthDTO;
import com.hisun.kugga.duke.entity.ContentParamVo;
import com.hisun.kugga.duke.enums.*;
import com.hisun.kugga.duke.enums.message.MessageDealStatusEnum;
import com.hisun.kugga.duke.enums.message.MessageSceneEnum;
import com.hisun.kugga.duke.enums.message.MessageTemplateEnum;
import com.hisun.kugga.duke.enums.message.MessageTypeEnum;
import com.hisun.kugga.duke.growthrule.factory.GrowthRuleFactory;
import com.hisun.kugga.duke.league.api.LeagueApi;
import com.hisun.kugga.duke.league.bo.task.TaskFinishBO;
import com.hisun.kugga.duke.league.bo.task.TaskInitBO;
import com.hisun.kugga.duke.league.bo.task.TaskLeagueBO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueMemberDO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueNoticeDO;
import com.hisun.kugga.duke.league.dal.dataobject.TaskLeagueAuthDO;
import com.hisun.kugga.duke.league.dal.mysql.*;
import com.hisun.kugga.duke.league.factory.LeagueNoticeFactory;
import com.hisun.kugga.duke.pay.api.order.OrderApi;
import com.hisun.kugga.duke.system.api.message.SendMessageApi;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.user.api.oauth2.DukeUserApi;
import com.hisun.kugga.duke.utils.SplitAccountUtil;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 10:37
 */
@Slf4j
@Component
@AllArgsConstructor
public class LeagueNoticeLeagueAuth extends AbstractLeagueNoticeHandler {

    private static final LeagueNoticeStatusEnum noticeFinishStatus = LeagueNoticeStatusEnum.NOTICE_STATUS_3;

    LeagueNoticeMapper leagueNoticeMapper;
    LeagueMapper leagueMapper;
    DukeUserApi dukeUserApi;
    SendMessageApi sendMessageApi;
    LeagueMemberMapper leagueMemberMapper;
    OrderApi orderApi;
    TaskMapper taskMapper;
    LeagueRuleMapper leagueRuleMapper;
    TaskLeagueAuthMapper taskLeagueAuthMapper;
    GrowthRuleFactory growthRuleFactory;
    LeagueApi leagueApi;

    /**
     * 创建公会公告且发送消息
     * 去重公会ID
     *
     * @param bo
     * @return
     */
    @Override
    public void create(TaskInitBO bo) {
        bo.getTaskInit2().getLeagueList().forEach(o -> {
            //插入公告栏
            LeagueNoticeDO notice = insertCreateNotice(bo, o);
            if (o.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                //插入公会认证订单表
                insertTaskLeagueAuth(bo, o, notice);
            }
            createTaskSendMessage(bo, o, notice);
        });
    }

    /**
     * 完成公会公告
     * 1    上账
     * 2    更新任务状态
     * 3    更新公告状态
     * 4    插入新的公告
     * 5    调用消息模块
     *
     * @param bo
     * @return
     */
    @Override
    public CommonResult finish(TaskFinishBO bo) {
        LeagueNoticeDO notice = leagueNoticeMapper.selectById(bo.getLeagueNoticeId());
        Long userId = getLoginUserId();
        Long useLeagueId = notice.getByLeagueId();
        Long byLeagueId = notice.getUseLeagueId();
        Long byUserId = notice.getUseUserId();
        //布鲁斯（99duke*）为【产品经理公会】做公会认证，公会获得￥100的收益
        LeagueNoticeDO insert = LeagueNoticeDO.builder()
                .id(null)
                .taskId(bo.getId())
                .taskType(bo.getType())
                .leagueId(notice.getLeagueId())
                .type(LeagueNoticeTypeEnum.NOTICE_TYPE_4)
                .status(noticeFinishStatus)

                .useUserId(userId)
                .useLeagueId(useLeagueId)
                .byUserId(null)
                .byLeagueId(byLeagueId)
                .amount(notice.getAmount())
                .expiresTime(LocalDateTime.now().minusYears(-100L))
                .build();
        leagueNoticeMapper.insertSmart(insert);

        // 完成公会认证后，被邀请人加积分、邀请人加积分
        GrowthDTO growthDTO = new GrowthDTO(useLeagueId, userId);
        growthRuleFactory.getBy(GrowthType.ENDORSE_OTHER).growthValue(growthDTO, leagueApi::growthThenLevel);
        GrowthDTO growthDTO2 = new GrowthDTO(byLeagueId, byUserId);
        growthRuleFactory.getBy(GrowthType.ENDORSEMENT).growthValue(growthDTO2, leagueApi::growthThenLevel);

        return null;
    }

    private LeagueNoticeDO insertCreateNotice(TaskInitBO bo, TaskLeagueBO o) {
        Long userId = getLoginUserId();
        Long byAuthLeagueId = bo.getTaskInit2().getByAuthLeagueId();
        BigDecimal amount = SplitAccountUtil.splitByTwo(o.getAmount()).getLeagueAmount();
        LeagueNoticeDO notice = LeagueNoticeDO.builder()
                .id(null)
                .taskId(bo.getId())
                .taskType(bo.getType())

                .leagueId(o.getId())
                .type(LeagueNoticeTypeEnum.NOTICE_TYPE_3)
                .status(LeagueNoticeStatusEnum.NOTICE_STATUS_1)

                .useUserId(userId)
                .useLeagueId(byAuthLeagueId)
                .byLeagueId(o.getId())
                .byUserId(null)

                .amount(amount)
                .expiresTime(LocalDateTime.now().minusHours(-72L))
                .build();
        leagueNoticeMapper.insertSmart(notice);
        return notice;
    }

    private void insertTaskLeagueAuth(TaskInitBO bo, TaskLeagueBO o, LeagueNoticeDO notice) {
        TaskPayTypeEnum payType = TaskPayTypeEnum.PAY;
        if (o.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            payType = TaskPayTypeEnum.FREE;
        }
        TaskLeagueAuthDO insert = TaskLeagueAuthDO.builder()
                .id(null)
                .byAuthLeagueId(bo.getTaskInit2().getByAuthLeagueId())
                .taskId(bo.getId())
                .noticeId(notice.getId())
                .appOrderNo(bo.getOrderRecord())
                .amount(o.getAmount())
                .payType(payType)
                .status(PayStatusEnum.PAY).build();
        taskLeagueAuthMapper.insert(insert);
    }

    private void createTaskSendMessage(TaskInitBO bo, TaskLeagueBO o, LeagueNoticeDO notice) {
        Long userId = getLoginUserId();
        Long byAuthLeagueId = bo.getTaskInit2().getByAuthLeagueId();
        //发消息
        List<Long> userIdList = leagueMemberMapper.selectList(LeagueMemberDO::getLeagueId, o.getId())
                .stream().map(LeagueMemberDO::getUserId).collect(Collectors.toList());

        //设置消息内软 免费 付费,默认免费
        BigDecimal amount = SplitAccountUtil.splitByTwo(o.getAmount()).getLeagueAmount();
        MessageTemplateEnum template = MessageTemplateEnum.LEAGUE_AUTH_INVITE;

        SendMessageReqDTO message = new SendMessageReqDTO()
                .setMessageTemplate(template)
                .setMessageScene(MessageSceneEnum.LEAGUE_AUTHENTICATION)
                .setMessageType(MessageTypeEnum.INVITE)
                .setBusinessId(bo.getTaskInit2().getByAuthLeagueId())
                .setBusinessLink(null)
                .setReceivers(userIdList)
                .setMessageParam(new ContentParamVo()
                        .setInitiatorId(userId)
                        .setInitiatorLeagueId(byAuthLeagueId)
                        .setTaskLeagueAuthCreate(
                                ContentParamVo.TaskLeagueAuthCreate.builder()
                                        .id(bo.getId())
                                        .type(bo.getType().getValue())

                                        .useUserId(userId)
                                        .byAuthLeagueId(byAuthLeagueId)

                                        .leagueNoticeId(notice.getId())
                                        .amount(amount)
                                        .build()
                        ))
                .setLanguage(LanguageEnum.en_US)
                .setDealStatus(MessageDealStatusEnum.DEAL);
        sendMessageApi.sendMessage(message);
    }


    @Override
    public void afterPropertiesSet() {
        LeagueNoticeFactory.register(LeagueNoticeTypeEnum.NOTICE_TYPE_3.getValue(), this);
    }
}
