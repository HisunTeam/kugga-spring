package com.hisun.kugga.duke.pay.api.wallet.dto;

import com.hisun.kugga.framework.common.wallet.WalletBaseReqBody;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author: zhou_xiong
 */
@Data
public class ChargeCancelReqBody extends WalletBaseReqBody {
    @NotEmpty(message = "orderNo cannot be empty")
    private String orderNo;
}
