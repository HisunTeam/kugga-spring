package com.hisun.kugga.duke.batch.channel.wallet.dto;

import com.hisun.kugga.framework.common.wallet.WalletBaseReqBody;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author: zhou_xiong
 */
@Data
public class RedPacketDetailReqBody extends WalletBaseReqBody {
    /**
     * 钱包红包订单号
     */
    @NotEmpty(message = "orderNo can not be empty")
    private String orderNo;
}
