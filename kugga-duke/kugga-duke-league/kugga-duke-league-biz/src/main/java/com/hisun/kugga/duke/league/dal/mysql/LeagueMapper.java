package com.hisun.kugga.duke.league.dal.mysql;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueDO;
import com.hisun.kugga.duke.league.vo.LeagueAndRuleVO;
import com.hisun.kugga.duke.league.vo.LeagueDetailsRespVO;
import com.hisun.kugga.duke.league.vo.LeagueRecommendsVO;
import com.hisun.kugga.duke.league.vo.LeagueSearchReqVO;
import com.hisun.kugga.framework.common.pojo.PageParam;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 公会 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LeagueMapper extends BaseMapperX<LeagueDO> {

    /**
     * 根据公会名称查询公会信息
     *
     * @param name
     * @return
     */
    default LeagueDO selectByLeagueName(String name) {
        return selectOne(new LambdaQueryWrapperX<LeagueDO>()
                .eq(LeagueDO::getLeagueName, name).last("limit 1")
        );
    }

    /**
     * 分页查询支持认证的公会列表
     *
     * @param page
     * @return
     */
    IPage<LeagueAndRuleVO> pageAuthLeague(Page<LeagueAndRuleVO> page);

    /**
     * 初始化公会账户表数据
     *
     * @param leagueId
     * @param amount
     * @return
     */
    int initLeagueAccount(@Param("leagueId") Long leagueId, @Param("amount") BigDecimal amount);

    /**
     * 查询公会详情
     *
     * @param leagueId
     * @param userId
     * @return
     */
    LeagueDetailsRespVO selectLeagueDetail(@Param("leagueId") Long leagueId, @Param("userId") Long userId);


//    IPage<LeagueRecommendsVO> pageRecommends(Page page, @Param("userId") Long userId);


    /**
     * 分页查询公会推荐列表
     *
     * @param page
     * @param reqVO
     * @return
     */
    IPage<LeagueRecommendsVO> pageRecommends(@Param("page") Page page, @Param("reqVO") LeagueSearchReqVO reqVO);

    /**
     * 公会搜索
     *
     * @param page
     * @param reqVO
     * @return
     */
    IPage<LeagueRecommendsVO> pageSearchRecommends(@Param("page") Page page, @Param("reqVO") LeagueSearchReqVO reqVO);

    /**
     * 根据标签查询公会列表,并指定总条数
     *
     * @param pageParam
     * @param label
     * @return
     */
    default PageResult<LeagueDO> pageByLabel(PageParam pageParam, String label) {
        return selectPage(pageParam, new LambdaQueryWrapperX<LeagueDO>()
                .eq(LeagueDO::getLeagueLabel, label)
                .orderByAsc(LeagueDO::getSortId)
                .orderByDesc(LeagueDO::getCreateTime)
        );
    }

    /**
     * 根据ID列表做批量查询
     *
     * @param ids
     * @return
     */
    default List<LeagueDO> selectInId(List<Long> ids) {
        return selectList(new LambdaQueryWrapperX<LeagueDO>().in(LeagueDO::getId, ids));
    }

    /**
     * 查询用户所有加入公会的id
     * @param userId
     * @return
     */
    List<Long> selectUserAllJoinLeagueId(@Param("userId") Long userId);
}
