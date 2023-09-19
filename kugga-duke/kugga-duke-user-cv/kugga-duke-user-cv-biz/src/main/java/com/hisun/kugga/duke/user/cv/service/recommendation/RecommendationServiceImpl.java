package com.hisun.kugga.duke.user.cv.service.recommendation;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.entity.Content;
import com.hisun.kugga.duke.league.api.LeagueApi;
import com.hisun.kugga.duke.league.api.dto.task.LeagueTaskFinishDTO;
import com.hisun.kugga.duke.league.api.task.TaskApi;
import com.hisun.kugga.duke.user.cv.controller.app.recommendation.vo.*;
import com.hisun.kugga.duke.user.cv.dal.dataobject.recommendation.RecommendationDO;
import com.hisun.kugga.duke.user.cv.dal.dataobject.recommendationcontent.RecommendationContentDO;
import com.hisun.kugga.duke.user.cv.dal.mysql.recommendation.DukeRecommendationMapper;
import com.hisun.kugga.duke.user.cv.service.recommendationcontent.RecommendationContentService;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 推荐报告 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class RecommendationServiceImpl implements RecommendationService {

    @Resource
    private DukeRecommendationMapper recommendationMapper;
    @Resource
    private TaskApi taskApi;
    @Resource
    private LeagueApi leagueApi;
    @Resource
    private RecommendationContentService recommendationContentService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void noticeCreateRecommendation(NoticeRecommendationCreateReqVO createReqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        // 被推荐人与推荐人不能是同一人
        if (loginUserId.equals(createReqVO.getRecommendedId())) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.RECOMMENDATION_MYSELF_ILLEGAL);
        }
        // 插入
        RecommendationDO recommendation = new RecommendationDO();
        recommendation.setRecommendedId(createReqVO.getRecommendedId());
        recommendation.setRecommenderLeagueId(createReqVO.getRecommenderLeagueId());
        recommendation.setRecommenderId(loginUserId);
        recommendationMapper.insert(recommendation);
        // 保存推荐报告内容
        recommendationContentService.batchInsert(recommendation.getId(), createReqVO.getContent());
        // 创建任务
        LeagueTaskFinishDTO leagueTaskFinishDTO = new LeagueTaskFinishDTO();
        leagueTaskFinishDTO.setId(createReqVO.getTaskId());
        leagueTaskFinishDTO.setLeagueId(createReqVO.getRecommenderLeagueId());
        leagueTaskFinishDTO.setLeagueNoticeId(createReqVO.getLeagueNoticeId());
        leagueTaskFinishDTO.setType(LeagueTaskFinishDTO.LeagueTaskTypeEnum.TASK_TYPE_1);
        taskApi.finish(leagueTaskFinishDTO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void leagueCreateRecommendation(MemberRecommendationCreateReqVO createReqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        // 被推荐人与推荐人不能是同一人
        if (loginUserId.equals(createReqVO.getRecommendedId())) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.RECOMMENDATION_MYSELF_ILLEGAL);
        }
        // 判断当前登录人和被推荐人是不是同一公会成员
        Boolean leagueMember = leagueApi.isLeagueMember(createReqVO.getRecommenderLeagueId(), loginUserId, createReqVO.getRecommendedId());
        if (!leagueMember) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.IS_NOT_LEAGUE_MEMBER);
        }
        // 插入推荐报告主表
        RecommendationDO recommendation = new RecommendationDO();
        recommendation.setRecommenderId(loginUserId);
        recommendation.setRecommendedId(createReqVO.getRecommendedId());
        recommendation.setRecommenderLeagueId(createReqVO.getRecommenderLeagueId());
        recommendationMapper.insert(recommendation);
        // 保存推荐报告内容
        recommendationContentService.batchInsert(recommendation.getId(), createReqVO.getContent());
    }

    @Override
    public PageResult<LeagueRecommendationPageRspVO> pageQueryByLeagueId(LeagueRecommendationPageReqVO leagueRecommendationPageReqVO) {
        Page<LeagueRecommendationPageRspVO> page = new Page<>(leagueRecommendationPageReqVO.getPageNo(),
                leagueRecommendationPageReqVO.getPageSize());
        IPage<LeagueRecommendationPageRspVO> iPage = recommendationMapper.pageLeagueList(page, leagueRecommendationPageReqVO.getLeagueId(),
                SecurityFrameworkUtils.getLoginUserId());
        // 查询推荐报告内容
        setContent(iPage.getRecords());
        return new PageResult(iPage.getRecords(), iPage.getTotal());
    }

    private <T extends RecommendationPageRspBaseVO> void setContent(List<T> list) {
        list.forEach(record -> {
            List<RecommendationContentDO> contentList = recommendationContentService.getByRecommendationId(record.getRecommendationId());
            List<Content> contents = contentList.stream()
                    .map(RecommendationContentDO::getContent)
                    .map(ctn -> JSONUtil.toBean(ctn, Content.class))
                    .collect(Collectors.toList());
            record.setContent(contents);
        });
    }

    @Override
    public PageResult<MyRecommendationPageRspVO> pageMyRecommendation(MyRecommendationPageReqVO myRecommendationPageReqVO) {
        Page<MyRecommendationPageRspVO> page = new Page<>(myRecommendationPageReqVO.getPageNo(),
                myRecommendationPageReqVO.getPageSize());
        IPage<MyRecommendationPageRspVO> iPage = recommendationMapper.pageMyList(page, SecurityFrameworkUtils.getLoginUserId());
        setContent(iPage.getRecords());
        return new PageResult(iPage.getRecords(), iPage.getTotal());
    }

    @Override
    public PageResult<MemberRecommendationPageRspVO> pageMemberRecommendation(MemberRecommendationPageReqVO memberRecommendationPageReqVO) {
        // 如果是从分享链接进入，且当前分页数是1，则分享的推荐报告置顶；不是从分享链接进入则正常分页展示
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        Page<MemberRecommendationPageRspVO> page = new Page<>(memberRecommendationPageReqVO.getPageNo(),
                memberRecommendationPageReqVO.getPageSize());
        IPage<MemberRecommendationPageRspVO> iPage = ObjectUtil.isNull(memberRecommendationPageReqVO.getRecommendationId()) ?
                recommendationMapper.pageMemberList(page, memberRecommendationPageReqVO, loginUserId) :
                recommendationMapper.pageMemberListWithTop(page, memberRecommendationPageReqVO, loginUserId);
        // 查询推荐报告内容
        setContent(iPage.getRecords());
        return new PageResult(iPage.getRecords(), iPage.getTotal());
    }

}
