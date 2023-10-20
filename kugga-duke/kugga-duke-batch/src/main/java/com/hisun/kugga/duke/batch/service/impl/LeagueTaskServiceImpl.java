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
 * League Task Service Implementation
 *
 * Author: Zuo Cheng
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
            log.info("Task ID [{}], when processing refund for expired league authentication, the task is in an abnormal state and cannot execute the refund process", task.getId());
            return;
        }
        // Set league notices to invalid
        leagueNoticeMapper.updateInvalidByTaskId(task.getId());
        // Retrieve the list of authentications
        List<TaskLeagueAuthDO> auths = taskLeagueAuthMapper.selectByTaskId(task.getId());
        // Amount to be refunded
        BigDecimal refAmt = new BigDecimal("0");
        // Sum up the associated amounts for the task
        for (TaskLeagueAuthDO obj : auths) {
            refAmt = refAmt.add(obj.getAmount());
        }
        // Update authentication orders to refunded
        taskLeagueAuthMapper.updateRefundByTaskId(task.getId());

        log.info("League authentication expired refund order number [{}], refund amount [{}]", task.getOrderRecord(), refAmt);

        if (refAmt.compareTo(new BigDecimal("0")) == 0) {
            log.info("Task ID [{}], when processing refund for expired league authentication, the refund amount is 0, no refund is made", task.getId());
            return;
        }

        try {
            payOrderService.refund(new RefundReqDTO()
                    .setAppOrderNo(task.getOrderRecord())
                    .setRefundAmount(refAmt));
        } catch (Exception e) {
            // Ignore errors, log for troubleshooting
            log.error("Order [{}] failed to refund when processing refund for expired league authentication", task.getOrderRecord(), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void chatExpire(TaskDO task) {
        int res0 = taskMapper.updateChatExpire(task.getId());
        if (res0 != 1) {
            log.info("Task ID [{}], when processing refund for expired chat, the task is in an abnormal state and cannot execute the refund process", task.getId());
            return;
        }
        // Set notices to invalid
        leagueNoticeMapper.updateInvalidByTaskId(task.getId());
        // Retrieve the list of chat orders
        List<TaskChatDO> chats = taskChatMapper.selectByTaskId(task.getId());
        // Amount to be refunded
        BigDecimal refAmt = new BigDecimal("0");
        // Sum up the associated amounts for the task
        for (TaskChatDO obj : chats) {
            refAmt = refAmt.add(obj.getAmount());
        }
        // Update chat orders to refunded
        taskChatMapper.updateRefundByTaskId(task.getId());

        log.info("Chat expired refund order number [{}], refund amount [{}]", task.getOrderRecord(), refAmt);

        if (refAmt.compareTo(new BigDecimal("0")) == 0) {
            log.info("Task ID [{}], when processing refund for expired chat, the refund amount is 0, no refund is made", task.getId());
            return;
        }

        try {
            payOrderService.refund(new RefundReqDTO()
                    .setAppOrderNo(task.getOrderRecord())
                    .setRefundAmount(refAmt));
        } catch (Exception e) {
            // Ignore errors, log for troubleshooting
            log.error("Order [{}] failed to refund when processing refund for expired chat", task.getOrderRecord(), e);
        }
    }

    @Override
    public void reportExpire(LeagueNoticeDO notice) {
        LeagueNoticeDO update = new LeagueNoticeDO();
        update.setId(notice.getId());
        update.setStatus(LeagueNoticeStatusEnum.NOTICE_STATUS_4);
        update.setUpdater("SYS");

        // Set notices to invalid
        leagueNoticeMapper.updateInvalidById(update);
        // Get order number and order amount based on the notice ID
        TaskReportDO report = taskReportMapper.selectByNoticeId(notice.getId());
        if (ObjectUtil.isNull(report)) {
            log.info("Notice ID [{}], no refundable data available", notice.getId());
            return;
        }
        // Amount to be refunded
        BigDecimal refAmt = report.getAmount();
        // Update report orders to refunded
        taskReportMapper.updateRefundByNoticeId(notice.getId());

        log.info("Report expired refund order number [{}], refund amount [{}]", report.getAppOrderNo(), refAmt);

        if (ObjectUtil.isNull(refAmt) || refAmt.compareTo(new BigDecimal("0")) == 0) {
            log.info("Notice ID [{}], when processing refund for expired report, the refund amount is 0, no refund is made", notice.getId());
            return;
        }

        try {
            payOrderService.refund(new RefundReqDTO()
                    .setAppOrderNo(report.getAppOrderNo())
                    .setRefundAmount(refAmt));
        } catch (Exception e) {
            // Ignore errors, log for troubleshooting
            log.error("Order [{}] failed to refund when processing refund for expired report", report.getAppOrderNo(), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void leagueAuthRefund(TaskDO taskDO) {
        // Update task status
        taskMapper.updateById(new TaskDO().setId(taskDO.getId()).setStatus(TASK_STATUS_5).setUpdater("leagueAuthRefund"));
        LambdaQueryWrapperX<LeagueNoticeDO> noticeWrapper = new LambdaQueryWrapperX<LeagueNoticeDO>()
                .eq(LeagueNoticeDO::getTaskId, taskDO.getId())
                .eq(LeagueNoticeDO::getType, LeagueNoticeTypeEnum.NOTICE_TYPE_3)
                .eq(LeagueNoticeDO::getStatus, LeagueNoticeStatusEnum.NOTICE_STATUS_1);
        // Change notice status
        leagueNoticeMapper.update(new LeagueNoticeDO().setStatus(LeagueNoticeStatusEnum.NOTICE_STATUS_3).setUpdater("leagueAuthRefund"), noticeWrapper);

        LambdaQueryWrapperX<TaskLeagueAuthDO> orderWrapper = new LambdaQueryWrapperX<TaskLeagueAuthDO>()
                .eq(TaskLeagueAuthDO::getTaskId, taskDO.getId())
                .eq(TaskLeagueAuthDO::getPayType, TaskPayTypeEnum.PAY)
                .eq(TaskLeagueAuthDO::getStatus, PayStatusEnum.WAIT_REFUND);
        // Query the list of orders
        List<TaskLeagueAuthDO> orderList = taskLeagueAuthMapper.selectList(orderWrapper);
        // Update order status
        taskLeagueAuthMapper.update(new TaskLeagueAuthDO().setStatus(PayStatusEnum.REFUND).setUpdater("leagueAuthRefund"), orderWrapper);
        BigDecimal amount = orderList.stream().map(TaskLeagueAuthDO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("League authentication expired refund order number [{}], refund amount [{}]", taskDO.getOrderRecord(), amount);
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            log.info("Task ID [{}], when processing refund for expired league authentication, the refund amount is 0, no refund is made", taskDO.getId());
            return;
        }
        try {
            payOrderService.refund(new RefundReqDTO().setAppOrderNo(taskDO.getOrderRecord()).setRefundAmount(amount));
        } catch (Exception e) {
            // Ignore errors, log for troubleshooting
            log.error("Order [{}] failed to refund when processing refund for expired league authentication", taskDO.getOrderRecord(), e);
            throw new RuntimeException();
        }
    }
}
