package com.hisun.kugga.duke.batch.job.league;

import com.hisun.kugga.duke.batch.dal.dataobject.league.TaskDO;
import com.hisun.kugga.duke.batch.dal.dataobject.message.MessagesDO;
import com.hisun.kugga.duke.batch.dal.mysql.league.TaskMapper;
import com.hisun.kugga.duke.batch.dal.mysql.message.MessagesMapper;
import com.hisun.kugga.duke.batch.service.LeagueTaskService;
import com.hisun.kugga.duke.enums.message.MessageDealStatusEnum;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.quartz.core.handler.JobHandler;
import com.hisun.kugga.framework.utils.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 聊天失效定时任务
 *
 * @author: zuo_cheng
 */
@Slf4j
@Component
public class ChatExpireJob implements JobHandler {

    public static final String TASK_LOCK_CHAT = "task:chat:";

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private RedissonUtils redissonUtils;

    @Resource
    private LeagueTaskService leagueTaskService;

    @Resource
    private MessagesMapper messagesMapper;

    /**
     * 聊天 过期退款 定时任务
     * 先做业务再退款
     * duke_task    任务表
     * duke_league_notice   公告栏表
     * duke_task_chat 任务聊天订单表
     * 1 扫描任务表  类型是聊天，状态是已支付，时间已过期，
     * 2 加入分布式锁 key为    RedisKeyPrefixConstants.TASK_LOCK_CHAT+订单号(TaskDO.orderRecord)
     * 退款业务
     * 3 更改任务状态为已退款
     * 3 更改公告栏表 条件为当前任务ID 类型是 聊天 （类型为5） 状态为已发布     更改任状态为已退款
     * 4 查询 任务聊天订单表 条件为当前任务ID 状态为已支付    获取到金额字段 amount
     * 调用退款接口
     * 5 拿金额 和 任务字段order_record 订单号 调用退款接口
     *
     * @param param 参数
     * @return
     * @throws Exception
     */
    @Override
    public String execute(String param) throws Exception {
        log.info(" ChatExpire 定时处理聊天过期 start:{}", LocalDateTime.now());
        List<TaskDO> list = taskMapper.queryChatExpire();
        list.stream().forEach(item -> redissonUtils.tryLock(TASK_LOCK_CHAT + item.getOrderRecord(), () -> leagueTaskService.chatExpire(item)));

        dealMessageExpire(list);

        log.info(" ChatExpire 定时处理聊天过期 end:{}", LocalDateTime.now());
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    private void dealMessageExpire(List<TaskDO> list) {
        //任务失效后把消息改为 已过期
        for (TaskDO taskDO : list) {
            MessagesDO messagesDO = new MessagesDO().setDealFlag(MessageDealStatusEnum.EXPIRE.getCode());
            messagesMapper.updateExpireByBusinessId(messagesDO, taskDO.getId());
        }
    }

}
