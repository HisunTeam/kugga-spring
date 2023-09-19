package com.hisun.kugga.duke.league.service;

import com.hisun.kugga.duke.league.vo.joinLeague.LeagueJoinApprovalPageReqVO;
import com.hisun.kugga.duke.league.vo.joinLeague.LeagueJoinApprovalRespVO;
import com.hisun.kugga.duke.league.vo.joinLeague.LeagueJoinApprovalUpdateReqVO;
import com.hisun.kugga.framework.common.pojo.PageResult;

/**
 * 加入公会审批 Service 接口
 *
 * @author 芋道源码
 */
public interface LeagueJoinApprovalService {

    /**
     * 更新审批状态
     *
     * @param reqVO
     */
    void updateLeagueJoinApproval(LeagueJoinApprovalUpdateReqVO reqVO);

    /**
     * 获取分页审批列表
     *
     * @param pageVO
     * @return
     */
    PageResult<LeagueJoinApprovalRespVO> getLeagueJoinApprovalPage(LeagueJoinApprovalPageReqVO pageVO);

    /**
     * 查询一个公会未审批记录
     *
     * @return
     */
    Long getApprovalCount();
}
