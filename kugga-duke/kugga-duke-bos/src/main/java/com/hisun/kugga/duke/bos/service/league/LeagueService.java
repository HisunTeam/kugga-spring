package com.hisun.kugga.duke.bos.service.league;


import com.hisun.kugga.duke.bos.controller.admin.league.vo.*;
import com.hisun.kugga.framework.common.pojo.PageResult;

import javax.validation.Valid;
import java.util.List;

/**
 * 公会 Service 接口
 *
 * @author 芋道源码
 */
public interface LeagueService {


    /**
     * 更新公会
     *
     * @param updateReqVO 更新信息
     */
    void updateLeague(@Valid LeagueRecommendsUpdateVO updateReqVO);

    /**
     * 更新公会标签
     *
     * @param updateReqVO 更新信息
     */
    void updateLeagueLabel(@Valid LeagueRecommendsUpdateVO updateReqVO);

    /**
     * 更新公会
     *
     * @param updateReqVO 批量更新信息
     */
    void updateLeagueBatch(@Valid LeagueUpdateBatchVO updateReqVO);

    /**
     * 更新公会
     *
     * @param updateReqVO 批量更新信息
     */
    void updateLeagueLabelBatch(@Valid LeagueUpdateBatchVO updateReqVO);


    /**
     * 查询推荐公会列表
     *
     * @param reqVO
     * @return
     */
    PageResult<LeagueRecommendsVO> recommendLeagues(LeagueRecommendsReqVO reqVO);

    /**
     * 查询推荐公会列表
     *
     * @param reqVO
     * @return
     */
    PageResult<LeagueRecommendsVO> pageLeagueDisplay(LeagueRecommendsReqVO reqVO);

    /**
     * 查询推荐公会标签
     *
     * @return
     */
    List<LeagueLabelVO> getLeagueLabels();

}
