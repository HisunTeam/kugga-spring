package com.hisun.kugga.duke.batch.service;

import com.hisun.kugga.duke.batch.dal.dataobject.league.LeagueNoticeDO;
import com.hisun.kugga.duke.batch.dal.dataobject.league.TaskDO;

/**
 * Public Association Task Service (User Association Certification, Chat, Recommendation Report Refund)
 *
 * @author zuo_cheng
 */
public interface LeagueTaskService {
    /**
     * Public Association Authentication Expiration Processing
     *
     * @param task The TaskDO object.
     */
    void authExpire(TaskDO task);

    /**
     * Public Association Chat Expiration Processing
     *
     * @param task The TaskDO object.
     */
    void chatExpire(TaskDO task);

    /**
     * Recommendation Report Refund
     *
     * @param notice The LeagueNoticeDO object.
     */
    void reportExpire(LeagueNoticeDO notice);

    void leagueAuthRefund(TaskDO taskDO);
}
