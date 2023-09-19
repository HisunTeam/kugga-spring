package com.hisun.kugga.duke.batch.job.league;

import com.hisun.kugga.duke.batch.dal.dataobject.league.TaskDO;
import com.hisun.kugga.duke.batch.dal.mysql.league.TaskMapper;
import com.hisun.kugga.duke.batch.service.LeagueTaskService;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.quartz.core.handler.JobHandler;
import com.hisun.kugga.framework.utils.RedissonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 认证失效定时任务
 *
 * @author: zuo_cheng
 */
@Slf4j
@Component
@AllArgsConstructor
public class TaskLeagueAuthByRefundJob implements JobHandler {

    public static final String TASK_LOCK_LEAGUE_AUTH = "task:league_auth:";
    private TaskMapper taskMapper;
    private LeagueTaskService leagueTaskService;
    private RedissonUtils redissonUtils;

    /**
     * 公会认证 被动退款 定时任务
     * 先做业务再退款
     * duke_task    任务表
     * duke_league_notice   公告栏表
     * duke_task_league_auth 公会认证订单表
     * <p>
     * 1 扫描类型是公会认证 状态是待退款  所有任务
     * 2 加入分布式锁 key为    "task:league_auth:"+订单号
     * 业务退款
     * 3 更改任务表状态 条件是当前任务
     * 4 更改公告栏状态 条件为当前任务ID 类型是公会认证 （类型为4 TASK_STATUS_4） 状态为待退款     更改任状态为已退款
     * 5 查询公会认证订单表 条件为当前任务ID 状态为已支付 把所有金额加起来        得出 总金额
     * 调用退款
     * 6 拿总金额 和 任务字段order_record 调用退款接口
     *
     * @param param 参数
     * @return
     * @throws Exception
     */
    @Override
    public String execute(String param) throws Exception {
        log.info(" leagueAuthRefund 定时处理邀请链接失效数据 start:{}", LocalDateTime.now());
        List<TaskDO> list = taskMapper.queryLeagueAuthRefundList();
        list.stream().forEach(taskDO ->
                redissonUtils.tryLock(TASK_LOCK_LEAGUE_AUTH + taskDO.getOrderRecord(), () -> leagueTaskService.leagueAuthRefund(taskDO))
        );
        log.info("leagueAuthRefund 定时处理邀请链接失效数据 end:{}", LocalDateTime.now());
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }
}
