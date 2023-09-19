package com.hisun.kugga.duke.pay.api.wallet.dto;

import com.hisun.kugga.framework.common.wallet.WalletBaseReqBody;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: zhou_xiong
 */
@Data
public class PrepayReqBody extends WalletBaseReqBody {
    /**
     * 用户的钱包账户
     */
    @NotEmpty(message = "account cannot be empty")
    private String account;
    /**
     * 消费金额
     */
    @NotNull(message = "amount cannot be empty")
    private Integer amount;
    /**
     * 应用订单号
     */
    @NotEmpty(message = "appOrderNo cannot be empty")
    private String appOrderNo;
    /**
     * 交易对手钱包账户
     */
    private String toAccount;
}
