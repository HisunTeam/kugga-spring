package com.hisun.kugga.duke.batch.service;

import com.hisun.kugga.duke.batch.dal.dataobject.userbill.UserBillDO;

/**
 * User Bill Service Interface
 *
 * @author zhou_xiong
 */
public interface UserBillService {

    /**
     * Generate a bill if it hasn't been created for the order
     *
     * @param userBillDO
     */
    void insertIfNotExist(UserBillDO userBillDO);

}
