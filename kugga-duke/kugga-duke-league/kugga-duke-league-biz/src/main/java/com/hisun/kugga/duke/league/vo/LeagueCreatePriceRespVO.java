package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zuocheng
 */
@ApiModel("公会创建价格  Response VO")
@Data
public class LeagueCreatePriceRespVO {
    @ApiModelProperty(value = "公会创建价格", example = "100.00")
    private BigDecimal price;
}
