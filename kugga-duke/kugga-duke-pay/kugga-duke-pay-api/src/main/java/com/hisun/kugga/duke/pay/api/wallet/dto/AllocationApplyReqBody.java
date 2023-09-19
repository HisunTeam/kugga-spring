package com.hisun.kugga.duke.pay.api.wallet.dto;

import com.hisun.kugga.framework.common.wallet.WalletBaseReqBody;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author: zhou_xiong
 */
@Data
public class AllocationApplyReqBody extends WalletBaseReqBody {
    private String callbackUrl;
    /**
     * 钱包订单号
     */
    @NotEmpty(message = "orderNo cannot be empty")
    private String orderNo;

    /**
     * 分账接收方列表
     */
    @NotEmpty(message = "receiverList cannot be empty")
    private List<Receiver> receiverList;
}
