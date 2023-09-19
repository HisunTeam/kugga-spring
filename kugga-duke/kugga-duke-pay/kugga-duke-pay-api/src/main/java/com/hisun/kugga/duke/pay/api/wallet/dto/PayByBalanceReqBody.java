package com.hisun.kugga.duke.pay.api.wallet.dto;

import com.hisun.kugga.framework.common.wallet.WalletBaseReqBody;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author: zhou_xiong
 */
@Data
public class PayByBalanceReqBody extends WalletBaseReqBody {
    /**
     * 应用订单号
     */
    private String appOrderNo;
    /**
     * 钱包订单号
     */
    @NotEmpty(message = "orderNo cannot be empty")
    private String orderNo;
}
