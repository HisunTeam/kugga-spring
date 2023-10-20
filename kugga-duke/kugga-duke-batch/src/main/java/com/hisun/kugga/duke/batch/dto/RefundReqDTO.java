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
     * Internal Order Number
     */
    @NotEmpty(message = "appOrderNo cannot be empty")
    private String appOrderNo;
    /**
     * Refund amount cannot be empty
     */
    @NotNull(message = "refundAmount cannot be empty")
    private BigDecimal refundAmount;
}
