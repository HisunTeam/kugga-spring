package com.hisun.kugga.duke.pay.api.wallet.dto;

import com.hisun.kugga.framework.common.wallet.WalletBaseReqBody;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author: zhou_xiong
 */
@Data
public class AccountDetailReqBody extends WalletBaseReqBody {
    /**
     * 账号
     */
    @NotEmpty(message = "account cannot be empty")
    private String account;
}
