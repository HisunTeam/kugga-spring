package com.hisun.kugga.duke.user.cv.api.recommendation;

import com.hisun.kugga.duke.user.cv.api.recommendation.dto.RecommendationPageReqDTO;
import com.hisun.kugga.duke.user.cv.api.recommendation.dto.RecommendationPageRspDTO;
import com.hisun.kugga.duke.user.cv.service.recommendation.RecommendationService;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: zhou_xiong
 */
@Service
public class RecommendationApiImpl implements RecommendationApi {
    @Resource
    private RecommendationService recommendationService;

    @Override
    public PageResult<RecommendationPageRspDTO> pageQueryByLeagueId(RecommendationPageReqDTO recommendationPageReqDTO) {
        return null;
    }
}
