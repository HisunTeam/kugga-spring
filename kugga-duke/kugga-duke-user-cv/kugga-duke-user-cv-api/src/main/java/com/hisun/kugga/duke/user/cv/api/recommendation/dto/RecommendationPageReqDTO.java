package com.hisun.kugga.duke.user.cv.api.recommendation.dto;

import com.hisun.kugga.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class RecommendationPageReqDTO extends PageParam {
    /**
     * 公会ID
     */
    private Long leagueId;
}
