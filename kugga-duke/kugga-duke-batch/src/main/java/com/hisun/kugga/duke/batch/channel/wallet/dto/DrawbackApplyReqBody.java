package com.hisun.kugga.duke.batch.channel.wallet.dto;

import com.hisun.kugga.framework.common.wallet.WalletBaseReqBody;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: zhou_xiong
 */
@Data
public class DrawbackApplyReqBody extends WalletBaseReqBody {
    /**
     * Wallet account
     */
    @NotEmpty(message = "account cannot be empty")
    private String account;
    /**
     * Callback notification URL
     */
    @NotEmpty(message = "callbackUrl cannot be empty")
    private String callbackUrl;
    /**
     * Refund amount
     */
    @NotNull(message = "drawbackAmount cannot be empty")
    private Integer drawbackAmount;
    /**
     * Order type, currently only supports refund for consumption, i.e., 'pay' type
     */
    @NotEmpty(message = "orderType cannot be empty")
    private String orderType;
    /**
     * Source order number
     */
    @NotEmpty(message = "originalOrderNo cannot be empty")
    private String originalOrderNo;
}
