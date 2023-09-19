package com.hisun.kugga.duke.user.cv.service.recommendation;

import com.hisun.kugga.duke.user.cv.controller.app.recommendation.vo.*;
import com.hisun.kugga.framework.common.pojo.PageResult;

import javax.validation.Valid;

/**
 * 推荐报告 Service 接口
 *
 * @author 芋道源码
 */
public interface RecommendationService {

    /**
     * 创建推荐报告
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    void noticeCreateRecommendation(@Valid NoticeRecommendationCreateReqVO createReqVO);

    /**
     * 根据公会id分页查询推荐列表
     *
     * @param leagueRecommendationPageReqVO
     * @return
     */
    PageResult<LeagueRecommendationPageRspVO> pageQueryByLeagueId(LeagueRecommendationPageReqVO leagueRecommendationPageReqVO);

    /**
     * 个人中心-我的推荐报告
     *
     * @param myRecommendationPageReqVO
     * @return
     */
    PageResult<MyRecommendationPageRspVO> pageMyRecommendation(MyRecommendationPageReqVO myRecommendationPageReqVO);

    /**
     * 公会成员-推荐报告分页列表
     *
     * @param memberRecommendationPageReqVO
     * @return
     */
    PageResult<MemberRecommendationPageRspVO> pageMemberRecommendation(MemberRecommendationPageReqVO memberRecommendationPageReqVO);

    /**
     * 同一个公会创建推荐报告免费
     *
     * @param createReqVO
     */
    void leagueCreateRecommendation(MemberRecommendationCreateReqVO createReqVO);
}
