package com.hisun.kugga.duke.league.handler.task;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.entity.ContentParamVo;
import com.hisun.kugga.duke.enums.*;
import com.hisun.kugga.duke.enums.message.MessageDealStatusEnum;
import com.hisun.kugga.duke.enums.message.MessageSceneEnum;
import com.hisun.kugga.duke.enums.message.MessageTemplateEnum;
import com.hisun.kugga.duke.enums.message.MessageTypeEnum;
import com.hisun.kugga.duke.league.bo.task.*;
import com.hisun.kugga.duke.league.dal.dataobject.*;
import com.hisun.kugga.duke.league.dal.mysql.*;
import com.hisun.kugga.duke.league.factory.TaskFactory;
import com.hisun.kugga.duke.league.service.LeagueMemberService;
import com.hisun.kugga.duke.league.service.LeagueNoticeService;
import com.hisun.kugga.duke.league.utils.TaskUtils;
import com.hisun.kugga.duke.league.utils.TransactionalUtils;
import com.hisun.kugga.duke.league.vo.task.TaskCreateResultVO;
import com.hisun.kugga.duke.league.vo.task.TaskInitResultVO;
import com.hisun.kugga.duke.pay.api.leagueaccount.LeagueAccountApi;
import com.hisun.kugga.duke.pay.api.order.OrderApi;
import com.hisun.kugga.duke.pay.api.order.dto.*;
import com.hisun.kugga.duke.pay.api.useraccount.UserAccountApi;
import com.hisun.kugga.duke.pay.api.wallet.WalletApi;
import com.hisun.kugga.duke.pay.api.wallet.dto.CreateAccountReqBody;
import com.hisun.kugga.duke.pay.api.wallet.dto.CreateAccountRspBody;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.hisun.kugga.duke.common.RedisKeyPrefixConstants.TASK_LOCK_LEAGUE_AUTH;
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
public class TaskLeagueAuth extends AbstractTaskHandler {

    private static final LeagueNoticeTypeEnum requestNoticeType = LeagueNoticeTypeEnum.NOTICE_TYPE_3;
    private static final String messageBusinessPreFix = "task_leagueAuth_";

    LeagueNoticeService leagueNoticeService;
    DukeUserApi dukeUserApi;
    OrderApi orderApi;
    TaskUtils taskUtils;
    LeagueMapper leagueMapper;
    BusinessParamsMapper businessParamsMapper;
    TransactionalUtils transactionalUtils;
    TaskLeagueAuthMapper taskLeagueAuthMapper;
    RedissonUtils redissonUtils;
    LeagueNoticeMapper leagueNoticeMapper;
    SendMessageApi sendMessageApi;
    LeagueMemberMapper leagueMemberMapper;
    LeagueMemberService leagueMemberService;
    UserAccountApi userAccountApi;
    WalletApi walletApi;
    LeagueAccountApi leagueAccountApi;

    /**
     * 1 校验各种参数（待抽象）
     * 2 根据金额是否为零 类型分为 免费 付费
     * 免费   下单并支付
     * 付费   下单等待前端调用支付接口】
     * <p>
     * 初始化任务（下单）
     * 免费   返回空对象
     * 1 初始化任务
     * 2 保存业务参数
     * 3 调用公告栏 发公告且发送个人消息通知
     * <p>
     * 付费   返回前端订单号，手续费等字段
     * 1 下单
     * 2 初始化任务
     * 3 保存业务参数
     *
     * @param bo
     * @return
     */
    @Override
    public TaskInitResultVO initTask(TaskInitBO bo) {
        //校验参数
        checkBusinessParams(bo);
        if (BigDecimal.ZERO.compareTo(bo.getAmount()) == 0) {
            //免费
            return freeInitTask(bo);
        } else {
            //付费
            return payInitTask(bo);
        }
    }

    /**
     * 支付
     * 1 更改任务状态
     * 2 插入多条公告
     * 3 插入多条订单记录
     * 4 发消息
     *
     * @param bo
     * @return
     */
    @Override
    public TaskCreateResultVO createTask(TaskCreateBO bo) {
        //校验支付参数
        checkPayTaskParams(bo);
        //校验支付密码
        verifyPassword(bo.getPassword(), bo.getPublicKey());
        //锁订单
        boolean tryLock = redissonUtils.tryLock(TASK_LOCK_LEAGUE_AUTH + bo.getAppOrderNo(), () -> {
            TaskDO taskDO = taskMapper.selectOne(TaskDO::getOrderRecord, bo.getAppOrderNo());
            taskUtils.checkOrderParams(taskDO);
            //支付
            orderApi.pay(PayReqDTO.builder().appOrderNo(bo.getAppOrderNo()).payChannel(PayChannel.BALANCE).build());
            //更新任务状态为已支付
            taskMapper.updateById(new TaskDO().setId(taskDO.getId()).setStatus(TaskStatusEnum.TASK_STATUS_1));
            //查询业务参数
            BusinessParamsDO businessParamsDO = businessParamsMapper.selectOne(BusinessParamsDO::getBusinessId, taskDO.getId());
            JSONArray jsonArray = JSONUtil.parseArray(businessParamsDO.getParams());
            TaskInitBO taskInitBO = jsonArray.get(0, TaskInitBO.class);
            //调用公告栏且发送个人消息通知
            taskInitBO.setLeagueNoticeType(requestNoticeType);
            leagueNoticeService.create(taskInitBO);
        });
        if (!tryLock) {
            //未获取到锁
            throw new ServiceException(TASK_LEAGUE_AUTH_LOCK);
        }
        //正常返回
        return new TaskCreateResultVO();
    }

    /**
     * 完成公会认证
     * 1 检查所有参数
     * 2 加分布式锁  维度为被认证公会ID
     * 3 修改公会认证状态 为已认证
     * 4 修改公告栏的状态 为已完成
     * 5 修改公会认证订单表状态 为已分账
     * 6 发一条公告 （公会认证已完成）
     * 7 发消息 （可优化成异步）
     * 8 之前发过的所有邀请认证消息置灰 不可点击
     * 9 分账 一定要放在所有业务之后做分账！！！
     */
    @Override
    public TaskFinishResultBO finish(TaskFinishBO bo) {
        checkFinishTaskParams(bo);
        TaskDO taskDO = taskMapper.selectById(bo.getId());
        Long byAuthLeagueId = Long.parseLong(taskDO.getBusinessParams());
        //锁被认证公会
        boolean tryLock = redissonUtils.tryLock(TASK_LOCK_LEAGUE_AUTH + byAuthLeagueId, () -> {
            LeagueDO byAuthLeague = leagueMapper.selectById(byAuthLeagueId);
            if (byAuthLeague.getAuthFlag()) {
                throw new ServiceException(LEAGUE_HAS_AUTH);
            }
            //任务表状态更新为待退款
            LambdaQueryWrapperX<TaskDO> wrapperX = new LambdaQueryWrapperX<TaskDO>().eq(TaskDO::getType, TaskTypeEnum.TASK_TYPE_2).eq(TaskDO::getBusinessParams, byAuthLeagueId);
            taskMapper.update(new TaskDO().setStatus(TaskStatusEnum.TASK_STATUS_4), wrapperX);
            //公会认证订单表状态更新为待退款
            LambdaQueryWrapperX<TaskLeagueAuthDO> authWrapper = new LambdaQueryWrapperX<TaskLeagueAuthDO>()
                    .eq(TaskLeagueAuthDO::getByAuthLeagueId, byAuthLeagueId);
            taskLeagueAuthMapper.update(new TaskLeagueAuthDO().setStatus(PayStatusEnum.WAIT_REFUND), authWrapper);
            //修改公会认证状态
            leagueMapper.updateById(new LeagueDO().setId(byAuthLeagueId).setAuthFlag(true));
            //修改公告栏状态
            int noticeUpdate = leagueNoticeMapper.updateById(new LeagueNoticeDO().setId(bo.getLeagueNoticeId()).setStatus(LeagueNoticeStatusEnum.NOTICE_STATUS_3));
            if (noticeUpdate <= 0) {
                ServiceException.throwServiceException(TASK_UPDATE_EXCEPTION);
            }
            //修改公会认证订单表状态
            taskLeagueAuthMapper.update(new TaskLeagueAuthDO().setStatus(PayStatusEnum.SPLIT_ACCOUNT)
                    , new LambdaQueryWrapperX<TaskLeagueAuthDO>().eq(TaskLeagueAuthDO::getNoticeId, bo.getLeagueNoticeId()));
            //免费情况下不生成订单
/*            if (leagueAuthUpdate <= 0) {
                ServiceException.throwServiceException(TASK_UPDATE_EXCEPTION);
            }*/
            //发公告
            bo.setLeagueNoticeType(requestNoticeType);
            leagueNoticeService.finish(bo);
            //发消息
            taskFinishSendMessage(bo, taskDO);
            //所有批次邀请公会认证的 个人中心消息置灰
            readOnlyMessage(taskDO);
            //所有批次邀请公会认证的 公告栏消息置灰
            readOnlyNotice(byAuthLeagueId);
            createLeagueAccount(byAuthLeagueId);
            //分账
            if (taskDO.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                callSplitAccount(bo, taskDO);
            }
            //退款    需求暂定是被动退款 定时退款  暂不实现主动退款
        });
        if (!tryLock) {
            //未获取到锁
            throw new ServiceException(TASK_LEAGUE_AUTH_LOCK);
        }
        //正常返回
        return new TaskFinishResultBO();
    }

    private void readOnlyNotice(Long byAuthLeagueId) {
        LambdaQueryWrapperX<LeagueNoticeDO> noticeWrapper = new LambdaQueryWrapperX<LeagueNoticeDO>()
                .eq(LeagueNoticeDO::getUseLeagueId, byAuthLeagueId)
                .eq(LeagueNoticeDO::getType, LeagueNoticeTypeEnum.NOTICE_TYPE_3)
                .eq(LeagueNoticeDO::getStatus, LeagueNoticeStatusEnum.NOTICE_STATUS_1);
        leagueNoticeMapper.update(new LeagueNoticeDO().setStatus(LeagueNoticeStatusEnum.NOTICE_STATUS_3), noticeWrapper);
    }

    private void createLeagueAccount(Long byAuthLeagueId) {
        CreateAccountRspBody account = walletApi.createAccount(new CreateAccountReqBody());
        int update = leagueAccountApi.updateLeagueAccount(byAuthLeagueId, account.getAccount());
        if (update < 1) {
            ServiceException.throwServiceException(LEAGUE_CREATE_ACCOUNT_FAIL);
        }
    }

    @Override
    public void afterPropertiesSet() {
        TaskFactory.register(TaskTypeEnum.TASK_TYPE_2.getValue(), this);
    }

    /**
     * 校验参数
     * 抽象   可能调用两次
     *
     * @param bo
     */
    private void checkBusinessParams(TaskInitBO bo) {
        if (Objects.isNull(bo.getAmount())) {
            throw new ServiceException(REQUEST_PARAMETERS_MISSING);
        }
        if (BigDecimal.ZERO.compareTo(bo.getAmount()) > 0) {
            throw new ServiceException(REQUEST_PARAMETER_ILLEGAL, "taskLeagueAuthAmount");
        }
        List<TaskLeagueBO> list = bo.getTaskInit2().getLeagueList();
        if (list.isEmpty()) {
            throw new ServiceException(LEAGUE_AUTH_INVITE_LIST_EMPTY);
        }
        //多选公会判断金额
        TaskUtils.checkLeagueList(list, bo.getAmount());
        //公会是否认证
        final Long byAuthLeagueId = bo.getTaskInit2().getByAuthLeagueId();
        if (Objects.isNull(byAuthLeagueId)) {
            throw new ServiceException(LEAGUE_INFO_EMPTY);
        }
        //选择的公会必须已认证
        taskUtils.leagueAuthFlagList(list);
        LeagueDO league = leagueMapper.selectById(byAuthLeagueId);
        if (league.getAuthFlag()) {
            throw new ServiceException(LEAGUE_HAS_AUTH);
        }
        //价格是否实时变动
        taskUtils.judgeLeagueRealTimePrice(list, bo.getType());
    }

    /**
     * 免费下单
     * 下单并支付
     *
     * @param bo
     * @return
     */
    private TaskInitResultVO freeInitTask(TaskInitBO bo) {
        //创建任务
        insertTask(bo, new TaskDO().setBusinessParams(bo.getTaskInit2().getByAuthLeagueId().toString()));
        //保存一份业务参数
        businessParamsMapper.insert(bo.getId().toString(), bo);
        //调用公告栏且发送个人消息通知
        bo.setLeagueNoticeType(requestNoticeType);
        leagueNoticeService.create(bo);
        return new TaskInitResultVO();
    }

    /**
     * 付费下单
     *
     * @param bo
     * @return
     */
    private TaskInitResultVO payInitTask(TaskInitBO bo) {
        OrderCreateRspDTO order = callOrder(bo);
        insertTask(bo, new TaskDO().setBusinessParams(bo.getTaskInit2().getByAuthLeagueId().toString()).setOrderRecord(order.getAppOrderNo()));
        bo.setOrderRecord(order.getAppOrderNo());
        businessParamsMapper.insert(bo.getId().toString(), bo, order);
        TaskInitResultVO resultBO = new TaskInitResultVO().setAppOrderNo(order.getAppOrderNo()).setFee(order.getFee());
        return resultBO;
    }

    /**
     * 下单
     *
     * @param bo
     * @return
     */
    private OrderCreateRspDTO callOrder(TaskInitBO bo) {
        OrderCreateReqDTO req = new OrderCreateReqDTO()
                .setOrderType(OrderType.AUTH_LEAGUE)
                .setPayerId(getLoginUserId())
                .setAccountType(AccountType.USER)
                .setAmount(bo.getAmount());
        return orderApi.createOrder(req);
    }

//    /**
//     * 校验支付参数
//     * @param bo
//     */
//    private void checkCreateTaskParams(TaskCreateBO bo)  {
//        if (StringUtils.isEmpty(bo.getAppOrderNo())) {
//            ServiceException.throwServiceException(REQUIRED_PARAMETER_IS_EMPTY);
//        }
//        if (StringUtils.isEmpty(bo.getPassword())) {
//            ServiceException.throwServiceException(REQUIRED_PARAMETER_IS_EMPTY);
//        }
//        if (StringUtils.isEmpty(bo.getPublicKey())) {
//            ServiceException.throwServiceException(REQUIRED_PARAMETER_IS_EMPTY);
//        }
//    }

    /**
     * 校验参数 完成任务
     *
     * @param bo
     */
    private void checkFinishTaskParams(TaskFinishBO bo) {
    }

    /**
     * 分账
     *
     * @param bo
     * @param taskDO
     */
    private void callSplitAccount(TaskFinishBO bo, TaskDO taskDO) {
        LambdaQueryWrapper<TaskDO> taskUpdateWrapper = new LambdaQueryWrapperX<TaskDO>()
                .eq(TaskDO::getBusinessParams, taskDO.getBusinessParams())
                .eq(TaskDO::getType, TaskTypeEnum.TASK_TYPE_2)
                .eq(TaskDO::getStatus, TaskStatusEnum.TASK_STATUS_1)
                .ne(TaskDO::getId, taskDO.getId());
        if (taskDO.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            if (Objects.isNull(taskDO.getOrderRecord())) {
                ServiceException.throwServiceException(ORDER_NUMBER_IS_EMPTY);
            }
            //更新任务状态
            taskMapper.update(new TaskDO().setStatus(TaskStatusEnum.TASK_STATUS_4), taskUpdateWrapper);
            //分账
            LeagueNoticeDO noticeDO = leagueNoticeMapper.selectById(bo.getLeagueNoticeId());
            TaskLeagueAuthDO leagueAuthDO = taskLeagueAuthMapper.selectByNoticeId(bo.getLeagueNoticeId());
            if (ObjectUtil.isNotNull(leagueAuthDO)) {
                //分账金额为订单表的金额
                SplitAccountVo splitAccountVo = SplitAccountUtil.splitByTwo(leagueAuthDO.getAmount());
                ReceiverInfo league = new ReceiverInfo().setReceiverId(noticeDO.getLeagueId()).setAccountType(AccountType.LEAGUE).setAmount(splitAccountVo.getLeagueAmount());
                ReceiverInfo platform = new ReceiverInfo().setReceiverId(1L).setAccountType(AccountType.PLATFORM).setAmount(splitAccountVo.getPlatformAmount());
                SplitAccountReqDTO splitAccountReqDTO = new SplitAccountReqDTO()
                        .setOrderType(OrderType.AUTH_LEAGUE)
                        .setAppOrderNo(taskDO.getOrderRecord())
                        .setReceiverList(Arrays.asList(league, platform));
                log.info("分账接口传入对象：" + splitAccountReqDTO);
                orderApi.splitAccount(splitAccountReqDTO);
            }
        } else {
            //所有更新为待退款
            taskMapper.update(new TaskDO().setStatus(TaskStatusEnum.TASK_STATUS_3), taskUpdateWrapper);
        }
    }

    /**
     * 公会认证完成后被认证公会所有管理员都会收到消息
     * 消息内容：【后端开发工程师】公会已完成公会认证，快去设置公会规则吧
     *
     * @param bo
     * @param taskDO
     */
    private void taskFinishSendMessage(TaskFinishBO bo, TaskDO taskDO) {
        long byAuthLeagueId = Long.parseLong(taskDO.getBusinessParams());
        List<Long> leagueAdminList = leagueMemberService.getLeagueAdmin(byAuthLeagueId);
        Long userId = getLoginUserId();
        SendMessageReqDTO message = SendMessageReqDTO.builder()
                .messageTemplate(MessageTemplateEnum.LEAGUE_CREATE_RULE_INVITE)
                .messageScene(MessageSceneEnum.LEAGUE_CREATE_RULE)
                .messageType(MessageTypeEnum.INVITE)
                .businessId(byAuthLeagueId)
//                .businessId(messageBusinessPreFix + byAuthLeagueId)
                .businessLink(null)
                .receivers(leagueAdminList)
                .messageParam(
                        new ContentParamVo()
                                .setInitiatorId(userId)
                                .setInitiatorLeagueId(byAuthLeagueId)
                                .setTaskLeagueAuthFinish(
                                        ContentParamVo.TaskLeagueAuthFinish.builder()
                                                .leagueId(byAuthLeagueId)
                                                .build()
                                )
                )
                .language(LanguageEnum.en_US)
                .dealStatus(MessageDealStatusEnum.DEAL)
                .build();
        sendMessageApi.sendMessage(message);
    }

    /**
     * 公会认证后相关消息设置为已读
     *
     * @param taskDO
     */
    private void readOnlyMessage(TaskDO taskDO) {
        LambdaQueryWrapper<TaskDO> taskWrapper = new LambdaQueryWrapperX<TaskDO>()
                .eq(TaskDO::getBusinessParams, taskDO.getBusinessParams())
                .eq(TaskDO::getType, TaskTypeEnum.TASK_TYPE_2)
                .ne(TaskDO::getId, taskDO.getId());
        List<TaskDO> taskList = taskMapper.selectList(taskWrapper);
//        taskMapper.update(new TaskDO().setStatus(TaskStatusEnum.TASK_STATUS_3), taskWrapper);
        //所有批次邀请公会认证的 个人中心消息置灰
        //数据库删除但refundList不变 可删
/*        if (taskList.isEmpty()) {
            throw new ServiceException(LEAGUE_AUTH_IS_DELETE);
        }*/
        sendMessageApi.messageDeal(new MessagesUpdateReqDTO().setTaskId(Long.parseLong(taskDO.getBusinessParams())).setStatus(MessageDealStatusEnum.ALREADY_DEAL));
    }
}
