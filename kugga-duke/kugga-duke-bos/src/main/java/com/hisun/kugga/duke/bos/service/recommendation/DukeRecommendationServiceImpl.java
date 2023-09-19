package com.hisun.kugga.duke.bos.service.recommendation;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.bos.controller.admin.recommendation.vo.RecommendationReqVO;
import com.hisun.kugga.duke.bos.controller.admin.recommendation.vo.RecommendationRespVO;
import com.hisun.kugga.duke.bos.dal.mysql.recommendation.DukeRecommendationMapper;
import com.hisun.kugga.duke.bos.service.serialpassword.SerialPasswordService;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;


/**
 * 推荐报告 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class DukeRecommendationServiceImpl implements DukeRecommendationService {

    @Resource
    private DukeRecommendationMapper recommendationMapper;
    @Resource
    private SerialPasswordService serialPasswordService;

    @Override
    public PageResult<RecommendationRespVO> pageRecommendation(RecommendationReqVO reqVO) {
        if (!serialPasswordService.judgePasswordEffective()) {
            throw ServiceExceptionUtil.exception(BusinessErrorCodeConstants.DB_GET_FAILED);
        }
        Page page = new Page<>(reqVO.getPageNo(),
                reqVO.getPageSize());
        IPage<RecommendationRespVO> pageLeagues = recommendationMapper.pageRecommendation(page, reqVO.getFirstName(), reqVO.getLastName(),
                reqVO.getEmail());
        return new PageResult<>(pageLeagues.getRecords(), pageLeagues.getTotal());
    }

}
