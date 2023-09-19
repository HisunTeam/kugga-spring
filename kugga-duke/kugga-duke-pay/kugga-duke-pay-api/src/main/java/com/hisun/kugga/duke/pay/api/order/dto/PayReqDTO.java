package com.hisun.kugga.duke.pay.api.order.dto;

import com.hisun.kugga.duke.enums.PayChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author: zhou_xiong
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayReqDTO {
    /**
     * 内部订单号
     */
    @NotEmpty(message = "appOrderNo cannot be empty")
    private String appOrderNo;
    /**
     * 支付方式 目前只支持余额支付
     */
    //@NotNull(message = "payChannel cannot be empty")
    private PayChannel payChannel;
}
