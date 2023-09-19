package com.hisun.kugga.duke.league.handler.task;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.hisun.kugga.duke.chat.api.ChatApi;
import com.hisun.kugga.duke.chat.api.dto.*;
import com.hisun.kugga.duke.chat.api.enums.PayChatStatusApiEnum;
import com.hisun.kugga.duke.chat.enums.PayTypeEnum;
import com.hisun.kugga.duke.dto.GrowthDTO;
import com.hisun.kugga.duke.entity.ContentParamVo;
import com.hisun.kugga.duke.enums.*;
import com.hisun.kugga.duke.enums.message.MessageDealStatusEnum;
import com.hisun.kugga.duke.enums.message.MessageSceneEnum;
import com.hisun.kugga.duke.enums.message.MessageTemplateEnum;
import com.hisun.kugga.duke.enums.message.MessageTypeEnum;
import com.hisun.kugga.duke.growthrule.factory.GrowthRuleFactory;
import com.hisun.kugga.duke.league.api.LeagueApi;
import com.hisun.kugga.duke.league.bo.task.TaskCreateBO;
import com.hisun.kugga.duke.league.bo.task.TaskFinishBO;
import com.hisun.kugga.duke.league.bo.task.TaskFinishResultBO;
import com.hisun.kugga.duke.league.bo.task.TaskInitBO;
import com.hisun.kugga.duke.league.constants.TaskConstants;
import com.hisun.kugga.duke.league.dal.dataobject.*;
import com.hisun.kugga.duke.league.dal.mysql.BusinessParamsMapper;
import com.hisun.kugga.duke.league.dal.mysql.LeagueNoticeMapper;
import com.hisun.kugga.duke.league.dal.mysql.LeagueRuleMapper;
import com.hisun.kugga.duke.league.dal.mysql.TaskChatMapper;
import com.hisun.kugga.duke.league.factory.TaskFactory;
import com.hisun.kugga.duke.league.service.LeagueNoticeService;
import com.hisun.kugga.duke.league.service.LeagueService;
import com.hisun.kugga.duke.league.utils.TaskUtils;
import com.hisun.kugga.duke.league.vo.task.TaskCreateResultVO;
import com.hisun.kugga.duke.league.vo.task.TaskInitResultVO;
import com.hisun.kugga.duke.pay.api.order.OrderApi;
import com.hisun.kugga.duke.pay.api.order.dto.*;
import com.hisun.kugga.duke.system.api.message.SendMessageApi;
import com.hisun.kugga.duke.system.api.message.dto.MessagesUpdateReqDTO;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.user.api.oauth2.DukeUserApi;
import com.hisun.kugga.duke.utils.SplitAccountUtil;
import com.hisun.kugga.duke.utils.vo.SplitAccountVo;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hisun.kugga.framework.utils.RedissonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static com.hisun.kugga.duke.common.RedisKeyPrefixConstants.TASK_LOCK_CHAT;
import static com.hisun.kugga.duke.enums.TaskStatusEnum.TASK_STATUS_1;
import static com.hisun.kugga.duke.league.constants.TaskConstants.*;
import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 10:37
 */
@Slf4j
@Component
@AllArgsConstructor
public class TaskChat extends AbstractTaskHandler {

    private static final TaskTypeEnum taskType = TaskTypeEnum.TASK_TYPE_3;
    private static final TaskStatusEnum taskFinishStatus = TaskStatusEnum.TASK_STATUS_3;

    private static final LeagueNoticeTypeEnum requestNoticeType = LeagueNoticeTypeEnum.NOTICE_TYPE_5;

    private static final LeagueNoticeTypeEnum noticeFinishType = LeagueNoticeTypeEnum.NOTICE_TYPE_6;
    private static final LeagueNoticeStatusEnum noticeFinishStatus = LeagueNoticeStatusEnum.NOTICE_STATUS_3;

    LeagueNoticeService leagueNoticeService;
    DukeUserApi dukeUserApi;
    LeagueRuleMapper leagueRuleMapper;
    OrderApi orderApi;
    SendMessageApi sendMessageApi;
    LeagueService leagueService;
    ChatApi chatApi;
    TaskUtils taskUtils;
    TaskChatMapper taskChatMapper;
    LeagueNoticeMapper leagueNoticeMapper;
    RedissonUtils redissonUtils;
    BusinessParamsMapper businessParamsMapper;
    GrowthRuleFactory growthRuleFactory;
    LeagueApi leagueApi;

    /**
     * 下单   聊天
     * 1 调用聊天模块查询能否聊天
     * 2 免费聊天 查询两个用户是否在同一个公会 在同一个公会且之前聊过天有聊天房间 就正常聊天
     * 3 判断金额是否付费聊天
     *
     * @param bo
     * @return
     */
    @Override
    public TaskInitResultVO initTask(TaskInitBO bo) {
        checkInitTaskParams(bo);
        TaskInitBO.TaskInit3 params = bo.getTaskInit3();
        Long userId = getLoginUserId();
        Long byLeagueId = params.getByLeagueId();
        Long byUserId = params.getByUserId();
        TaskInitResultVO resultVO = initResult();
        //调用聊天模块查询能否聊天  能不能聊天
        ChatCheckReqDto chatCheckDTO = new ChatCheckReqDto()
                .setUserId(userId)
                .setReceiveUserId(byUserId)
                .setIsRoomPayCheck(params.getIsRoomPayCheck());
        ChatCheckRespDto chat = chatApi.chatCheck(chatCheckDTO);
        if (chat.getIsOnChat()) {
            return resultVO;
        }
        //免费聊天 查询两个用户是否在同一个公会
        Boolean isLeagueMember = leagueService.isLeagueMember(byLeagueId, userId, byUserId);
        //判断是否在同一个公会 在同一个公会 就正常聊天
        if (isLeagueMember && chat.getIsInPrivateRoom()) {
            chatApi.expireRoomToFree(
                    new ExpireRoomToFreeReqDto()
                            .setRoomId(chat.getRoomId())
                            .setLeagueId(byLeagueId));
            resultVO.getTaskInitResultChat().setPayType(PayTypeEnum.FREE_CHAT);
            return resultVO;
        }
        //付费聊天  免费 付费
        if (BigDecimal.ZERO.compareTo(bo.getAmount()) == 0) {
            return freeInitTask(bo, resultVO);
        } else {
            return payInitTask(bo, resultVO, chat);
        }
    }

    /**
     * 支付
     * 订单维度锁单   （不能复用订单！！！）
     * 先调支付再做业务 （把支付放在前面，如果断开，支付成功后还可以重新发起业务）
     * 检查业务参数   预期聊天价格是否与公会实时聊天价格相同
     * 1 调用支付接口
     * 2 更新任务状态为已支付
     * 3 查询业务参数
     * 4 调用公告栏且发送个人消息通知
     * 5 支付完更新房间状态
     *
     * @param bo
     * @return
     */
    @Override
    public TaskCreateResultVO createTask(TaskCreateBO bo) {
        checkPayTaskParams(bo);
        this.verifyPassword(bo.getPassword(), bo.getPublicKey());
        String appOrderNo = bo.getAppOrderNo();
//        锁订单
        //不能复用订单
        boolean tryLock = redissonUtils.tryLock(TASK_LOCK_CHAT + appOrderNo, () -> {
            TaskDO taskDO = taskMapper.selectOne(TaskDO::getOrderRecord, appOrderNo);
            taskUtils.checkOrderParams(taskDO);
            //支付    把支付放在前面 如果断开 支付成功后还可以重新发起业务
            orderApi.pay(new PayReqDTO().setAppOrderNo(bo.getAppOrderNo()).setPayChannel(PayChannel.BALANCE));
            //更新任务状态为已支付
            taskMapper.updateById(TaskDO.builder().id(taskDO.getId()).status(TASK_STATUS_1).build());
            //订单状态为已支付
            updateTaskChat(taskDO.getId());
            //查询业务参数
            BusinessParamsDO businessParamsDO = businessParamsMapper.selectOne(BusinessParamsDO::getBusinessId, taskDO.getId());
            JSONArray jsonArray = JSONUtil.parseArray(businessParamsDO.getParams());
            TaskInitBO taskInitBO = jsonArray.get(0, TaskInitBO.class);
            //调用公告栏且发送个人消息通知
            taskInitBO.setLeagueNoticeType(requestNoticeType);
            leagueNoticeService.create(taskInitBO);
            //支付完更新房间状态
            //可能存在问题是两笔同时支付一个同意一个待同意，把房间已同意状态改为待同意
            ChatCheckRespDto chat = jsonArray.get(2, ChatCheckRespDto.class);
            if (!chat.getIsOnChat() && Objects.nonNull(chat.getRoomId())) {
                chatApi.payChatStatus(
                        PayChatStatusReqDto.builder()
                                .receiveLeagueId(taskInitBO.getTaskInit3().getByLeagueId())
                                .inviterUserId(taskInitBO.getTaskInit3().getByUserId())
                                .roomId(chat.getRoomId())
                                .payChatStatus(PayChatStatusApiEnum.TO_BE_CONFIRMED)
                                .build()
                );
            }
        });
        if (!tryLock) {
            //未获取到锁
            throw new ServiceException(TASK_LEAGUE_AUTH_LOCK);
        }
        //正常返回
        return new TaskCreateResultVO();
    }

    /**
     * 前置判断
     * 1    任务状态是否为未完成
     * 完成任务
     * 1    入账，钱到公会钱包
     * 2    更改公告栏状态
     * 3    创建一条新的公告栏消息
     * 4    更改任务状态
     * 5    调用个人通知模块
     * <p>
     * 过期时间 待接入
     * 查询任务下所有公告完成状态 基于所有公告反驱动更新任务状态    暂时不用
     * <p>
     * 三种公告如下：
     * 张三（99duke*）邀请与公会成员李四（99duke*）聊天 ，可为公会获得￥100的收益  去处理>
     * 公会成员李四（99duke*）已同意与张三（99duke*）聊天，公会获得￥100的收益
     * 公会成员王五（99duke*）邀请【产品经理公会】王二（99duke*）聊天
     *
     * @param bo
     * @return
     */
    @Override
    public TaskFinishResultBO finish(TaskFinishBO bo) {
        checkFinishParams(bo);
        return redissonUtils.tryLock(bo.getId().toString(), () -> {
            if (bo.getType3().getAgreeOrNo()) {
                return this.agreeChart(bo);
            } else {
                return this.notAgreeChart(bo);
            }
        });
    }

    @Override
    public void afterPropertiesSet() {
        TaskFactory.register(taskType.getValue(), this);
    }

    private void updateTaskChat(Long taskId) {
        TaskChatDO taskChatDO = new TaskChatDO().setStatus(PayStatusEnum.PAY);
        LambdaQueryWrapperX<TaskChatDO> wrapperX = new LambdaQueryWrapperX<TaskChatDO>().eq(TaskChatDO::getTaskId, taskId);
        int update = taskChatMapper.update(taskChatDO, wrapperX);
        if (update < 1) {
            ServiceException.throwServiceException(chat_order_update_exception);
        }
    }

    private TaskInitResultVO initResult() {
        TaskInitResultVO resultVO = new TaskInitResultVO();
        TaskInitResultVO.TaskInitResultChat resultChat = new TaskInitResultVO.TaskInitResultChat();
        resultVO.setTaskInitResultChat(resultChat);
        resultChat.setPayType(PayTypeEnum.PAY_CHAT);
        return resultVO;
    }

    private TaskInitResultVO freeInitTask(TaskInitBO bo, TaskInitResultVO resultVO) {
        insertTask(bo);
        businessParamsMapper.insert(bo.getId().toString(), bo);
        bo.setLeagueNoticeType(requestNoticeType);
        leagueNoticeService.create(bo);
        return resultVO;
    }

    private TaskInitResultVO payInitTask(TaskInitBO bo, TaskInitResultVO resultVO, ChatCheckRespDto chat) {
        Long userId = getLoginUserId();
        Long byLeagueId = bo.getTaskInit3().getByLeagueId();
        Long byUserId = bo.getTaskInit3().getByUserId();
        String taskChatKey = "duke_task_chat_" + userId + "_" + byUserId + "_" + byLeagueId + "_" + TASK_STATUS_1.getValue();
        //检查是否存在订单
        checkExistOrder(taskChatKey);
        OrderCreateRspDTO order = createOrder(bo);
        insertTask(bo, new TaskDO()
                .setBusinessParams(taskChatKey)
                .setOrderRecord(order.getAppOrderNo())
                .setExpiresTime(LocalDateTime.now().minusDays(-7L))
        );
        //订单表
        insertTaskChat(bo, order);
        businessParamsMapper.insert(bo.getId().toString(), bo, order, chat);
        resultVO.setAppOrderNo(order.getAppOrderNo()).setFee(order.getFee());
        return resultVO;
    }

    private void checkExistOrder(String taskChatKey) {
        LambdaQueryWrapperX<TaskDO> countWrapper = new LambdaQueryWrapperX<TaskDO>().eq(TaskDO::getBusinessParams, taskChatKey).eq(TaskDO::getStatus, TASK_STATUS_1);
        Long count = taskMapper.selectCount(countWrapper);
        if (count >= 1) {
            throw new ServiceException(WAIT_TARGET_AGREE_CHAT);
        }
    }

    private void insertTaskChat(TaskInitBO bo, OrderCreateRspDTO order) {
        TaskPayTypeEnum payType = TaskPayTypeEnum.PAY;
        PayStatusEnum payStatus = PayStatusEnum.NO_PAY;
        if (BigDecimal.ZERO.compareTo(bo.getAmount()) == 0) {
            payType = TaskPayTypeEnum.FREE;
            payStatus = PayStatusEnum.DEFAULT_STATUS;
        }
        TaskInitBO.TaskInit3 params = bo.getTaskInit3();
        Long userId = getLoginUserId();
        Long byLeagueId = params.getByLeagueId();
        Long byUserId = params.getByUserId();
        String taskChatKey = "duke_task_chat_" + userId + "_" + byUserId + "_" + byLeagueId + "_" + TASK_STATUS_1.getValue();
        TaskChatDO insert = TaskChatDO.builder()
                .id(null)
                .taskChatKey(taskChatKey)
                .taskId(bo.getId())
                .noticeId(null)
                .appOrderNo(order.getAppOrderNo())
                .amount(bo.getAmount())
                .payType(payType)
                .status(payStatus)
                .build();
        taskChatMapper.insert(insert);
    }

    private OrderCreateRspDTO createOrder(TaskInitBO bo) {
        OrderCreateReqDTO req = new OrderCreateReqDTO()
                .setOrderType(OrderType.CHAT)
                .setPayerId(getLoginUserId())
                .setAccountType(AccountType.USER)
                .setAmount(bo.getAmount());
        return orderApi.createOrder(req);
    }

    private void checkInitTaskParams(TaskInitBO bo) {
        TaskInitBO.TaskInit3 params = bo.getTaskInit3();
        //公会是否认证
        boolean leagueAuthFlag = taskUtils.leagueAuthFlag(params.getByLeagueId());
        if (!leagueAuthFlag) {
            throw new ServiceException(LEAGUE_NOT_AUTH_NO_CHAT);
        }
        //请求参数缺失
        if (Objects.isNull(params.getIsRoomPayCheck())) {
            ServiceException.throwServiceException(REQUEST_PARAMETERS_MISSING);
        }
        //公会不存在
        LeagueRuleDO ruleDO = leagueRuleMapper.selectById(params.getByLeagueId());
        if (Objects.isNull(ruleDO)) {
            throw new ServiceException(LEAGUE_NOT_EXIST);
        }
        //价格已变动
        if (ruleDO.getChatPrice().compareTo(bo.getAmount()) != 0) {
            throw new ServiceException(NOT_EXPECT_PRICE);
        }
    }

    private void checkFinishParams(TaskFinishBO bo) {
        if (bo.getId() == null) {
            ServiceException.throwServiceException(REQUIRED_PARAMETER_IS_EMPTY);
        }
        if (bo.getType() == null) {
            ServiceException.throwServiceException(REQUIRED_PARAMETER_IS_EMPTY);
        }
        if (bo.getLeagueNoticeId() == null) {
            ServiceException.throwServiceException(REQUIRED_PARAMETER_IS_EMPTY);
        }
        if (bo.getType3().getAgreeOrNo() == null) {
            ServiceException.throwServiceException(REQUIRED_PARAMETER_IS_EMPTY);
        }
    }

    private void splitAccount(TaskDO task, LeagueNoticeDO noticeDO, TaskChatDO chatDO) {
        //分账金额为订单表的金额
        SplitAccountVo splitAccountVo = SplitAccountUtil.splitByThree(chatDO.getAmount());
        ReceiverInfo user = new ReceiverInfo().setReceiverId(getLoginUserId()).setAccountType(AccountType.USER).setAmount(splitAccountVo.getPersonAmount());
        ReceiverInfo league = new ReceiverInfo().setReceiverId(noticeDO.getLeagueId()).setAccountType(AccountType.LEAGUE).setAmount(splitAccountVo.getLeagueAmount());
        ReceiverInfo platform = new ReceiverInfo().setReceiverId(1L).setAccountType(AccountType.PLATFORM).setAmount(splitAccountVo.getPlatformAmount());
        SplitAccountReqDTO splitAccountReqDTO = new SplitAccountReqDTO()
                .setOrderType(OrderType.CHAT)
                .setAppOrderNo(task.getOrderRecord())
                .setReceiverList(Arrays.asList(user, league, platform));
        log.info("分账接口传入对象：" + splitAccountReqDTO);
        orderApi.splitAccount(splitAccountReqDTO);
    }

    /**
     * 同意聊天 完成交易
     *
     * @param bo
     * @return
     */
    public TaskFinishResultBO agreeChart(TaskFinishBO bo) {
        TaskFinishResultBO resultBO = new TaskFinishResultBO();
        TaskDO task = taskMapper.selectById(bo.getId());
        checkAgreeChartParams(bo, task);
        LeagueNoticeDO notice = leagueNoticeMapper.selectById(bo.getLeagueNoticeId());
        ChatCheckRespDto chatCheck = checkChat(notice);
        if (chatCheck.getIsOnChat()) {
            return resultBO;
        }
        LocalDateTime expireTime = LocalDateTime.now().minusDays(-7L);
        //更新任务状态
        taskMapper.updateById(new TaskDO().setId(bo.getId()).setStatus(taskFinishStatus).setExpiresTime(expireTime));
        //更新公告栏状态
        leagueNoticeMapper.updateById(new LeagueNoticeDO().setId(bo.getLeagueNoticeId()).setStatus(noticeFinishStatus).setExpiresTime(expireTime));
        //聊天创建房间
        createChatRoom(notice, expireTime);
        //操作公告表 更新和插入
        LeagueNoticeDO insertNotice = insertTaskChatFinish(notice, expireTime);
        //个人消息
        agreeChartsSendMassage(bo, insertNotice);
        //消息置灰
        sendMessageApi.messageDeal(new MessagesUpdateReqDTO().setTaskId(bo.getId()).setStatus(MessageDealStatusEnum.ALREADY_DEAL));
        // 同意聊天后，接收人增长积分
        GrowthDTO growthDTO = new GrowthDTO(notice.getLeagueId(), getLoginUserId());
        growthRuleFactory.getBy(GrowthType.CHAT_OTHER).growthValue(growthDTO, leagueApi::growthThenLevel);


        //分账
        TaskChatDO chatDO = taskChatMapper.selectByNoticeId(bo.getLeagueNoticeId());
        if (ObjectUtil.isNull(chatDO)) {
            ServiceException.throwServiceException(ORDER_NOT_EXISTS);
        }
        if (task.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            splitAccount(task, notice, chatDO);
        }
        return resultBO;
    }

    private void createChatRoom(LeagueNoticeDO notice, LocalDateTime expireTime) {
        PayChatRespDto payChatRespDto = chatApi.payChat(
                new PayChatReqDto()
                        .setLeagueId(notice.getLeagueId())
                        .setUserId(notice.getUseUserId())
                        .setReceiveUserId(notice.getByUserId())
                        .setExpireTime(expireTime));
        log.info("聊天房间号为：" + payChatRespDto);
    }

    private ChatCheckRespDto checkChat(LeagueNoticeDO noticeDO) {
        ChatCheckReqDto chat = new ChatCheckReqDto()
                .setUserId(noticeDO.getUseUserId())
                .setReceiveUserId(noticeDO.getByUserId())
                .setIsRoomPayCheck(Boolean.FALSE);
        ChatCheckRespDto chatCheck = chatApi.chatCheck(chat);
        return chatCheck;
    }

    /**
     * 拒绝聊天 退款
     *
     * @param bo
     * @return
     */
    public TaskFinishResultBO notAgreeChart(TaskFinishBO bo) {
        TaskDO task = taskMapper.selectById(bo.getId());
        //聊天过期判断
        if (LocalDateTime.now().isAfter(task.getExpiresTime()) || TaskStatusEnum.TASK_STATUS_4.equals(task.getStatus())) {
            throw new ServiceException(TaskConstants.CHAT_APPLY_PAST);
        }
        //退款
//        orderApi.refundByOrderNo(leagueTaskMapper.selectById(bo.getId()).getOrderRecord());
        taskMapper.updateById(new TaskDO().setId(bo.getId()).setStatus(TaskStatusEnum.TASK_STATUS_4));
        //更新公告栏状态
        leagueNoticeMapper.updateById(new LeagueNoticeDO().setId(bo.getLeagueNoticeId()).setStatus(noticeFinishStatus));
        //消息置灰
        sendMessageApi.messageDeal(new MessagesUpdateReqDTO().setTaskId(bo.getId()).setStatus(MessageDealStatusEnum.ALREADY_DEAL));
        return new TaskFinishResultBO();
    }

    private void agreeChartsSendMassage(TaskFinishBO bo, LeagueNoticeDO insertNotice) {
        MessageTemplateEnum template = MessageTemplateEnum.CHAT_CALLBACK;
        SendMessageReqDTO message = new SendMessageReqDTO()
                .setMessageTemplate(template)
                .setMessageScene(MessageSceneEnum.CHAT)
                .setMessageType(MessageTypeEnum.CALLBACK)
                .setBusinessId(bo.getId())
                .setBusinessLink(null)
                .setReceivers(Arrays.asList(insertNotice.getByUserId()))
                .setMessageParam(new ContentParamVo()
                        .setInitiatorId(insertNotice.getUseUserId())
                        .setTaskChat(
                                new ContentParamVo.TaskChat()
                                        .setId(bo.getId())
                                        .setType(bo.getType().getValue())
                                        .setLeagueNoticeId(insertNotice.getId())
                                        .setLeagueId(insertNotice.getLeagueId())

                                        .setUseUserId(insertNotice.getUseUserId())
                                        .setAmount(insertNotice.getAmount())
                        )
                )
                .setLanguage(LanguageEnum.en_US)
                .setDealStatus(MessageDealStatusEnum.NO_DEAL);
        sendMessageApi.sendMessage(message);

        BigDecimal amount = BigDecimal.ZERO;
        MessageTemplateEnum template2 = MessageTemplateEnum.CHAT_CALLBACK_RECEIVER2;
        if (ObjectUtil.notEqual(amount, insertNotice.getAmount())) {
            amount = insertNotice.getAmount();
            template2 = MessageTemplateEnum.REACH_ACCOUNT_CHAT;
        }
        SendMessageReqDTO message2 = new SendMessageReqDTO()
                .setMessageTemplate(template2)
                .setMessageScene(MessageSceneEnum.CHAT)
                .setMessageType(MessageTypeEnum.CALLBACK_RECEIVER)
                .setBusinessId(bo.getId())
                .setBusinessLink(null)
                .setReceivers(Arrays.asList(insertNotice.getUseUserId()))
                .setMessageParam(
                        new ContentParamVo()
                                .setInitiatorId(insertNotice.getUseUserId())
                                .setInitiatorLeagueId(insertNotice.getUseLeagueId())
                                .setReceiverId(insertNotice.getByUserId())
                                .setReceiverLeagueId(insertNotice.getByLeagueId())
                                .setTaskChat(
                                        new ContentParamVo.TaskChat()
                                                .setId(bo.getId())
                                                .setType(bo.getType().getValue())
                                                .setLeagueNoticeId(insertNotice.getId())
                                                .setLeagueId(insertNotice.getLeagueId())

                                                .setUseUserId(insertNotice.getUseUserId())
                                                .setAmount(amount)
                                )
                )
                .setLanguage(LanguageEnum.en_US)
                .setDealStatus(MessageDealStatusEnum.NO_DEAL);
        sendMessageApi.sendMessage(message2);
    }

    private LeagueNoticeDO insertTaskChatFinish(LeagueNoticeDO noticeDO, LocalDateTime expireTime) {
        LeagueNoticeDO insert = LeagueNoticeDO.builder()
                .id(null)
                .taskId(noticeDO.getTaskId())
                .taskType(noticeDO.getTaskType())
                .leagueId(noticeDO.getLeagueId())
                .type(noticeFinishType)
                .status(noticeFinishStatus)
                .useUserId(noticeDO.getByUserId())
                .byUserId(noticeDO.getUseUserId())
                .amount(noticeDO.getAmount())
                .expiresTime(expireTime)
                .build();
        leagueNoticeMapper.insertSmart(insert);
        return insert;
    }

    private void checkAgreeChartParams(TaskFinishBO bo, TaskDO task) {
        Optional.ofNullable(bo.getId()).orElseThrow(() -> new ServiceException(REQUEST_PARAMETERS_MISSING));
        Optional.ofNullable(bo.getType()).orElseThrow(() -> new ServiceException(REQUEST_PARAMETERS_MISSING));
        Optional.ofNullable(bo.getLeagueNoticeId()).orElseThrow(() -> new ServiceException(REQUEST_PARAMETERS_MISSING));
        if (LocalDateTime.now().isAfter(task.getExpiresTime()) || TaskStatusEnum.TASK_STATUS_4.equals(task.getStatus())) {
            throw new ServiceException(CHAT_APPLY_PAST);
        }
        if (TaskStatusEnum.TASK_STATUS_2.equals(task.getStatus())) {
            throw new ServiceException(HAS_CONSENT_CHAT);
        }
    }
}
