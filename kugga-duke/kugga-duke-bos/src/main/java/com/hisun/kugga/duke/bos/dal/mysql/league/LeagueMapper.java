package com.hisun.kugga.duke.bos.dal.mysql.league;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.bos.controller.admin.league.vo.LeagueRecommendsVO;
import com.hisun.kugga.duke.bos.dal.dataobject.league.LeagueDO;
import com.hisun.kugga.duke.bos.enums.LeagueSortEnum;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;


/**
 * 公会 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LeagueMapper extends BaseMapperX<LeagueDO> {

    /**
     * 分页查询公会推荐列表
     *
     * @param page
     * @param leagueLabel
     * @return
     */
    default int resetSort(LeagueDO reqVO) {
        return update(null, new LambdaUpdateWrapper<LeagueDO>()
                .eq(LeagueDO::getSortId, reqVO.getSortId())
                .eq(LeagueDO::getLeagueLabel, reqVO.getLeagueLabel())
                .set(LeagueDO::getUpdater, LocalDateTime.now())
                .set(LeagueDO::getSortId, LeagueSortEnum.no_sorted.getCode())
        );
    }

    /**
     * 分页查询公会推荐列表
     *
     * @param page
     * @param leagueLabel
     * @return
     */
    IPage<LeagueRecommendsVO> pageRecommends(Page page, @Param("leagueLabel") String leagueLabel, @Param("leagueName") String leagueName);

    /**
     * 分页查询公会推荐列表
     *
     * @param page
     * @param leagueLabel
     * @param authFlag
     * @return
     */
    IPage<LeagueRecommendsVO> pageLeagueDisplay(Page page, @Param("leagueLabel") String leagueLabel, @Param("leagueName") String leagueName, @Param("authFlag") Boolean authFlag);

}
