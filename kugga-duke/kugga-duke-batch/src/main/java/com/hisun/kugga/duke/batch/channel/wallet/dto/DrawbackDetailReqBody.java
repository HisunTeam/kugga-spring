package com.hisun.kugga.duke.batch.channel.wallet.dto;

import com.hisun.kugga.framework.common.wallet.WalletBaseReqBody;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author: zhou_xiong
 */
@Data
public class DrawbackDetailReqBody extends WalletBaseReqBody {
    /**
     * 钱包的退款订单号
     */
    @NotEmpty(message = "orderNo cannot be empty")
    private String orderNo;
}
