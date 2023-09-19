package com.hisun.kugga.duke.batch.service;

import com.hisun.kugga.duke.batch.dal.dataobject.userbill.UserBillDO;

/**
 * 用户账单 Service 接口
 *
 * @author zhou_xiong
 */
public interface UserBillService {

    /**
     * 如果订单没生成账单，就生成
     *
     * @param userBillDO
     */
    void insertIfNotExist(UserBillDO userBillDO);

}
