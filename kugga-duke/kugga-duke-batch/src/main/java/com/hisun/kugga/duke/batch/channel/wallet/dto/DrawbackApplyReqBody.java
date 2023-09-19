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
     * 钱包账号
     */
    @NotEmpty(message = "account cannot be empty")
    private String account;
    /**
     * 回调通知地址
     */
    @NotEmpty(message = "callbackUrl cannot be empty")
    private String callbackUrl;
    /**
     * 退款金额
     */
    @NotNull(message = "drawbackAmount cannot be empty")
    private Integer drawbackAmount;
    /**
     * 订单类型, 目前仅支持消费退款, 即pay类型
     */
    @NotEmpty(message = "orderType cannot be empty")
    private String orderType;
    /**
     * 源订单号
     */
    @NotEmpty(message = "originalOrderNo cannot be empty")
    private String originalOrderNo;
}
