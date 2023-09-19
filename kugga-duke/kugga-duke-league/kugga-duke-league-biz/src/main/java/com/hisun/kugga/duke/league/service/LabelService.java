package com.hisun.kugga.duke.league.service;

import com.hisun.kugga.duke.league.vo.*;
import com.hisun.kugga.framework.common.pojo.PageResult;

import java.util.List;

/**
 * @author zuocheng
 * 公会标签服务
 */
public interface LabelService {
    /**
     * 分页查询标签列表信息（只返回值 与 名称）
     *
     * @return
     */
    List<SimpleLabelsVO> simpleLabels();

    /**
     * 标签推荐公会列表
     *
     * @return
     */
    List<LabelBestLeaguesVO> labelBestLeagues();

    /**
     * 查询单个标签详情
     *
     * @param labelValue
     * @return
     */
    LeagueLabelVO singleLabel(String labelValue);


    /**
     * 分页查询标签公会列表
     *
     * @param reqVO
     * @return
     */
    PageResult<LeagueVO> labelLeagues(PageLabelLeaguesReqVO reqVO);
}
