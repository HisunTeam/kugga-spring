package com.hisun.kugga.duke.user.cv.service.recommendationcontent;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.entity.Content;
import com.hisun.kugga.duke.user.cv.dal.dataobject.recommendationcontent.RecommendationContentDO;
import com.hisun.kugga.duke.user.cv.dal.mysql.recommendationcontent.RecommendationContentMapper;
import com.hisun.kugga.duke.user.cv.utils.EditorUtils;
import com.hisun.kugga.framework.common.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;


/**
 * 推荐报告内容 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class RecommendationContentServiceImpl implements RecommendationContentService {

    @Resource
    private RecommendationContentMapper recommendationContentMapper;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void batchInsert(Long recommendationId, List<Content> content) {
        if (CollectionUtil.isEmpty(content)) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.CONTENT_NOT_EXISTS);
        }
        content.stream().forEach(ctn -> {
            RecommendationContentDO recommendationContentDO = new RecommendationContentDO();
            recommendationContentDO.setRecommendationId(recommendationId);
            recommendationContentDO.setContent(JSONUtil.toJsonStr(ctn));
            recommendationContentDO.setOriginalText(EditorUtils.parseContent(ctn));
            recommendationContentMapper.insert(recommendationContentDO);
        });
    }

    @Override
    public List<RecommendationContentDO> getByRecommendationId(Long recommendationId) {
        return recommendationContentMapper.selectList(new LambdaQueryWrapper<RecommendationContentDO>()
                .select(RecommendationContentDO::getId, RecommendationContentDO::getContent)
                .eq(RecommendationContentDO::getRecommendationId, recommendationId)
                .orderByAsc(RecommendationContentDO::getId));
    }
}
