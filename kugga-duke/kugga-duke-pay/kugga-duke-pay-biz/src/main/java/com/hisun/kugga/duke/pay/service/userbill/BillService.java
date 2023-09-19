package com.hisun.kugga.duke.pay.service.userbill;

import com.hisun.kugga.duke.pay.bo.BillBO;

/**
 * @author: zhou_xiong
 */
public interface BillService {
    /**
     * 根据账户类型生成账单
     *
     * @param billBO
     */
    void saveBillByAccountType(BillBO billBO);
}
