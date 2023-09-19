package com.hisun.kugga.duke.batch.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
@Data
public class RefundReqDTO {
    /**
     * 内部订单号
     */
    @NotEmpty(message = "appOrderNo cannot be empty")
    private String appOrderNo;
    /**
     * 退款金额不能为空
     */
    @NotNull(message = "refundAmount cannot be empty")
    private BigDecimal refundAmount;
}
