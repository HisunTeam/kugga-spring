package com.hisun.kugga.duke.pay.api.wallet.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
@Data
public class AppDetailRspBody {
    private BigDecimal chargeFeeFixed;
    private BigDecimal chargeFeeMax;
    private BigDecimal chargeFeeMin;
    private BigDecimal chargeFeeRate;

    private BigDecimal consumeFeeFixed;
    private BigDecimal consumeFeeMax;
    private BigDecimal consumeFeeMin;
    private BigDecimal consumeFeeRate;

    private BigDecimal drawbackFeeFixed;
    private BigDecimal drawbackFeeMax;
    private BigDecimal drawbackFeeMin;
    private BigDecimal drawbackFeeRate;

    private BigDecimal payoutFeeFixed;
    private BigDecimal payoutFeeMax;
    private BigDecimal payoutFeeMin;
    private BigDecimal payoutFeeRate;
}
