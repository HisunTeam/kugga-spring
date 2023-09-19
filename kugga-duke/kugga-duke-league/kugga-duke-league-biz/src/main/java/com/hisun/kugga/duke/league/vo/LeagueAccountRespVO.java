package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zuocheng
 */
@ApiModel("公会账户额度  Response VO")
@Data
public class LeagueAccountRespVO {
    @ApiModelProperty(value = "公会账户额度", example = "100")
    private BigDecimal account;
}
