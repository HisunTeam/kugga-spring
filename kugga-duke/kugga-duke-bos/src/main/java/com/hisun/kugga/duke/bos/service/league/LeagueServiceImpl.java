package com.hisun.kugga.duke.bos.service.league;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.bos.controller.admin.league.vo.*;
import com.hisun.kugga.duke.bos.dal.dataobject.league.LeagueDO;
import com.hisun.kugga.duke.bos.dal.mysql.league.LeagueLabelMapper;
import com.hisun.kugga.duke.bos.dal.mysql.league.LeagueMapper;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 公会 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class LeagueServiceImpl implements LeagueService {

    @Resource
    private LeagueMapper leagueMapper;
    @Resource
    private LeagueLabelMapper leagueLabelMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateLeague(LeagueRecommendsUpdateVO updateReqVO) {
        // 校验存在
        this.validateLeagueExists(updateReqVO.getLeagueId());
        LeagueDO updateObj = new LeagueDO()
                .setId(updateReqVO.getLeagueId())
                .setSortId(updateReqVO.getSortId())
                .setLeagueLabel(updateReqVO.getLeagueLabel())
                .setUpdateTime(LocalDateTime.now());
        //重置已有排序
        leagueMapper.resetSort(updateObj);
        // 更新排序
        leagueMapper.updateById(updateObj);
    }

    @Override
    public void updateLeagueLabel(LeagueRecommendsUpdateVO updateReqVO) {
        // 校验存在
        this.validateLeagueExists(updateReqVO.getLeagueId());
        // 更新
        LeagueDO updateObj = new LeagueDO()
                .setId(updateReqVO.getLeagueId())
                .setLeagueLabel(updateReqVO.getLeagueLabel());
        leagueMapper.updateById(updateObj);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateLeagueLabelBatch(LeagueUpdateBatchVO updateReqVOs) {
        updateReqVOs.getMultipleSelection().forEach(this::updateLeagueLabel);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateLeagueBatch(LeagueUpdateBatchVO updateReqVOs) {
        updateReqVOs.getMultipleSelection().forEach(this::updateLeague);
    }


    private void validateLeagueExists(Long id) {
        if (leagueMapper.selectById(id) == null) {
            throw exception(BusinessErrorCodeConstants.LEAGUE_NAME_NOT_EXISTS);
        }
    }

    @Override
    public PageResult<LeagueRecommendsVO> recommendLeagues(LeagueRecommendsReqVO reqVO) {
        Page page = new Page<>(reqVO.getPageNo(),
                reqVO.getPageSize());
        IPage<LeagueRecommendsVO> pageLeagues = leagueMapper.pageRecommends(page, reqVO.getLeagueLabel(), reqVO.getLeagueName());
        return new PageResult<>(pageLeagues.getRecords(), pageLeagues.getTotal());
    }

    @Override
    public PageResult<LeagueRecommendsVO> pageLeagueDisplay(LeagueRecommendsReqVO reqVO) {
        Page page = new Page<>(reqVO.getPageNo(),
                reqVO.getPageSize());
        IPage<LeagueRecommendsVO> pageLeagues = leagueMapper.pageLeagueDisplay(page, reqVO.getLeagueLabel(),
                reqVO.getLeagueName(), reqVO.getAuthFlag());
        return new PageResult<>(pageLeagues.getRecords(), pageLeagues.getTotal());
    }

    @Override
    public List<LeagueLabelVO> getLeagueLabels() {
        return leagueLabelMapper.getLeagueLabels();
    }
}
