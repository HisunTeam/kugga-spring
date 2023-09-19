package com.hisun.kugga.duke.pay.api.wallet.dto;

import com.hisun.kugga.framework.common.wallet.WalletBaseReqBody;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: zhou_xiong
 */
@Data
public class WithdrawToPayPalReqBody extends WalletBaseReqBody {
    @NotEmpty(message = "account cannot be empty")
    private String account;
    @NotNull(message = "amount cannot be empty")
    private Integer amount;
    private String appOrderNo;
    private String callbackUrl;
    private String currency;
    @NotEmpty(message = "paypalAccountEmail cannot be empty")
    private String paypalAccountEmail;
}
