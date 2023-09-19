package com.hisun.kugga.duke.league.handler.task;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.hisun.kugga.duke.enums.*;
import com.hisun.kugga.duke.league.bo.task.*;
import com.hisun.kugga.duke.league.dal.dataobject.BusinessParamsDO;
import com.hisun.kugga.duke.league.dal.dataobject.TaskDO;
import com.hisun.kugga.duke.league.dal.mysql.BusinessParamsMapper;
import com.hisun.kugga.duke.league.factory.TaskFactory;
import com.hisun.kugga.duke.league.service.LeagueNoticeService;
import com.hisun.kugga.duke.league.utils.TaskUtils;
import com.hisun.kugga.duke.league.vo.task.TaskCreateResultVO;
import com.hisun.kugga.duke.league.vo.task.TaskInitResultVO;
import com.hisun.kugga.duke.pay.api.order.OrderApi;
import com.hisun.kugga.duke.pay.api.order.dto.OrderCreateReqDTO;
import com.hisun.kugga.duke.pay.api.order.dto.OrderCreateRspDTO;
import com.hisun.kugga.duke.pay.api.order.dto.PayReqDTO;
import com.hisun.kugga.duke.user.api.oauth2.DukeUserApi;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.utils.RedissonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static com.hisun.kugga.duke.common.RedisKeyPrefixConstants.TASK_LOCK_RECOMMENDATION;
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
public class TaskWriteReport extends AbstractTaskHandler {

    private static final TaskTypeEnum taskType = TaskTypeEnum.TASK_TYPE_1;
    private static final LeagueNoticeTypeEnum requestNoticeType = LeagueNoticeTypeEnum.NOTICE_TYPE_1;

    LeagueNoticeService leagueNoticeService;
    OrderApi orderApi;
    DukeUserApi dukeUserApi;
    TaskUtils taskUtils;
    BusinessParamsMapper businessParamsMapper;
    RedissonUtils redissonUtils;

    @Override
    public TaskInitResultVO initTask(TaskInitBO bo) {
        //前置校验
        preCheck(bo);
        if (BigDecimal.ZERO.compareTo(bo.getAmount()) == 0) {
            //免费
            return freeInitTask(bo);
        } else {
            //付费
            return payInitTask(bo);
        }
    }

    /**
     * 推荐报告
     * <p>
     * 前置条件
     * 选择公会列表不能为空，必须选一个及以上
     * 所有选择的公会 是否认证
     * <p>
     * 1    写推荐报告不能自己给自己写
     * 2    公会ID和金额不能为空
     * 3    任务总计金额计算错误，请重新计算    判断金钱相加是否相等  请勿用BigDecimal的equals方法！错误的比较方法！
     * 4    去重（前端做了，后端再做一次去重？）
     * 5    判断价格是否符合预期  价格是否实时变动
     * <p>
     * 代码逻辑
     * 1    创建任务，下单，成功后保存订单号，创建任务，调用订单模块，默认下单成功(费用大于0调用订单接口)
     * 2    支付
     * 3    创建公会公告
     * 4    调用消息模块，带参数过去
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
        boolean tryLock = redissonUtils.tryLock(TASK_LOCK_RECOMMENDATION + bo.getAppOrderNo(), () -> {
            TaskDO taskDO = taskMapper.selectOne(TaskDO::getOrderRecord, bo.getAppOrderNo());
            if (taskDO == null) {
                ServiceException.throwServiceException(ORDER_NOT_EXISTS);
            }
            if (ObjectUtil.notEqual(taskDO.getStatus(), TaskStatusEnum.TASK_STATUS_0)) {
                ServiceException.throwServiceException(ORDER_HAS_BEEN_PAID);
            }
            //支付
            orderApi.pay(new PayReqDTO().setAppOrderNo(bo.getAppOrderNo()).setPayChannel(PayChannel.BALANCE));
            //更新任务状态为已支付
            taskMapper.updateById(new TaskDO().setId(taskDO.getId()).setStatus(TaskStatusEnum.TASK_STATUS_1));
            //查询业务参数
            BusinessParamsDO businessParamsDO = businessParamsMapper.selectOne(BusinessParamsDO::getBusinessId, taskDO.getId());
            JSONArray jsonArray = JSONUtil.parseArray(businessParamsDO.getParams());
            TaskInitBO taskInitBO = jsonArray.get(0, TaskInitBO.class);
            //调用公告栏且发送个人消息通知
            taskInitBO.setLeagueNoticeType(LeagueNoticeTypeEnum.NOTICE_TYPE_1);
            leagueNoticeService.create(taskInitBO);
        });
        if (!tryLock) {
            //未获取到锁
            throw new ServiceException(TASK_LEAGUE_AUTH_LOCK);
        }
        //正常返回
        return new TaskCreateResultVO();
    }


    @Override
    public CommonResult accept(TaskAcceptBO bo) {
        bo.setLeagueNoticeType(LeagueNoticeTypeEnum.NOTICE_TYPE_1);
        return leagueNoticeService.accept(bo);
    }

    /**
     * 完成此公告栏   公会内抢单
     * 分配钱
     * 查询任务下所有公告完成状态 基于所有公告反驱动更新任务状态    暂时不做
     *
     * @param bo
     * @return
     */
    @Override
    public TaskFinishResultBO finish(TaskFinishBO bo) {
        bo.setLeagueNoticeType(requestNoticeType);
        leagueNoticeService.finish(bo);
        return new TaskFinishResultBO();
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
        insertTask(bo);
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
        super.insertTask(bo, new TaskDO().setOrderRecord(order.getAppOrderNo()));
        bo.setOrderRecord(order.getAppOrderNo());
        businessParamsMapper.insert(bo.getId().toString(), bo, order);
        TaskInitResultVO resultBO = new TaskInitResultVO().setAppOrderNo(order.getAppOrderNo()).setFee(order.getFee());
        return resultBO;
    }

    private OrderCreateRspDTO callOrder(TaskInitBO bo) {
        OrderCreateReqDTO req = new OrderCreateReqDTO()
                .setOrderType(OrderType.CREATE_RECOMMENDATION)
                .setPayerId(getLoginUserId())
                .setAccountType(AccountType.USER)
                .setAmount(bo.getAmount());
        return orderApi.createOrder(req);
    }


    @Override
    public void afterPropertiesSet() {
        TaskFactory.register(taskType.getValue(), this);
    }

    private void preCheck(TaskInitBO bo) {
        List<TaskLeagueBO> leagueList = bo.getTaskInit1().getLeagueList();
        if (ObjectUtil.isNull(bo.getAmount())) {
            ServiceException.throwServiceException(REQUIRED_PARAMETER_IS_EMPTY);
        }
        if (leagueList.isEmpty()) {
            ServiceException.throwServiceException(REPORT_INVITE_LEAGUE_LIST_EMPTY);
        }
        //判断金钱相加是否相等 总金额计算不能错误
        TaskUtils.checkLeagueList(leagueList, bo.getAmount());
        //公会是否认证
        taskUtils.leagueAuthFlagList(leagueList);
        //去重
//        List<TaskLeagueBO> list = leagueList.stream().distinct().collect(Collectors.toList());
        //价格是否实时变动
        taskUtils.judgeLeagueRealTimePrice(leagueList, bo.getType());
    }
}
