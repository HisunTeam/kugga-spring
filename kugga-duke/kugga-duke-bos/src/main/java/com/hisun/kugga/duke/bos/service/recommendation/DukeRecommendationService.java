package com.hisun.kugga.duke.bos.service.recommendation;


import com.hisun.kugga.duke.bos.controller.admin.recommendation.vo.RecommendationReqVO;
import com.hisun.kugga.duke.bos.controller.admin.recommendation.vo.RecommendationRespVO;
import com.hisun.kugga.framework.common.pojo.PageResult;

/**
 * 推荐报告 Service 接口
 *
 * @author 芋道源码
 */
public interface DukeRecommendationService {
    /**
     * 查询用户编写推荐报告记录
     *
     * @param reqVO
     */
    public PageResult<RecommendationRespVO> pageRecommendation(RecommendationReqVO reqVO);

}
