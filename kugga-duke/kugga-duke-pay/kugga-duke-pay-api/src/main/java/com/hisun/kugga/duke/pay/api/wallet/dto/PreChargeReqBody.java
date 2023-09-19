package com.hisun.kugga.duke.pay.api.wallet.dto;

import com.hisun.kugga.framework.common.wallet.WalletBaseReqBody;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: zhou_xiong
 */
@Data
public class PreChargeReqBody extends WalletBaseReqBody {
    @NotEmpty(message = "account cannot be empty")
    private String account;
    @NotNull(message = "amount cannot be empty")
    private Integer amount;
    private String appOrderNo;
    @NotEmpty(message = "returnUrl cannot be empty")
    private String returnUrl;
    private String cancelUrl;
}
