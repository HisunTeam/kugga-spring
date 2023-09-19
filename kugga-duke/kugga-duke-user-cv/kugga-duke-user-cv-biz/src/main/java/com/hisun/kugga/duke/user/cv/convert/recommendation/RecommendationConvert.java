package com.hisun.kugga.duke.user.cv.convert.recommendation;

import com.hisun.kugga.duke.user.cv.controller.app.recommendation.vo.*;
import com.hisun.kugga.duke.user.cv.dal.dataobject.recommendation.RecommendationDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
 * 推荐报告 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface RecommendationConvert {

    RecommendationConvert INSTANCE = Mappers.getMapper(RecommendationConvert.class);

    //RecommendationDO convert(NoticeRecommendationCreateReqVO bean);

    RecommendationDO convert(RecommendationUpdateReqVO bean);

    RecommendationRespVO convert(RecommendationDO bean);

    //RecommendationDO convert(MemberRecommendationCreateReqVO bean);

    List<RecommendationRespVO> convertList(List<RecommendationDO> list);

    PageResult<RecommendationRespVO> convertPage(PageResult<RecommendationDO> page);

    List<RecommendationExcelVO> convertList02(List<RecommendationDO> list);

    List<MemberRecommendationPageRspVO> convertList03(List<MyRecommendationPageRspVO> list);

}
