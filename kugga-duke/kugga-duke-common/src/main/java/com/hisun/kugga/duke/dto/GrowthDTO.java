package com.hisun.kugga.duke.dto;

import com.hisun.kugga.duke.enums.GrowthType;
import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class GrowthDTO {
    private Long leagueId;
    private Long userId;
    private GrowthType growthType;
    private Integer growthValue;

    public GrowthDTO(Long leagueId, Long userId) {
        this.leagueId = leagueId;
        this.userId = userId;
    }
}
