package com.hisun.kugga.duke.pay.api.wallet.dto;

import com.hisun.kugga.framework.common.wallet.WalletBaseReqBody;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: zhou_xiong
 */
@Data
public class RedPacketApplyReqBody extends WalletBaseReqBody {
    /**
     * 发起账户
     */
    @NotEmpty(message = "account can not be empty")
    private String account;
    /**
     * 红包金额
     */
    @NotNull(message = "balance can not be empty")
    private Integer balance;
    /**
     * 回调通知地址
     */
    @NotEmpty(message = "callbackUrl can not be empty")
    private String callbackUrl;
    /**
     * 红包接受方列表
     */
    @NotEmpty(message = "receiverList can not be empty")
    private List<RedPacketReceiver> receiverList;
    /**
     * 红包个数
     */
    @NotNull(message = "redPacketCnt can not be empty")
    private Integer redPacketCnt;
}
