package com.hisun.kugga.duke.league.service;

import com.hisun.kugga.duke.league.vo.joinLeague.*;
import com.hisun.kugga.framework.common.pojo.PageResult;

/**
 * 公会加入 Service 接口
 *
 * @author 芋道源码
 */
public interface LeagueJoinService {

    /**
     * 加入公会 下单
     *
     * @param vo
     * @return
     */
    LeagueJoinInitRespVO joinLeagueInit(LeagueJoinInitReqVO vo);

    /**
     * 加入公会支付
     *
     * @param vo
     * @return
     */
    void joinLeaguePay(LeagueJoinPayReqVO vo);

    /**
     * 我加入公会的申请记录
     *
     * @param pageVO
     * @return
     */
    PageResult<LeagueJoinApprovalRespVO> getLeagueJoinPage(LeagueJoinApprovalPageReqVO pageVO);

    /**
     * 测试Lock4j
     * @param vo
     */
    void lockTest(LeagueJoinInitReqVO vo);
}
