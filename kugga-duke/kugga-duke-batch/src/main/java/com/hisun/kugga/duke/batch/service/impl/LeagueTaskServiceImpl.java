package com.hisun.kugga.duke.batch.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.batch.dal.dataobject.league.*;
import com.hisun.kugga.duke.batch.dal.mysql.league.*;
import com.hisun.kugga.duke.batch.dto.RefundReqDTO;
import com.hisun.kugga.duke.batch.service.LeagueTaskService;
import com.hisun.kugga.duke.batch.service.PayOrderService;
import com.hisun.kugga.duke.enums.LeagueNoticeStatusEnum;
import com.hisun.kugga.duke.enums.LeagueNoticeTypeEnum;
import com.hisun.kugga.duke.enums.PayStatusEnum;
import com.hisun.kugga.duke.enums.TaskPayTypeEnum;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;

import static com.hisun.kugga.duke.enums.TaskStatusEnum.TASK_STATUS_5;

/**
 * 公会聊天、推荐报告、认证退款等
 *
 * @author zuo_cheng
 */
@Slf4j
@Service
@Validated
@AllArgsConstructor
public class LeagueTaskServiceImpl implements LeagueTaskService {

    private LeagueNoticeMapper leagueNoticeMapper;
    private TaskMapper taskMapper;
    private TaskLeagueAuthMapper taskLeagueAuthMapper;
    private PayOrderService payOrderService;
    private TaskChatMapper taskChatMapper;
    private TaskReportMapper taskReportMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void authExpire(TaskDO task) {
        int res0 = taskMapper.updateAuthExpire(task.getId());
        if (res0 != 1) {
            log.info("任务ID[{}],做公会认证过期退款时,任务为状态异常,无法执行退款处理", task.getId());
            return;
        }
        //将公会公告设置成失效
        leagueNoticeMapper.updateInvalidByTaskId(task.getId());
        //查询认证列表
        List<TaskLeagueAuthDO> auths = taskLeagueAuthMapper.selectByTaskId(task.getId());
        //待退款金额
        BigDecimal refAmt = new BigDecimal("0");
        //把任务所关联的金额金相加
        for (TaskLeagueAuthDO obj : auths) {
            refAmt = refAmt.add(obj.getAmount());
        }
        //更新认证订单为退款
        taskLeagueAuthMapper.updateRefundByTaskId(task.getId());

        log.info("公会认证过期退款订单号[{}],退款金额[{}]", task.getOrderRecord(), refAmt);

        if (refAmt.compareTo(new BigDecimal("0")) == 0) {
            log.info("任务ID[{}],做公会认证过期退款时,待退款金额为0,不做退款", task.getId());
            return;
        }

        try {
            payOrderService.refund(new RefundReqDTO()
                    .setAppOrderNo(task.getOrderRecord())
                    .setRefundAmount(refAmt));
        } catch (Exception e) {
            //忽略报错,打印日志方便追查问题
            log.error("订单[{}]做公会认证过期退款时,退款失败", task.getOrderRecord(), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void chatExpire(TaskDO task) {
        int res0 = taskMapper.updateChatExpire(task.getId());
        if (res0 != 1) {
            log.info("任务ID[{}],做聊天过期退款时,任务为状态异常,无法执行退款处理", task.getId());
            return;
        }
        //更新公告为失效
        leagueNoticeMapper.updateInvalidByTaskId(task.getId());
        //查询聊天订单列表
        List<TaskChatDO> chats = taskChatMapper.selectByTaskId(task.getId());
        //待退款金额
        BigDecimal refAmt = new BigDecimal("0");
        //把任务所关联的金额金相加
        for (TaskChatDO obj : chats) {
            refAmt = refAmt.add(obj.getAmount());
        }
        //更新认证订单为退款
        taskChatMapper.updateRefundByTaskId(task.getId());

        log.info("聊天过期退款订单号[{}],退款金额[{}]", task.getOrderRecord(), refAmt);

        if (refAmt.compareTo(new BigDecimal("0")) == 0) {
            log.info("任务ID[{}],做聊天过期退款时,待退款金额为0,不做退款", task.getId());
            return;
        }

        try {
            payOrderService.refund(new RefundReqDTO()
                    .setAppOrderNo(task.getOrderRecord())
                    .setRefundAmount(refAmt));
        } catch (Exception e) {
            //忽略报错,打印日志方便追查问题
            log.error("订单[{}]做聊天过期退款时,退款失败", task.getOrderRecord(), e);
        }
    }

    @Override
    public void reportExpire(LeagueNoticeDO notice) {
        LeagueNoticeDO update = new LeagueNoticeDO();
        update.setId(notice.getId());
        update.setStatus(LeagueNoticeStatusEnum.NOTICE_STATUS_4);
        update.setUpdater("SYS");

        //更新公告为失效
        leagueNoticeMapper.updateInvalidById(update);
        //根据通知ID获取到定单号与定单金额
        TaskReportDO report = taskReportMapper.selectByNoticeId(notice.getId());
        if (ObjectUtil.isNull(report)) {
            log.info("通知ID[{}],无可退款数据", notice.getId());
            return;
        }
        //待退款金额
        BigDecimal refAmt = report.getAmount();
        //更新认证订单为退款
        taskReportMapper.updateRefundByNoticeId(notice.getId());

        log.info("推荐报告过期退款订单号[{}],退款金额[{}]", report.getAppOrderNo(), refAmt);

        if (ObjectUtil.isNull(refAmt) || refAmt.compareTo(new BigDecimal("0")) == 0) {
            log.info("通知ID[{}],做推荐报告过期退款时,待退款金额为0,不做退款", notice.getId());
            return;
        }

        try {
            payOrderService.refund(new RefundReqDTO()
                    .setAppOrderNo(report.getAppOrderNo())
                    .setRefundAmount(refAmt));
        } catch (Exception e) {
            //忽略报错,打印日志方便追查问题
            log.error("订单[{}]做推荐报告过期退款时,退款失败", report.getAppOrderNo(), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void leagueAuthRefund(TaskDO taskDO) {
        //更新任务状态
        taskMapper.updateById(new TaskDO().setId(taskDO.getId()).setStatus(TASK_STATUS_5).setUpdater("leagueAuthRefund"));
        LambdaQueryWrapperX<LeagueNoticeDO> noticeWrapper = new LambdaQueryWrapperX<LeagueNoticeDO>()
                .eq(LeagueNoticeDO::getTaskId, taskDO.getId())
                .eq(LeagueNoticeDO::getType, LeagueNoticeTypeEnum.NOTICE_TYPE_3)
                .eq(LeagueNoticeDO::getStatus, LeagueNoticeStatusEnum.NOTICE_STATUS_1);
        //更改公告栏状态
        leagueNoticeMapper.update(new LeagueNoticeDO().setStatus(LeagueNoticeStatusEnum.NOTICE_STATUS_3).setUpdater("leagueAuthRefund"), noticeWrapper);

        LambdaQueryWrapperX<TaskLeagueAuthDO> orderWrapper = new LambdaQueryWrapperX<TaskLeagueAuthDO>()
                .eq(TaskLeagueAuthDO::getTaskId, taskDO.getId())
                .eq(TaskLeagueAuthDO::getPayType, TaskPayTypeEnum.PAY)
                .eq(TaskLeagueAuthDO::getStatus, PayStatusEnum.WAIT_REFUND);
        //查询订单列表
        List<TaskLeagueAuthDO> orderList = taskLeagueAuthMapper.selectList(orderWrapper);
        //更新订单状态
        taskLeagueAuthMapper.update(new TaskLeagueAuthDO().setStatus(PayStatusEnum.REFUND).setUpdater("leagueAuthRefund"), orderWrapper);
        BigDecimal amount = orderList.stream().map(TaskLeagueAuthDO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("公会认证过期退款订单号[{}],退款金额[{}]", taskDO.getOrderRecord(), amount);
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            log.info("任务ID[{}],做公会认证过期退款时,待退款金额为0,不做退款", taskDO.getId());
            return;
        }
        try {
            payOrderService.refund(new RefundReqDTO().setAppOrderNo(taskDO.getOrderRecord()).setRefundAmount(amount));
        } catch (Exception e) {
            //忽略报错,打印日志方便追查问题
            log.error("订单[{}]做公会认证过期退款时,退款失败", taskDO.getOrderRecord(), e);
            throw new RuntimeException();
        }
    }
}
