package com.hisun.kugga.duke.batch.service;

import com.hisun.kugga.duke.batch.dal.dataobject.leaguebill.LeagueBillDO;

/**
 * League Bill Service Interface
 *
 * Author: zhou_xiong
 */
public interface LeagueBillService {
    /**
     * Generate a bill if it does not already exist for the order.
     *
     * @param leagueBillDO The LeagueBillDO object.
     */
    void insertIfNotExist(LeagueBillDO leagueBillDO);
}
