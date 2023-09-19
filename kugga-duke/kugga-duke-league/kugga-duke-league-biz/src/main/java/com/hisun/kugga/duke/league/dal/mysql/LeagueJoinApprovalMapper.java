package com.hisun.kugga.duke.league.dal.mysql;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueJoinApprovalDO;
import com.hisun.kugga.duke.league.vo.joinLeague.LeagueJoinApprovalPageReqVO;
import com.hisun.kugga.duke.league.vo.joinLeague.LeagueJoinApprovalRespVO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 加入公会审批 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LeagueJoinApprovalMapper extends BaseMapperX<LeagueJoinApprovalDO> {


    /**
     * 审批记录
     *
     * @param pageParam
     * @param pageVO
     * @param adminLeagueIds
     * @return
     */
    Page<LeagueJoinApprovalRespVO> selectApprovalPage(@Param("pageParam") Page<LeagueJoinApprovalRespVO> pageParam,
                                                      @Param("pageVO") LeagueJoinApprovalPageReqVO pageVO,
                                                      @Param("adminLeagueIds") List<Long> adminLeagueIds);

    /**
     * 查询当前公会的审批记录
     *
     * @param leagueId
     * @return
     */
    List<LeagueJoinApprovalDO> selectApprovalByLeagueId(@Param("leagueId") Long leagueId, @Param("now") LocalDateTime now);

    /**
     * 获取未审批数
     *
     * @param adminLeagueIds
     * @param now
     * @return
     */
    List<LeagueJoinApprovalDO> selectListUnApproval(@Param("adminLeagueIds") List<Long> adminLeagueIds, @Param("now") LocalDateTime now);
}
