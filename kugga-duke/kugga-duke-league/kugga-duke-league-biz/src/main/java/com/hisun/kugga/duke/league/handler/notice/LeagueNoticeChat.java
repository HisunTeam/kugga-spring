package com.hisun.kugga.duke.league.handler.notice;

import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.chat.api.ChatApi;
import com.hisun.kugga.duke.entity.ContentParamVo;
import com.hisun.kugga.duke.enums.LanguageEnum;
import com.hisun.kugga.duke.enums.LeagueNoticeStatusEnum;
import com.hisun.kugga.duke.enums.LeagueNoticeTypeEnum;
import com.hisun.kugga.duke.enums.TaskPayTypeEnum;
import com.hisun.kugga.duke.enums.message.MessageDealStatusEnum;
import com.hisun.kugga.duke.enums.message.MessageSceneEnum;
import com.hisun.kugga.duke.enums.message.MessageTemplateEnum;
import com.hisun.kugga.duke.enums.message.MessageTypeEnum;
import com.hisun.kugga.duke.league.bo.task.TaskFinishBO;
import com.hisun.kugga.duke.league.bo.task.TaskInitBO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueNoticeDO;
import com.hisun.kugga.duke.league.dal.dataobject.TaskChatDO;
import com.hisun.kugga.duke.league.dal.mysql.*;
import com.hisun.kugga.duke.league.factory.LeagueNoticeFactory;
import com.hisun.kugga.duke.league.service.LeagueMemberService;
import com.hisun.kugga.duke.pay.api.order.OrderApi;
import com.hisun.kugga.duke.system.api.message.SendMessageApi;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.user.api.oauth2.DukeUserApi;
import com.hisun.kugga.duke.utils.SplitAccountUtil;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.hisun.kugga.duke.enums.LeagueNoticeTypeEnum.NOTICE_TYPE_7;
import static com.hisun.kugga.duke.league.constants.TaskConstants.NOT_EXPECT_METHOD;
import static com.hisun.kugga.duke.league.constants.TaskConstants.chat_order_update_exception;
import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 10:37
 */
@Slf4j
@Component
@AllArgsConstructor
public class LeagueNoticeChat extends AbstractLeagueNoticeHandler {

    private static final LeagueNoticeTypeEnum noticeType = LeagueNoticeTypeEnum.NOTICE_TYPE_5;

    private static final LeagueNoticeStatusEnum noticeCreateStatus = LeagueNoticeStatusEnum.NOTICE_STATUS_1;
    private static final LeagueNoticeStatusEnum noticeFinishStatus = LeagueNoticeStatusEnum.NOTICE_STATUS_3;

    LeagueNoticeMapper leagueNoticeMapper;
    LeagueMapper leagueMapper;
    TaskMapper taskMapper;
    DukeUserApi dukeUserApi;
    LeagueMemberMapper leagueMemberMapper;
    LeagueMemberService leagueMemberService;
    OrderApi orderApi;
    SendMessageApi sendMessageApi;
    ChatApi chatApi;
    TaskChatMapper taskChatMapper;

    /**
     * 创建公会公告
     *
     * @param bo
     * @return
     */
    @Override
    public void create(TaskInitBO bo) {
        //插入一条公告
        LeagueNoticeDO notice = insertCreateNotice(bo);
        //更新聊天订单表，set公告栏ID
        updateTaskChatToNotice(bo, notice.getId());
        //发起者加入的所有公会都要发一条公告
        createNoticeSendNotice(bo);
        //发送个人消息
        createNoticeSendMessage(bo, notice);
    }

    private void updateTaskChatToNotice(TaskInitBO bo, Long noticeId) {
        TaskChatDO taskChatDO = new TaskChatDO().setNoticeId(noticeId);
        LambdaQueryWrapperX<TaskChatDO> wrapperX = new LambdaQueryWrapperX<TaskChatDO>().eq(TaskChatDO::getTaskId, bo.getId());
        int update = taskChatMapper.update(taskChatDO, wrapperX);
        if (update < 1) {
            ServiceException.throwServiceException(chat_order_update_exception);
        }
    }

    private LeagueNoticeDO insertCreateNotice(TaskInitBO bo) {
        LeagueNoticeDO notice = LeagueNoticeDO.builder()
                .id(null)
                .taskId(bo.getId())
                .taskType(bo.getType())
                .leagueId(bo.getTaskInit3().getByLeagueId())
                .type(bo.getLeagueNoticeType())
                .status(noticeCreateStatus)

                .useUserId(getLoginUserId())
                .useLeagueId(null)

                .byUserId(bo.getTaskInit3().getByUserId())
                .byLeagueId(bo.getTaskInit3().getByLeagueId())

                .amount(SplitAccountUtil.splitByThree(bo.getAmount()).getPersonAmount())
                .expiresTime(LocalDateTime.now().minusDays(-7L))
                .build();
        leagueNoticeMapper.insertSmart(notice);
        return notice;
    }

    private void createNoticeSendNotice(TaskInitBO bo) {
        Long userId = getLoginUserId();
        List<Long> leagueList = leagueMemberService.getUserJoinLeague(userId);
        List insertList = new ArrayList();
        leagueList.forEach(leagueId -> {
            LeagueNoticeDO leagueNoticeDO = LeagueNoticeDO.builder()
                    .id(null)
                    .taskId(bo.getId())
                    .taskType(bo.getType())
                    .leagueId(leagueId)
                    .type(NOTICE_TYPE_7)
                    .status(noticeFinishStatus)

                    .useUserId(userId)
                    .useLeagueId(leagueId)

                    .byUserId(bo.getTaskInit3().getByUserId())
                    .byLeagueId(bo.getTaskInit3().getByLeagueId())
                    .amount(SplitAccountUtil.splitByThree(bo.getAmount()).getPersonAmount())
                    .payType(TaskPayTypeEnum.FREE)
                    .expiresTime(LocalDateTime.now().minusDays(-7L))
                    .build();
            insertList.add(leagueNoticeDO);
        });
        leagueNoticeMapper.insertBatch(insertList);
    }

    private void createNoticeSendMessage(TaskInitBO bo, LeagueNoticeDO notice) {
        Long userId = getLoginUserId();

        //设置消息内软 免费 付费,默认免费
        BigDecimal amount = BigDecimal.ZERO;
        MessageTemplateEnum template = MessageTemplateEnum.CHAT_INVITE_FREE;
        if (ObjectUtil.notEqual(amount, notice.getAmount())) {
            amount = notice.getAmount();
            template = MessageTemplateEnum.CHAT_INVITE;
        }

        SendMessageReqDTO message = SendMessageReqDTO.builder()
                .messageTemplate(template)
                .messageTemplate(MessageTemplateEnum.CHAT_INVITE)
                .messageScene(MessageSceneEnum.CHAT)
                .messageType(MessageTypeEnum.INVITE)
                .businessId(bo.getId())
                .businessLink(null)
                .receivers(Arrays.asList(notice.getByUserId()))
                .messageParam(
                        new ContentParamVo()
                                .setInitiatorId(userId)
                                .setInitiatorLeagueId(null)
                                .setReceiverId(bo.getTaskInit3().getByUserId())
                                .setReceiverLeagueId(bo.getTaskInit3().getByLeagueId())
                                .setTaskChat(
                                        new ContentParamVo.TaskChat()
                                                .setId(bo.getId())
                                                .setType(bo.getType().getValue())
                                                .setLeagueNoticeId(notice.getId())
                                                .setLeagueId(notice.getLeagueId())
                                                .setUseUserId(userId)
                                                .setAmount(amount)
                                ))
                .language(LanguageEnum.en_US)
                .dealStatus(MessageDealStatusEnum.DEAL)
                .build();
        sendMessageApi.sendMessage(message);
    }

    /**
     * 完成公会公告
     * 同意或者拒绝聊天
     *
     * @param bo
     * @return
     */
    public CommonResult finish(TaskFinishBO bo) {
        throw new ServiceException(NOT_EXPECT_METHOD);
    }

    @Override
    public void afterPropertiesSet() {
        LeagueNoticeFactory.register(noticeType.getValue(), this);
    }
}
