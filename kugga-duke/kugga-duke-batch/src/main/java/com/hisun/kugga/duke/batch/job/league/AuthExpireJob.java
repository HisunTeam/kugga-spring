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
 * 认证失效定时任务
 *
 * @author: zuo_cheng
 */
@Slf4j
@Component
public class AuthExpireJob implements JobHandler {

    public static final String TASK_LOCK_LEAGUE_AUTH = "task:league_auth:";

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private LeagueTaskService leagueTaskService;

    @Resource
    private RedissonUtils redissonUtils;
    @Resource
    private MessagesMapper messagesMapper;

    /**
     * 公会认证 过期退款 定时任务
     * 先做业务再退款
     * duke_task    任务表
     * duke_league_notice   公告栏表
     * duke_task_league_auth 公会认证订单表
     * 1 扫描类型是公会认证，状态是已支付，时间已过期的所有任务，
     * 2 加入分布式锁 key为    "task:league_auth:"+被认证的公会ID
     * 3 更改公告栏表 条件为当前任务ID 类型是公会认证 （类型为3） 状态为已发布     更改任状态为已退款
     * 4 查询公会认证订单表 条件为当前任务ID 状态为已支付 把所有金额加起来        得出 总金额
     * 5 拿总金额 和 任务字段order_record 调用退款接口
     *
     * @param param 参数
     * @return
     * @throws Exception
     */
    @Override
    public String execute(String param) throws Exception {
        log.info(" AuthExpire 定时处理认证过期 start:{}", LocalDateTime.now());
        List<TaskDO> list = taskMapper.queryAuthExpire();
        list.stream().forEach(item ->
                redissonUtils.tryLock(TASK_LOCK_LEAGUE_AUTH + item.getOrderRecord(), () -> leagueTaskService.authExpire(item))
        );

        dealMessageExpire(list);

        log.info("AuthExpire 定时处理认证过期 end:{}", LocalDateTime.now());
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
