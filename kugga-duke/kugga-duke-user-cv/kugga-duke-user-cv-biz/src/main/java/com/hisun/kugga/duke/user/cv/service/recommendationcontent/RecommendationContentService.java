package com.hisun.kugga.duke.user.cv.service.recommendationcontent;

import com.hisun.kugga.duke.entity.Content;
import com.hisun.kugga.duke.user.cv.dal.dataobject.recommendationcontent.RecommendationContentDO;

import java.util.List;

/**
 * 推荐报告内容 Service 接口
 *
 * @author 芋道源码
 */
public interface RecommendationContentService {
    /**
     * 批量保存
     *
     * @param recommendationId
     * @param content
     */
    void batchInsert(Long recommendationId, List<Content> content);

    /**
     * 通过recommendationId查询列表
     *
     * @param recommendationId
     * @return
     */
    List<RecommendationContentDO> getByRecommendationId(Long recommendationId);
}
