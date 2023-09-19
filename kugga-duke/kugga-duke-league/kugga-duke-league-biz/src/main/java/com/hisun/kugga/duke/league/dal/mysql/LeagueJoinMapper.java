package com.hisun.kugga.duke.league.dal.mysql;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueJoinDO;
import com.hisun.kugga.duke.league.vo.joinLeague.LeagueJoinApprovalPageReqVO;
import com.hisun.kugga.duke.league.vo.joinLeague.LeagueJoinApprovalRespVO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * 公会加入 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LeagueJoinMapper extends BaseMapperX<LeagueJoinDO> {

    /**
     * 我加入公会的申请记录
     *
     * @param pageParam
     * @param pageVO
     * @return
     */
    Page<LeagueJoinApprovalRespVO> selectJoinLeaguePage(@Param("pageParam") Page<LeagueJoinApprovalRespVO> pageParam,
                                                        @Param("pageVO") LeagueJoinApprovalPageReqVO pageVO);


    /**
     * 查询当前是否存在有效申请记录 ,只有支付了才算有效的
     *
     * @param userId
     * @param status
     * @param now
     * @return
     */
    LeagueJoinDO getCurrentValidApproval(@Param("userId") Long userId,
                                         @Param("leagueId") Long leagueId,
                                         @Param("status") int status,
                                         @Param("now") LocalDateTime now);


}
