package com.hisun.kugga.duke.batch.service;

import com.hisun.kugga.duke.batch.dal.dataobject.league.LeagueNoticeDO;
import com.hisun.kugga.duke.batch.dal.dataobject.league.TaskDO;

/**
 * 公会任务服务(用户公会认证、聊天 、推荐报告退款)
 *
 * @author zuo_cheng
 */
public interface LeagueTaskService {
    /**
     * 公会认证过期处理
     *
     * @param task
     */
    void authExpire(TaskDO task);

    /**
     * 公会聊天过期处理
     *
     * @param task
     */
    void chatExpire(TaskDO task);

    /**
     * 推荐报告退款
     *
     * @param notice
     */
    void reportExpire(LeagueNoticeDO notice);

    void leagueAuthRefund(TaskDO taskDO);
}
