package com.hisun.kugga.duke.batch.service;

import com.hisun.kugga.duke.batch.dal.dataobject.leaguebill.LeagueBillDO;

/**
 * 公会账单 Service 接口
 *
 * @author zhou_xiong
 */
public interface LeagueBillService {
    /**
     * 如果订单没生成账单，就生成
     *
     * @param leagueBillDO
     */
    void insertIfNotExist(LeagueBillDO leagueBillDO);
}
