package com.hisun.kugga.duke.league.vo.joinLeague;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/13 17:36
 */
@Data
public class LeagueJoinInitRespVO {

    @ApiModelProperty(value = "内部订单号")
    private String appOrderNo;

    @ApiModelProperty(value = "uuid")
    private String uuid;

    /**
     * 手续费 单位：元
     */
    @ApiModelProperty(value = "手续费 单位:元")
    private BigDecimal fee;
}
