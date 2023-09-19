package com.hisun.kugga.duke.user.cv.api.recommendation;

import com.hisun.kugga.duke.user.cv.api.recommendation.dto.RecommendationPageReqDTO;
import com.hisun.kugga.duke.user.cv.api.recommendation.dto.RecommendationPageRspDTO;
import com.hisun.kugga.framework.common.pojo.PageResult;

/**
 * @author: zhou_xiong
 */
public interface RecommendationApi {
    /**
     * 公会详情页-推荐报告分页展示
     *
     * @param recommendationPageReqDTO
     * @return
     */
    PageResult<RecommendationPageRspDTO> pageQueryByLeagueId(RecommendationPageReqDTO recommendationPageReqDTO);
}
