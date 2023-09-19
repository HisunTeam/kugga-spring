package com.hisun.kugga.duke.league.vo.joinLeague;

import com.hisun.kugga.duke.league.LeagueSubscribeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/13 17:18
 */
@Data
public class LeagueJoinInitReqVO {
    @ApiModelProperty(value = "公会ID", required = true)
    private Long leagueId;

    private Long userId;

    @ApiModelProperty(value = "订阅类型", required = true)
    LeagueSubscribeType subscribeType;

    @ApiModelProperty(value = "金额", required = false)
    private BigDecimal amount;

    @ApiModelProperty(value = "加入理由", required = false)
    private String joinReason;
}
