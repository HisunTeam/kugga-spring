package com.hisun.kugga.duke.pay.api.order.dto;

import com.hisun.kugga.duke.enums.OrderType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author: zhou_xiong
 */
@Data
public class SplitAccountReqDTO {
    /**
     * 交易类型，如果不传，默认为下单时的交易类型
     */
    private OrderType orderType;
    /**
     * 内部订单号
     */
    @NotEmpty(message = "appOrderNo cannot be empty")
    private String appOrderNo;
    /**
     * 收款方信息
     */
    @NotEmpty(message = "orderSubId cannot be empty")
    private List<ReceiverInfo> receiverList;
}
