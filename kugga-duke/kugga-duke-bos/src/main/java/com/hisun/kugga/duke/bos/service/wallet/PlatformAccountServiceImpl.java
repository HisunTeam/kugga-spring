package com.hisun.kugga.duke.bos.service.wallet;


import com.hisun.kugga.duke.bos.controller.admin.wallet.WalletClient;
import com.hisun.kugga.duke.bos.controller.admin.wallet.dto.AccountDetailReqBody;
import com.hisun.kugga.duke.bos.controller.admin.wallet.dto.AccountDetailRspBody;
import com.hisun.kugga.duke.bos.dal.dataobject.platformaccount.PlatformAccountDO;
import com.hisun.kugga.framework.common.util.amount.AmountUtil;
import com.hisun.kugga.module.duke.dal.mysql.platformaccount.PlatformAccountMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author: lzt
 */
@Service
@Validated
public class PlatformAccountServiceImpl implements PlatformAccountService {

    @Resource
    private WalletClient walletClient;
    @Resource
    private PlatformAccountMapper platformAccountMapper;

    public BigDecimal selectAccountDetail() {
        PlatformAccountDO platformAccountDO = platformAccountMapper.selectAccountId();
        AccountDetailRspBody accountDetailRspBody = walletClient.accountDetail(new AccountDetailReqBody().setAccount(platformAccountDO.getAccountId()));
        //分转元
        BigDecimal balance = AmountUtil.fenToYuan(accountDetailRspBody.getBalance());
        return balance;
    }
}
