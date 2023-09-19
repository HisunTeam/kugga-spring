package com.hisun.kugga.duke.user.cv.dal.mysql.recommendation;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.user.cv.controller.app.recommendation.vo.*;
import com.hisun.kugga.duke.user.cv.dal.dataobject.recommendation.RecommendationDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 推荐报告 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DukeRecommendationMapper extends BaseMapperX<RecommendationDO> {

    default PageResult<RecommendationDO> selectPage(RecommendationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RecommendationDO>()
                .eqIfPresent(RecommendationDO::getRecommenderId, reqVO.getRecommenderId())
                .eqIfPresent(RecommendationDO::getRecommendedId, reqVO.getRecommendedId())
                .betweenIfPresent(RecommendationDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(RecommendationDO::getId));
    }

    default List<RecommendationDO> selectList(RecommendationExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RecommendationDO>()
                .eqIfPresent(RecommendationDO::getRecommenderId, reqVO.getRecommenderId())
                .eqIfPresent(RecommendationDO::getRecommendedId, reqVO.getRecommendedId())
                .betweenIfPresent(RecommendationDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(RecommendationDO::getId));
    }

    IPage<LeagueRecommendationPageRspVO> pageLeagueList(IPage<LeagueRecommendationPageRspVO> page,
                                                        @Param("leagueId") Long leagueId,
                                                        @Param("loginUserId") Long loginUserId);

    IPage<MyRecommendationPageRspVO> pageMyList(Page<MyRecommendationPageRspVO> page, @Param("userId") Long userId);

    MemberRecommendationPageRspVO selectTopInfo(@Param("recommendationId") Long recommendationId,
                                                @Param("loginUserId") Long loginUserId);

    IPage<MemberRecommendationPageRspVO> pageMemberList(Page<MemberRecommendationPageRspVO> page,
                                                        @Param("reqVO") MemberRecommendationPageReqVO memberRecommendationPageReqVO,
                                                        @Param("loginUserId") Long loginUserId);


    IPage<MemberRecommendationPageRspVO> pageMemberListWithTop(Page<MemberRecommendationPageRspVO> page,
                                                               @Param("reqVO") MemberRecommendationPageReqVO memberRecommendationPageReqVO,
                                                               @Param("loginUserId") Long loginUserId);
}
