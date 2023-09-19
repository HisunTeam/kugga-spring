package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zuocheng
 */
@ApiModel("创建公会预下单 Response VO")
@Data
public class CreatePreOrderRespVO {
    @ApiModelProperty(value = "内部订单号")
    private String appOrderNo;

    @ApiModelProperty(value = "uuid")
    private String uuid;

    /**
     * 手续费 单位：元
     */
    @ApiModelProperty(value = "手续费 单位:元")
    private BigDecimal fee;

    @ApiModelProperty(value = "公会编号", example = "001")
    private Long leagueId;
}
