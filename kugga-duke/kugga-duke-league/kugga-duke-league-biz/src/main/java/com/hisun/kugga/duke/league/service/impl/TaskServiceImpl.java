package com.hisun.kugga.duke.league.service.impl;

import com.hisun.kugga.duke.league.bo.task.*;
import com.hisun.kugga.duke.league.dal.dataobject.TaskDO;
import com.hisun.kugga.duke.league.dal.mysql.TaskMapper;
import com.hisun.kugga.duke.league.factory.TaskFactory;
import com.hisun.kugga.duke.league.handler.task.AbstractTaskHandler;
import com.hisun.kugga.duke.league.service.TaskService;
import com.hisun.kugga.duke.league.vo.task.TaskCreateResultVO;
import com.hisun.kugga.duke.league.vo.task.TaskInitResultVO;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.idempotent.core.annotation.Idempotent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.hisun.kugga.duke.league.constants.TaskConstants.ORDER_NOT_EXISTS;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:44
 */
@Slf4j
@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    TaskMapper taskMapper;

    @Override
    @Idempotent(timeout = 2, message = "Repeat init task,please try again later")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TaskInitResultVO init(TaskInitBO bo) {
        AbstractTaskHandler build = TaskFactory.build(bo.getType().getValue());
        return build.initTask(bo);
    }

    /**
     * 发起一个任务
     *
     * @param bo
     * @return
     */
    @Override
    @Idempotent(timeout = 2, message = "Repeat create task,please try again later")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TaskCreateResultVO create(TaskCreateBO bo) {
        /**
         * 冻结金额 -> 账单  -> 任务 -> 公告板 -> 消息
         * 1    用户发布任务 推荐报告，认证，聊天
         * （用户或消息）  -> 任务service -> 任务工厂 -> 公告栏service -> 公告栏工厂 -> 消息模块（发给用户）
         *                                                           -> 订单模块（扣钱）
         *                                                           -> 聊天模块（查询任务状态及支付状态）
         *                                                           -> 目前支持 任务抢单模式，公会内抢单模式
         *                                                           -> 公会模块（查询用户是否在公会列表  免费公会 和 收费公会）
         *  2   种类      1 推荐报告 2 认证 3 聊天        4红包，活动 等等 待拓展
         */
        /**
         *  生成订单 扣钱操作
         *  查询订单状态 是否支付成功
         *  查询用户是否在公会列表  免费公会 和 收费公会
         */
        TaskDO taskDO = taskMapper.selectOne(TaskDO::getOrderRecord, bo.getAppOrderNo());
        Optional.ofNullable(taskDO).orElseThrow(() -> new ServiceException(ORDER_NOT_EXISTS, bo.getAppOrderNo()));
        AbstractTaskHandler build = TaskFactory.build(taskDO.getType().getValue());
        return build.createTask(bo);
    }

    /**
     * 接单
     *
     * @param bo
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Idempotent(timeout = 2, message = "Repeat accept task,please try again later")
    public CommonResult accept(TaskAcceptBO bo) {
        AbstractTaskHandler build = TaskFactory.build(bo.getType().getValue());
        return build.accept(bo);
    }

    /**
     * 完成
     *
     * @param bo
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Idempotent(timeout = 2, message = "Repeat finish task,please try again later")
    public TaskFinishResultBO finish(TaskFinishBO bo) {
        //完成任务
        //完成公告栏
        AbstractTaskHandler build = TaskFactory.build(bo.getType().getValue());
        return build.finish(bo);
    }
}
