package com.hisun.kugga.duke.league.service;

import com.hisun.kugga.duke.league.dal.dataobject.LeagueDO;
import com.hisun.kugga.duke.league.vo.*;
import com.hisun.kugga.framework.common.pojo.PageParam;
import com.hisun.kugga.framework.common.pojo.PageResult;

import java.util.List;

/**
 * @author zuocheng
 */
public interface LeagueService {
    /**
     * 创建公会上传图片
     */
    String createUploadAvatar(byte[] bytes, String originalFilename, String contentType);

    /**
     * 创建公会预下单
     *
     * @return
     */
    CreatePreOrderRespVO createPreOrder(CreatePreOrderReqVO reqVO);

    /**
     * 公会创建
     *
     * @param reqVO
     * @return
     */
    LeagueCreateRespVO create(LeagueCreateReqVO reqVO);

    /**
     * 获取公会创建价格
     *
     * @return
     */
    LeagueCreatePriceRespVO createPrice();

    /**
     * 获取公会邀请链接
     *
     * @param reqVO
     * @return
     */
    LeagueInviteUrlRespVO getInviteUrl(LeagueInviteUrlReqVO reqVO);

    /**
     * 获取邀请公会详情
     *
     * @param leagueId
     * @return
     */
    InviteDataRespVO getInviteData(String leagueId);

    /**
     * 下发邀请邮件
     *
     * @param reqVO
     */
    void inviteMail(LeagueInviteMailReqVO reqVO);

    /**
     * 加入公会
     *
     * @param reqVO
     */
    void joinLeague(JoinLeagueReqVO reqVO);

    /**
     * 查看公会详情
     *
     * @param reqVO
     * @return
     */
    LeagueDetailsRespVO details(LeagueDetailsReqVO reqVO);

    /**
     * 查询推荐公会列表
     *
     * @param reqVO
     * @return
     */
    PageResult<LeagueRecommendsVO> recommendLeagues(LeagueSearchReqVO reqVO);

    /**
     * 公会搜素
     *
     * @param reqVO
     * @return
     */
    PageResult<LeagueRecommendsVO> searchLeagues(LeagueSearchReqVO reqVO);

    /**
     * 查询具有认证权限的公会列表
     *
     * @param reqVO
     * @return
     */
    PageResult<LeagueAndRuleVO> recommendAuthLeagues(PageParam reqVO);


    /**
     * 公会成员
     *
     * @param reqVO
     * @return
     */
    PageResult<LeagueMembersVO> members(LeagueMembersPageReqVO reqVO);

    /**
     * 查询用户公会列表
     *
     * @param reqVO
     * @return
     */
    PageResult<UserLeaguesVO> userLeagues(UserLeaguesPageReqVO reqVO);

    /**
     * 根据ID查找
     *
     * @param leagueId
     * @return
     */
    LeagueDO getById(Long leagueId);

    /**
     * 查询用户是否为公会成员 true:是 false:否
     *
     * @param leagueId
     * @param userIds
     * @return
     */
    Boolean isLeagueMember(Long leagueId, Long... userIds);

    /**
     * 检查用户是否为公会管理员
     *
     * @param leagueId
     * @param userId
     * @return
     */
    Boolean isLeagueAdmin(Long leagueId, Long userId);


    /**
     * 查询公会账户额度
     *
     * @param reqVO
     * @return
     */
    LeagueAccountRespVO leagueAccount(LeagueAccountReqVO reqVO);

    /**
     * 检查邮箱对应的用户是否为公会成员
     *
     * @param reqVO
     * @return
     */
    CheckMailIsLeagueMemberRespVO checkMailIsLeagueMember(CheckMailIsLeagueMemberReqVO reqVO);

    /**
     * 查询用户所有加入公会的id
     * @param userId
     * @return
     */
    List<Long> queryUserAllJoinLeagueId(Long userId);
}
