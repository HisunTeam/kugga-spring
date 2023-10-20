package com.hisun.kugga.duke.batch.service;

import com.hisun.kugga.duke.batch.bo.BillBO;

/**
 * Author: zhou_xiong
 */
public interface BillService {
    /**
     * Generate bills based on the account type.
     *
     * @param billBO The billBO object containing bill details.
     */
    void saveBillByAccountType(BillBO billBO);
}
