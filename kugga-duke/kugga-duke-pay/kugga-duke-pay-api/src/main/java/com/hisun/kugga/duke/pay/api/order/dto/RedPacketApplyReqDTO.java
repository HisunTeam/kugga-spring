package com.hisun.kugga.duke.pay.api.order.dto;

import com.hisun.kugga.duke.enums.AccountType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: zhou_xiong
 */
@Data
public class RedPacketApplyReqDTO {
    /**
     * 付款方id
     */
    @NotNull(message = "payerId cannot be empty")
    private Long payerId;
    /**
     * 付款方类型
     */
    @NotNull(message = "accountType cannot be empty")
    private AccountType accountType;
    /**
     * 红包接受方列表
     */
    @NotEmpty(message = "receiverInfoList cannot be empty")
    private List<RedPacketReceiverInfo> receiverInfoList;
}
