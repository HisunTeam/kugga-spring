package com.hisun.kugga.duke.bos.service.wallet;


import com.hisun.kugga.duke.bos.controller.admin.wallet.dto.AccountDetailRspBody;

import java.math.BigDecimal;

/**
 * @author: lzt
 */

public interface PlatformAccountService {


    BigDecimal selectAccountDetail();

}
