package com.hisun.kugga.duke.league.handler.notice;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.dto.GrowthDTO;
import com.hisun.kugga.duke.entity.ContentParamVo;
import com.hisun.kugga.duke.enums.*;
import com.hisun.kugga.duke.enums.message.MessageDealStatusEnum;
import com.hisun.kugga.duke.enums.message.MessageSceneEnum;
import com.hisun.kugga.duke.enums.message.MessageTemplateEnum;
import com.hisun.kugga.duke.enums.message.MessageTypeEnum;
import com.hisun.kugga.duke.growthrule.factory.GrowthRuleFactory;
import com.hisun.kugga.duke.league.api.LeagueApi;
import com.hisun.kugga.duke.league.bo.task.TaskAcceptBO;
import com.hisun.kugga.duke.league.bo.task.TaskFinishBO;
import com.hisun.kugga.duke.league.bo.task.TaskInitBO;
import com.hisun.kugga.duke.league.bo.task.TaskLeagueBO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueMemberDO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueNoticeDO;
import com.hisun.kugga.duke.league.dal.dataobject.TaskDO;
import com.hisun.kugga.duke.league.dal.dataobject.TaskReportDO;
import com.hisun.kugga.duke.league.dal.mysql.*;
import com.hisun.kugga.duke.league.factory.LeagueNoticeFactory;
import com.hisun.kugga.duke.pay.api.order.OrderApi;
import com.hisun.kugga.duke.pay.api.order.dto.ReceiverInfo;
import com.hisun.kugga.duke.pay.api.order.dto.SplitAccountReqDTO;
import com.hisun.kugga.duke.system.api.message.SendMessageApi;
import com.hisun.kugga.duke.system.api.message.dto.MessagesUpdateReqDTO;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.system.api.params.SystemParamsApi;
import com.hisun.kugga.duke.user.api.oauth2.DukeUserApi;
import com.hisun.kugga.duke.utils.SplitAccountUtil;
import com.hisun.kugga.duke.utils.vo.SplitAccountVo;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.utils.RedissonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hisun.kugga.duke.common.RedisKeyPrefixConstants.TASK_LOCK_LEAGUE_AUTH;
import static com.hisun.kugga.duke.enums.LeagueNoticeStatusEnum.*;
import static com.hisun.kugga.duke.enums.system.SystemParamEnum.TASK_REPORT;
import static com.hisun.kugga.duke.league.constants.TaskConstants.*;
import static com.hisun.kugga.framework.common.pojo.CommonResult.success;
import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 10:37
 */
@Slf4j
@Component
@AllArgsConstructor
public class LeagueNoticeWriteReport extends AbstractLeagueNoticeHandler {

    private static final LeagueNoticeTypeEnum noticeType = LeagueNoticeTypeEnum.NOTICE_TYPE_1;
    private static final LeagueNoticeTypeEnum noticeFinishType = LeagueNoticeTypeEnum.NOTICE_TYPE_2;

    private static final LeagueNoticeStatusEnum noticeStatus = NOTICE_STATUS_1;
    private static final LeagueNoticeStatusEnum noticeFinishStatus = NOTICE_STATUS_3;

    LeagueNoticeMapper leagueNoticeMapper;
    LeagueMapper leagueMapper;
    OrderApi orderApi;
    SendMessageApi sendMessageApi;
    LeagueMemberMapper leagueMemberMapper;
    TaskMapper taskMapper;
    DukeUserApi dukeUserApi;
    TaskReportMapper reportMapper;
    RedissonUtils redissonUtils;
    SystemParamsApi systemParamsApi;
    GrowthRuleFactory growthRuleFactory;
    LeagueApi leagueApi;

    Environment environment;


    /**
     * 推荐报告
     *
     * @param bo
     * @return
     */
    @Override
    public void create(TaskInitBO bo) {
        if (ObjectUtil.isEmpty(bo.getTaskInit1().getLeagueList())) {
            return;
        }
        //默认72小时
        String expireValue = getDefaultExpireValue();
        bo.getTaskInit1().getLeagueList().forEach(item -> {
            //插入公告栏
            LeagueNoticeDO notice = insertCreateNotice(bo, item, expireValue);
            //插入推荐报告订单子表
            insertTaskRecommendation(bo, item, notice, expireValue);

            createTaskSendMessage(bo, item, notice);
        });
    }

    @Override
    public CommonResult accept(TaskAcceptBO bo) {
        //请求参数校验
        if (Objects.isNull(bo.getId()) || Objects.isNull(bo.getType()) || Objects.isNull(bo.getLeagueNoticeId())) {
            throw new ServiceException(TASK_NOT_COMPLETE);
        }

        // 加锁,锁的维度是一个任务下每个公会的公告栏
        boolean tryLock = redissonUtils.tryLock(TASK_LOCK_LEAGUE_AUTH + bo.getId() + "_" + bo.getLeagueNoticeId(), () -> {
            LeagueNoticeDO notice = leagueNoticeMapper.selectById(bo.getLeagueNoticeId());
            //公告栏订单状态校验
            preNoticeCheck(notice);

            Long userId = getLoginUserId();
            TaskDO task = taskMapper.selectById(bo.getId());
            //不能给自己写推荐报告 ,
            if (ObjectUtil.isNotNull(task) && ObjectUtil.equal(userId, task.getUserId())) {
                throw new ServiceException(NOT_ACCEPT);
            }

            String[] activeProfiles = environment.getActiveProfiles();
            if (ObjectUtil.isNotEmpty(activeProfiles) && ObjectUtil.equal(activeProfiles[0], "prd")) {
                //一个月内给这个人写推荐报告不能超过一次   生产校验
                Long countWriteReport = leagueNoticeMapper.oneMonthRepetitionWriteReport(userId, task.getUserId());
                if (countWriteReport >= 1) {
                    throw new ServiceException(ONE_MONTH_REPETITION_WRITE_REPORT);
                }
            }

            // 更新任务栏状态为 已接单
            updateNoticeDO(bo, userId);
        });
        if (!tryLock) {
            //未获取到锁
            throw new ServiceException(TASK_LEAGUE_AUTH_LOCK);
        }
        return success();
    }


    /**
     * 完成推荐报告
     * 1    更新原公告状态
     * 2    新增一条完成公告
     * 3    调用支付 上账接口 资金到账 公会|个人
     * <p>
     * 参数前置校验
     * 业务状态校验
     * 修改公告栏的状态 为已完成
     * 修改公会认证订单表状态 为已分账
     * 发消息 （可优化成异步）
     * 之前发过的所有邀请认证消息置灰 不可点击
     * 分账 一定要放在所有业务之后做分账！！！
     * todo 分账判断金额，同时消息提示金额
     *
     * @param bo 公告vo
     * @return
     */
    @Override
    public CommonResult finish(TaskFinishBO bo) {
        LeagueNoticeDO notice = leagueNoticeMapper.selectById(bo.getLeagueNoticeId());
        log.info("写推荐报告完成任务，finish notice:{}", notice);
        if (NOTICE_STATUS_3.equals(notice.getStatus())) {
            throw new ServiceException(NOT_COMMIT_REPORT_FINISH);
        }

        //公告栏任务更新 状态已完成
        leagueNoticeMapper.updateById(new LeagueNoticeDO().setId(bo.getLeagueNoticeId()).setStatus(NOTICE_STATUS_3));

        TaskReportDO reportDO = getReportDoByNoticeId(bo.getLeagueNoticeId());
        if (ObjectUtil.notEqual(notice.getAmount(), BigDecimal.ZERO)) {
            //付费的才分账  修改推荐报告订单表的订单状态 已分账
            reportMapper.updateById(new TaskReportDO().setId(reportDO.getId()).setStatus(PayStatusEnum.SPLIT_ACCOUNT));

            // 写完推荐报告后，笔者增长积分 付费的才增长积分
            GrowthDTO growthDTO = new GrowthDTO(notice.getLeagueId(), getLoginUserId());
            growthRuleFactory.getBy(GrowthType.WRITE_RECOMMENDATION).growthValue(growthDTO, leagueApi::growthThenLevel);
        }

        //插入公告栏完成通知
        insertNoticeFinish(notice);

        //发消息&消息失效置灰
        sendMessageAndDeal(bo, notice);

        if (ObjectUtil.notEqual(notice.getAmount(), BigDecimal.ZERO)) {
            splitAccount(notice, reportDO);
        }
        return success();
    }

    /**
     * 发消息&消息失效置灰
     *
     * @param bo
     * @param notice
     */
    private void sendMessageAndDeal(TaskFinishBO bo, LeagueNoticeDO notice) {
        // 李四为我写了推荐报告
        SendMessageReqDTO message = new SendMessageReqDTO()
                .setMessageTemplate(MessageTemplateEnum.RECOMMENDATION_CALLBACK)
                .setMessageScene(MessageSceneEnum.RECOMMENDATION)
                .setMessageType(MessageTypeEnum.CALLBACK)
                .setBusinessId(bo.getId())
                .setBusinessLink(null)
                .setReceivers(Collections.singletonList(notice.getUseUserId()))
                .setMessageParam(new ContentParamVo().setInitiatorId(getLoginUserId()))
                .setLanguage(LanguageEnum.en_US)
                .setDealStatus(MessageDealStatusEnum.NO_DEAL);
        sendMessageApi.sendMessage(message);

        // 任务完成后其他人消息已处理
        sendMessageApi.messageDeal(new MessagesUpdateReqDTO().setTaskId(notice.getId()).setStatus(MessageDealStatusEnum.ALREADY_DEAL));

        //金额不为0，发送到账通知  您撰写推荐报告，到账${50}
        if (ObjectUtil.notEqual(notice.getAmount(), BigDecimal.ZERO)) {
            SendMessageReqDTO message2 = new SendMessageReqDTO()
                    .setMessageTemplate(MessageTemplateEnum.REACH_ACCOUNT_RECOMMENDATION)
                    .setMessageScene(MessageSceneEnum.RECOMMENDATION)
                    .setMessageType(MessageTypeEnum.CALLBACK_RECEIVER)
                    .setBusinessId(bo.getId())
                    .setBusinessLink(null)
                    .setReceivers(Collections.singletonList(getLoginUserId()))
                    .setMessageParam(new ContentParamVo()
                            .setInitiatorId(notice.getUseUserId())
                            .setReceiverId(getLoginUserId())
                            .setReceiverLeagueId(notice.getByLeagueId())
                            .setTaskWriteReportCreate(new ContentParamVo.TaskWriteReportCreate().setAmount(notice.getAmount()))
                    )
                    .setLanguage(LanguageEnum.en_US)
                    .setDealStatus(MessageDealStatusEnum.NO_DEAL);
            sendMessageApi.sendMessage(message2);
        }
    }

    private TaskReportDO getReportDoByNoticeId(Long leagueNoticeId) {
        TaskReportDO taskReportDO = reportMapper.getReportDoByNoticeId(leagueNoticeId);
        if (ObjectUtil.isNull(taskReportDO)) {
            throw new ServiceException(ORDER_NOT_EXISTS, "taskReportDO");
        }
        return taskReportDO;
    }

    /**
     * 插入公告栏完成通知
     *
     * @param notice
     */
    private void insertNoticeFinish(LeagueNoticeDO notice) {
        LeagueNoticeDO insert = new LeagueNoticeDO()
                .setId(null)
                .setTaskId(notice.getTaskId())
                .setTaskType(notice.getTaskType())
                .setLeagueId(notice.getLeagueId())
                .setType(LeagueNoticeTypeEnum.NOTICE_TYPE_2)
                .setStatus(NOTICE_STATUS_3)

                .setUseLeagueId(notice.getLeagueId())
                .setUseUserId(getLoginUserId())

                .setByUserId(notice.getUseUserId())

                .setAmount(notice.getAmount());
        leagueNoticeMapper.insertSmart(insert);
    }

    /**
     * 分账
     *
     * @param notice
     */
    private void splitAccount(LeagueNoticeDO notice, TaskReportDO reportDO) {
        SplitAccountVo accountVo = SplitAccountUtil.splitByThree(reportDO.getAmount());
        //写推荐信 个人、公会、平台 532分账
        ReceiverInfo leagueAdmin = new ReceiverInfo().setReceiverId(notice.getByUserId()).setAccountType(AccountType.USER).setAmount(accountVo.getPersonAmount());
        ReceiverInfo league = new ReceiverInfo().setReceiverId(notice.getByLeagueId()).setAccountType(AccountType.LEAGUE).setAmount(accountVo.getLeagueAmount());
        ReceiverInfo platform = new ReceiverInfo().setReceiverId(1L).setAccountType(AccountType.PLATFORM).setAmount(accountVo.getPlatformAmount());
        SplitAccountReqDTO splitAccountReqDTO = new SplitAccountReqDTO()
                .setOrderType(OrderType.CREATE_RECOMMENDATION)
                .setAppOrderNo(reportDO.getAppOrderNo())
                .setReceiverList(Arrays.asList(leagueAdmin, league, platform));
        orderApi.splitAccount(splitAccountReqDTO);
    }

    /**
     * 更新任务栏状态
     *
     * @param bo
     * @param userId
     */
    private void updateNoticeDO(TaskAcceptBO bo, Long userId) {
        //乐观锁
        LambdaQueryWrapper<LeagueNoticeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LeagueNoticeDO::getId, bo.getLeagueNoticeId());

        String expireValue = getDefaultExpireValue();

        LeagueNoticeDO update = new LeagueNoticeDO()
                .setId(bo.getLeagueNoticeId())
                .setStatus(NOTICE_STATUS_2)
                // 此时描述的是 写推荐信用户id
                .setByUserId(userId);
        // 接单后有效期重新刷新为72小时
        //.setExpiresTime(LocalDateTime.now().plusHours(Long.parseLong(expireValue)));
        int res = leagueNoticeMapper.update(update, wrapper);
        if (res == 0) {
            throw new ServiceException(NOTICE_EXIST_USER);
        }
    }

    /**
     * 获取推荐报告默认过期时间
     *
     * @return 默认72
     */
    private String getDefaultExpireValue() {
        return systemParamsApi.getParamsByTypeAndKey(TASK_REPORT.getType(), TASK_REPORT.getParamKey());
    }


    /**
     * 任务栏的状态校验
     *
     * @param notice
     */
    private void preNoticeCheck(LeagueNoticeDO notice) {
        //非空、过期校验 推荐报告必须是初始 1未接单 状态
        if (Objects.isNull(notice)) {
            throw new ServiceException(NOTICE_IS_DELETE);
        }
        if (LocalDateTime.now().isAfter(notice.getExpiresTime())) {
            throw new ServiceException(NOT_ACCEPT_TIME_OUT_WRITE_REPORT);
        }
        if (ObjectUtil.notEqual(notice.getByUserId(), getLoginUserId()) && ObjectUtil.notEqual(NOTICE_STATUS_1, notice.getStatus())) {
            throw new ServiceException(NOTICE_EXIST_USER);
        }
    }


    private void createTaskSendMessage(TaskInitBO bo, TaskLeagueBO acceptLeagueBo, LeagueNoticeDO notice) {
        Long userId = getLoginUserId();
        //发消息
        List<Long> userIdList = leagueMemberMapper.selectList(LeagueMemberDO::getLeagueId, acceptLeagueBo.getId())
                .stream().map(LeagueMemberDO::getUserId).collect(Collectors.toList());

        //本公会邀请，那么不发给自己
        userIdList = userIdList.stream().filter(item -> ObjectUtil.notEqual(item, getLoginUserId())).collect(Collectors.toList());

        //设置消息内软 免费 付费,默认免费
        BigDecimal amount = BigDecimal.ZERO;
        MessageTemplateEnum template = MessageTemplateEnum.RECOMMENDATION_INVITE_FREE;
        if (ObjectUtil.notEqual(amount, acceptLeagueBo.getAmount())) {
            amount = SplitAccountUtil.splitByThree(acceptLeagueBo.getAmount()).getPersonAmount();
            template = MessageTemplateEnum.RECOMMENDATION_INVITE;
        }

        SendMessageReqDTO message = new SendMessageReqDTO()
                .setMessageTemplate(template)
                .setMessageScene(MessageSceneEnum.RECOMMENDATION)
                .setMessageType(MessageTypeEnum.INVITE)
                //推荐报告businessId为 公告栏id，未完成过期后根据公告栏id过期
                .setBusinessId(notice.getId())
                .setBusinessLink(null)
                .setReceivers(userIdList)
                .setMessageParam(new ContentParamVo()
                        .setInitiatorId(userId)
                        .setTaskWriteReportCreate(
                                new ContentParamVo.TaskWriteReportCreate()
                                        .setId(bo.getId())
                                        .setType(bo.getType().getValue())

                                        .setUseUserId(userId)
                                        .setLeagueNoticeId(notice.getId())
                                        .setByLeagueId(acceptLeagueBo.getId())
                                        .setAmount(amount)


                        ))
                .setLanguage(LanguageEnum.en_US)
                .setDealStatus(MessageDealStatusEnum.DEAL);
        sendMessageApi.sendMessage(message);
    }


    /**
     * 插入公告栏数据
     *
     * @param bo
     * @param item
     * @param expireValue 默认72小时
     * @return
     */
    private LeagueNoticeDO insertCreateNotice(TaskInitBO bo, TaskLeagueBO item, String expireValue) {
        Long userId = getLoginUserId();
        BigDecimal amount = SplitAccountUtil.splitByThree(item.getAmount()).getPersonAmount();
        LeagueNoticeDO noticeDO = new LeagueNoticeDO()
                .setTaskId(bo.getId())
                .setTaskType(bo.getType())

                .setLeagueId(item.getId())
                .setType(LeagueNoticeTypeEnum.NOTICE_TYPE_1)
                .setStatus(NOTICE_STATUS_1)

                // 张三邀请写推荐报告 use是张三，byLeagueId是接受消息的公会
                .setUseUserId(userId)
                .setByLeagueId(item.getId())
                .setAmount(amount)
                .setExpiresTime(LocalDateTime.now().plusHours(Long.parseLong(expireValue)));
        leagueNoticeMapper.insertSmart(noticeDO);
        return noticeDO;
    }

    /**
     * 插入推荐报告订单子表
     *
     * @param bo
     * @param o
     * @param notice
     */
    private void insertTaskRecommendation(TaskInitBO bo, TaskLeagueBO o, LeagueNoticeDO notice, String expireValue) {
        TaskPayTypeEnum payType = TaskPayTypeEnum.PAY;
        if (o.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            payType = TaskPayTypeEnum.FREE;
        }
        TaskReportDO reportDO = TaskReportDO.builder()
                .id(null)
                .taskId(bo.getId())
                .noticeId(notice.getId())
                .appOrderNo(bo.getOrderRecord())
                .amount(o.getAmount())
                .payType(payType)
                .status(PayStatusEnum.PAY)
                .expiresTime(LocalDateTime.now().plusHours(Long.parseLong(expireValue)))
                .build();
        reportMapper.insert(reportDO);
    }


    @Override
    public void afterPropertiesSet() {
        LeagueNoticeFactory.register(noticeType.getValue(), this);
    }
}
