package com.hisun.kugga.duke.league.vo.subscribe;

import com.hisun.kugga.duke.league.LeagueSubscribeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/10/19 15:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeVo {
    private Long userId;
    private Long leagueId;
    private LeagueSubscribeType subscribeType;
    private BigDecimal amount;
}
