package com.hisun.kugga.duke.pay.controller.app.testAccount;

import com.hisun.kugga.duke.enums.AccountType;
import lombok.Data;

/**
 * 测试类 生产需要del
 */
@Data
public class AccountDetailVO {
    private String walletAccountId;

    private AccountType accountType;
}
