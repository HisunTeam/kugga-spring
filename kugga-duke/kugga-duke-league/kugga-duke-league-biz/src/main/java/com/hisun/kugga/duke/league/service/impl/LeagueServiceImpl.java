package com.hisun.kugga.duke.league.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.enums.AccountType;
import com.hisun.kugga.duke.enums.NumberEnum;
import com.hisun.kugga.duke.enums.OrderType;
import com.hisun.kugga.duke.enums.PayStatusEnum;
import com.hisun.kugga.duke.enums.email.EmailScene;
import com.hisun.kugga.duke.enums.system.SystemParamEnum;
import com.hisun.kugga.duke.league.bo.InviteJoinLeagueNoticeBO;
import com.hisun.kugga.duke.league.bo.LeagueCreateBO;
import com.hisun.kugga.duke.league.dal.dataobject.*;
import com.hisun.kugga.duke.league.dal.dataobject.views.LeagueViewsDO;
import com.hisun.kugga.duke.league.dal.mysql.*;
import com.hisun.kugga.duke.league.dal.redis.league.LeagueLockRedisDAO;
import com.hisun.kugga.duke.league.dal.redis.league.OrderRedisRepository;
import com.hisun.kugga.duke.league.enums.LeagueJoinTypeEnums;
import com.hisun.kugga.duke.league.enums.LeagueMemberLevelEnums;
import com.hisun.kugga.duke.league.service.LeagueAsyncService;
import com.hisun.kugga.duke.league.service.LeagueMemberService;
import com.hisun.kugga.duke.league.service.LeagueService;
import com.hisun.kugga.duke.league.vo.*;
import com.hisun.kugga.duke.pay.api.leagueaccount.LeagueAccountApi;
import com.hisun.kugga.duke.pay.api.order.OrderApi;
import com.hisun.kugga.duke.pay.api.order.dto.*;
import com.hisun.kugga.duke.pay.api.useraccount.UserAccountApi;
import com.hisun.kugga.duke.pay.api.useraccount.dto.AccountPwdVerifyDTO;
import com.hisun.kugga.duke.system.api.email.SendEmailApi;
import com.hisun.kugga.duke.system.api.email.dto.GeneralEmailReqDTO;
import com.hisun.kugga.duke.system.api.params.SystemParamsApi;
import com.hisun.kugga.duke.system.api.params.dto.SystemParamsRespDTO;
import com.hisun.kugga.duke.system.api.s3.S3FileUploadApi;
import com.hisun.kugga.duke.system.api.shorturl.ShortUrlApi;
import com.hisun.kugga.duke.system.api.shorturl.dto.CreateShortUrlReqDTO;
import com.hisun.kugga.duke.system.api.shorturl.dto.CreateShortUrlRspDTO;
import com.hisun.kugga.duke.system.api.shorturl.dto.QueryShortUrlRspDTO;
import com.hisun.kugga.duke.system.enums.EffectiveUnitEnum;
import com.hisun.kugga.duke.system.enums.ShortUrlTypeEnum;
import com.hisun.kugga.duke.user.api.oauth2.DukeUserApi;
import com.hisun.kugga.duke.user.api.oauth2.dto.UserInfoRespDTO;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.PageParam;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.common.util.date.DateUtils;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.LEAGUE_NOT_EXISTS;
import static com.hisun.kugga.duke.common.CommonConstants.EN_US;
import static com.hisun.kugga.duke.enums.system.SystemParamEnum.*;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hisun.kugga.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

/**
 * @author zuocheng
 */
@Service
@Slf4j
public class LeagueServiceImpl implements LeagueService {
    /**
     * 首个加么公会的人,会涉及到打钱,需要做下加锁
     * 10分钟 的毫秒
     */
    public static final long JOIN_TIMEOUT_MILLIS = 600 * DateUtils.SECOND_MILLIS;

    @Resource
    private LeagueMapper leagueMapper;
    @Resource
    private LeagueGrowthLevelMapper growthLevelMapper;

    @Resource
    private LeagueUnfinishedMapper leagueUnfinishedMapper;

    @Resource
    private SystemParamsApi systemParamsApi;

    @Resource
    private ShortUrlApi shortUrlApi;

    @Resource
    private LeagueInviteUrlBindMapper leagueInviteUrlBindMapper;

    @Resource
    private LeagueMemberMapper leagueMemberMapper;
    @Resource
    private LeagueMemberService memberService;

    @Resource
    private LeagueRuleMapper leagueRuleMapper;

    @Resource
    private SendEmailApi sendEmailApi;

    @Resource
    private DukeUserApi dukeUserApi;

    @Resource
    private OrderApi orderApi;

    @Resource
    private LeagueLockRedisDAO leagueLockRedisDAO;

    @Resource
    private LeagueAsyncService leagueAsyncService;

    @Resource
    private S3FileUploadApi s3FileUploadApi;

    @Resource
    private OrderRedisRepository orderRedisRepository;

    @Resource
    private TaskCreateLeagueMapper taskCreateLeagueMapper;

    @Resource
    private UserAccountApi userAccountApi;

    @Resource
    private LeagueAccountApi leagueAccountApi;

    @Override
    public String createUploadAvatar(byte[] bytes, String originalFilename, String contentType) {
        return s3FileUploadApi.createUploadLeagueAvatar(bytes, originalFilename, contentType);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CreatePreOrderRespVO createPreOrder(CreatePreOrderReqVO reqVO) {
        CreatePreOrderRespVO rsp = new CreatePreOrderRespVO();
        //获取创建公会付费金额
        BigDecimal amt = createPaymentAmount();
        //获取临时生成的头像
        reqVO.setLeagueAvatar(reqVO.getLeagueAvatar());
        //检查公会名是否重复
        isNameRepeat(reqVO.getLeagueName());

        //金额大于0时,则表示需要付费,调用预下单接口
        if (amt.compareTo(new BigDecimal(NumberEnum.ZERO.getValue())) > 0) {
            if (amt.compareTo(reqVO.getAmt()) != 0) {
                log.info("创建工会时页面显示金额[{}]与下单金额[{}]不至", reqVO.getAmt(), amt);
                throw exception(BusinessErrorCodeConstants.LEAGUE_CREATE_COST_ERR);
            }
            //支付预下单
            OrderCreateRspDTO order = orderApi.createOrder(new OrderCreateReqDTO()
                    .setOrderType(OrderType.CREATE_LEAGUE)
                    .setPayerId(SecurityFrameworkUtils.getLoginUserId())
                    .setAccountType(AccountType.USER)
                    .setAmount(amt));

            if (ObjectUtil.isNull(order)) {
                log.info("创建公会失败,下单失败");
                throw exception(BusinessErrorCodeConstants.LEAGUE_CREATE_COST_ERR);
            }

            if (StrUtil.isBlank(order.getAppOrderNo())) {
                log.info("创建公会失败,下单失败(未获取到订单号)");
                throw exception(BusinessErrorCodeConstants.LEAGUE_CREATE_COST_ERR);
            }

            //付费才有订单号等数据返回
            String uuid = UUID.fastUUID().toString();
            orderRedisRepository.setRandom(OrderType.CREATE_LEAGUE, uuid, uuid);

            rsp.setAppOrderNo(order.getAppOrderNo())
                    .setFee(order.getFee())
                    .setUuid(uuid);
        }


        //记录预创建公会信息
        Long leagueId = saveLeagueUnfinished(new LeagueCreateBO()
                .setLeagueAvatar(reqVO.getLeagueAvatar())
                .setLeagueName(reqVO.getLeagueName())
                .setLeagueDesc(reqVO.getLeagueDesc())
                .setAmount(amt)
                .setAppOrderNo(rsp.getAppOrderNo())
                .setUserId(getLoginUserId())
        );
        //无需下单支付就可以创建公会的,直接返回公会ID出去
        if (StrUtil.isBlank(rsp.getAppOrderNo())) {
            rsp.setLeagueId(leagueId);
        }
        return rsp;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public LeagueCreateRespVO create(LeagueCreateReqVO reqVO) {
        LeagueCreateBO leagueCreateBO = BeanUtil.copyProperties(reqVO, LeagueCreateBO.class);
        leagueCreateBO.setUserId(getLoginUserId());
        //订单支付(没报错就是支付成功)
        Long leagueId = createPay(reqVO.getAppOrderNo(), reqVO.getPassword(), reqVO.getPublicKey());
        //更新公会为预创建完成
        updateLeagueUnfinished(leagueId, reqVO.getAppOrderNo());

        return new LeagueCreateRespVO().setLeagueId(leagueId).setActivationFlag(false);
    }

    @Override
    public LeagueCreatePriceRespVO createPrice() {
        LeagueCreatePriceRespVO rsp = new LeagueCreatePriceRespVO();
        SystemParamsRespDTO systemParams = systemParamsApi.getSystemParams(CREATE_LEAGUE_COST.getType(), CREATE_LEAGUE_COST.getParamKey());
        if (ObjectUtil.isNull(systemParams)) {
            //未配置金额时,默认无需创建费用
            return rsp.setPrice(new BigDecimal("0.00"));
        }
        if (NumberUtil.isNumber(systemParams.getValue())) {
            return rsp.setPrice(new BigDecimal(systemParams.getValue()));
        } else {
            log.info("创建公会所需金额配置错误,值需为数字,当前值为[{}],请检查表[duke_system_params][param_key=create_league_cost]的值", systemParams.getValue());
            throw exception(BusinessErrorCodeConstants.LEAGUE_CREATE_COST_ERR);
        }
    }

    @Override
    public LeagueInviteUrlRespVO getInviteUrl(LeagueInviteUrlReqVO reqVO) {
        Long userId = getLoginUserId();

        LeagueViewsDO leagueViews = queryLeagueById(reqVO.getLeagueId());
        if (leagueViews.getActivationFlag()) {
            if (!isLeagueAdmin(reqVO.getLeagueId(), userId)) {
                log.info("用户[{}]非公会[{}]管理员,无法生成邀请连接", userId, reqVO.getLeagueId());
                throw exception(BusinessErrorCodeConstants.LEAGUE_NOT_ADMIN);
            }
        } else {
            if (!userId.equals(leagueViews.getUserId())) {
                log.info("用户[{}]非公会[{}]管理员,无法生成邀请连接", userId, reqVO.getLeagueId());
                throw exception(BusinessErrorCodeConstants.LEAGUE_NOT_ADMIN);
            }
        }

        //查询当前公会/个人是否已经存在有效的邀请连接
        LeagueInviteUrlBindDO validBind = leagueInviteUrlBindMapper.selectOneByLeagueId(reqVO.getLeagueId(), userId);
        if (ObjectUtil.isNull(validBind)) {
            return createInviteUrl(reqVO, userId);
        } else {
            return queryInviteUrl(validBind.getShortUrlId()).setBindId(validBind.getId());
        }
    }

    @Override
    public InviteDataRespVO getInviteData(String uuid) {
        InviteDataRespVO rspVO = new InviteDataRespVO();

        //查询邀请链绑定信息
        LeagueInviteUrlBindDO bind = leagueInviteUrlBindMapper.selectOneByUuid(uuid);
        if (ObjectUtil.isNull(bind)) {
            log.info("根据UUID[{}],未查询到邀请信息", uuid);
            throw exception(BusinessErrorCodeConstants.LEAGUE_INVITE_URL_NOT);
        }

        if (bind.getExpireStatus()) {
            log.info("邀请链接已失效");
            throw exception(BusinessErrorCodeConstants.SHORT_URL_INVALID);
        }

        LocalDateTime nowTime = LocalDateTime.now().withNano(0);
        if (ObjectUtil.isNotNull(bind.getExpireTime()) && nowTime.isAfter(bind.getExpireTime())) {
            log.info("链接[{}]已失效,链接有效时间[{}-{}],当前时间[{}]", bind.getId(), bind.getCreateTime(), bind.getExpireTime(), nowTime);
            throw exception(BusinessErrorCodeConstants.SHORT_URL_INVALID);
        }

        //查询公会信息
        LeagueViewsDO leagueViewsDO = queryLeagueById(bind.getLeagueId());

        rspVO.setLeagueId(leagueViewsDO.getId());
        rspVO.setLeagueName(leagueViewsDO.getLeagueName());
        rspVO.setLeagueDesc(leagueViewsDO.getLeagueDesc());
        rspVO.setLeagueAvatar(leagueViewsDO.getLeagueAvatar());
        rspVO.setLeagueCreateTime(leagueViewsDO.getCreateTime());
        rspVO.setLeagueActivationFlag(leagueViewsDO.getActivationFlag());

        Long userId = getLoginUserId();
        //默认用户属于未加入状态
        rspVO.setJoinedFlag(false);
        if (ObjectUtil.isNotNull(userId)) {
            //设置用记是否已是加入状态
            if (leagueViewsDO.getActivationFlag()) {
                //公会已激活状态下,直接查询待加入用户是否为公会成员
                rspVO.setJoinedFlag(isLeagueMember(leagueViewsDO.getId(), userId));
            } else {
                //公会未激活状态下,直接判断待加入用户是否为公会创建者
                rspVO.setJoinedFlag(userId.equals(leagueViewsDO.getUserId()));
            }
        }

        //获取用户信息
        UserInfoRespDTO user = dukeUserApi.getUserById(bind.getUserId());
        rspVO.setUserId(user.getId());
        rspVO.setUserName(user.getUsername());
        rspVO.setNickName(user.getNickname());
        rspVO.setFirstName(user.getFirstName());
        rspVO.setLastName(user.getLastName());

        return rspVO;
    }

    @Override
    public void inviteMail(LeagueInviteMailReqVO reqVO) {
        LeagueInviteUrlBindDO bind = leagueInviteUrlBindMapper.selectById(reqVO.getBinId());
        if (ObjectUtil.isNull(bind)) {
            log.info("根据[id:{}]未查询到邀请连接关系", reqVO.getBinId());
            throw exception(BusinessErrorCodeConstants.LEAGUE_INVITE_URL_NOT);
        }

        QueryShortUrlRspDTO shorUrl = shortUrlApi.queryShortUrlById(bind.getShortUrlId());
        if (ObjectUtil.isNull(shorUrl)) {
            log.info("根据[id:{}]未查询到短链接", bind.getShortUrlId());
            throw exception(BusinessErrorCodeConstants.SHORT_URL_NOT_EXIST);
        }

        //查询公会信息
        LeagueViewsDO leagueViewsDO = queryLeagueById(reqVO.getLeagueId());
        if (ObjectUtil.isNull(leagueViewsDO)) {
            log.info("公会[{}]信息不存在", reqVO.getLeagueId());
            throw exception(BusinessErrorCodeConstants.LEAGUE_NAME_NOT_EXISTS);
        }

        //根据邮件查询用户是否已注册,若已注册则检查用户是否已加入被邀请的公会,对于已加入的用户不在发送通知与邮件
        Iterator<String> iterator = reqVO.getMails().iterator();
        while (iterator.hasNext()) {
            String mail = iterator.next();
            UserInfoRespDTO user = dukeUserApi.getUserByEmail(mail);

            //未注册的邮箱非平台用户,一定可以被邀请，且一定不会是邀请公会的成员
            if (ObjectUtil.isNull(user)) {
                continue;
            }

            if (isLeagueMember(reqVO.getLeagueId(), user.getId())) {
                iterator.remove();
                log.info("用户[{}]已在公会[{}],不在下发邮件与邀请通知", user.getId(), reqVO.getLeagueId());
                continue;
            }

            //向已注册用户发送邀请通知
            leagueAsyncService.inviteJoinLeagueNotice(new InviteJoinLeagueNoticeBO()
                    .setUserId(user.getId())
                    .setInviteUrl(shorUrl.getShortUrl())
                    .setLeagueId(leagueViewsDO.getId())
                    .setLeagueName(leagueViewsDO.getLeagueName())
                    .setInviteUserId(user.getId())
            );
        }

        //经过筛选如果已经没有满足的用户时,直接忽略发送邀请邮件
        if (CollUtil.isEmpty(reqVO.getMails())) {
            return;
        }

        //获取邀请人用户信息
        UserInfoRespDTO inviteUser = dukeUserApi.getUserById(getLoginUserId());
        //设置替换值
        ArrayList<String> replaceValues = new ArrayList<>();
        //用户(邀请人)
        replaceValues.add(inviteUser.getFirstName() + inviteUser.getLastName());
        //公会(被邀请到哪个公会)
        replaceValues.add(leagueViewsDO.getLeagueName());
        //邀请连接
        replaceValues.add(shorUrl.getShortUrl());

        GeneralEmailReqDTO emailReq = new GeneralEmailReqDTO()
                .setEmailScene(EmailScene.JOIN_LEAGUE)
                .setTo(reqVO.getMails())
                .setLocale(EN_US)
                .setReplaceValues(replaceValues);

        //发送邀请邮件
        sendEmailApi.sendEmail(emailReq);
    }

    @Override
    public void joinLeague(JoinLeagueReqVO reqVO) {
        //检查是邀请人是否有有效的邀请链接
        LeagueInviteUrlBindDO inviteUrl = leagueInviteUrlBindMapper.selectOneByLeagueId(reqVO.getLeagueId(), reqVO.getInviteUserId());
        if (ObjectUtil.isNull(inviteUrl)) {
            throw exception(BusinessErrorCodeConstants.SHORT_URL_INVALID);
        }
        //查询公会信息时,默认查询的公会为正式公会,若正式公会信息不存在,则取未激活的公会信息表
        LeagueViewsDO leagueViewsDO = queryLeagueById(reqVO.getLeagueId());
        //未激活公会,走激活流程,已激活公会,直接走公会加入流程
        if (leagueViewsDO.getActivationFlag()) {
            activateLeagueJoin(leagueViewsDO.getId(), reqVO);
        } else {
            leagueLockRedisDAO.lock(leagueViewsDO.getId(), JOIN_TIMEOUT_MILLIS, () ->
                    notActivateLeagueJoin(leagueViewsDO, reqVO)
            );
        }
    }

    @Override
    public LeagueDetailsRespVO details(LeagueDetailsReqVO reqVO) {
        LeagueDetailsRespVO respVO = leagueMapper.selectLeagueDetail(reqVO.getLeagueId(), SecurityFrameworkUtils.getLoginUserId());

        if (ObjectUtil.isNull(respVO)) {
            log.info("未查询到公会[ID:{}]信息", reqVO.getLeagueId());
            throw exception(BusinessErrorCodeConstants.LEAGUE_NAME_NOT_EXISTS);
        }

        boolean isLeagueMember = ObjectUtil.isNotNull(respVO.getUserLevel());
        //判断用户是否为公会成员
        respVO.setMemberFlag(isLeagueMember);

        if (!isLeagueMember) {
            //不是工会成员，返回是否可加入及加入金额
            LeagueRuleDO leagueRuleDO = leagueRuleMapper.getLeagueRuleInfoByLeagueId(reqVO.getLeagueId());
            if (ObjectUtil.isNull(leagueRuleDO)) {
                throw exception(LEAGUE_NOT_EXISTS);
            }
            respVO.setEnabledUserJoin(leagueRuleDO.getEnabledUserJoin());
            respVO.setUserJoinPrice(leagueRuleDO.getUserJoinPrice());
            respVO.setEnabledAdminApproval(leagueRuleDO.getEnabledAdminApproval());
        }

        return respVO;
    }

    @Override
    public PageResult<LeagueRecommendsVO> recommendLeagues(LeagueSearchReqVO reqVO) {
        Page page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        reqVO.setUserId(getLoginUserId());
        IPage<LeagueRecommendsVO> pageLeagues = leagueMapper.pageRecommends(page, reqVO);
        return new PageResult<>(pageLeagues.getRecords(), pageLeagues.getTotal());
    }

    @Override
    public PageResult<LeagueRecommendsVO> searchLeagues(LeagueSearchReqVO reqVO) {
        Page page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        reqVO.setUserId(getLoginUserId());
        IPage<LeagueRecommendsVO> pageLeagues = leagueMapper.pageSearchRecommends(page, reqVO);
        return new PageResult<>(pageLeagues.getRecords(), pageLeagues.getTotal());
    }

    @Override
    public PageResult<LeagueAndRuleVO> recommendAuthLeagues(PageParam reqVO) {
        Page<LeagueAndRuleVO> page = new Page<>(reqVO.getPageNo(),
                reqVO.getPageSize());
        IPage<LeagueAndRuleVO> leagues = leagueMapper.pageAuthLeague(page);
        return new PageResult<>(leagues.getRecords(), leagues.getTotal());
    }

    @Override
    public PageResult<LeagueMembersVO> members(LeagueMembersPageReqVO reqVO) {
        //先判断用户是否为公会成员（避免非公会成员盗取数据）
        if (!isLeagueMember(reqVO.getLeagueId(), getLoginUserId())) {
            log.info("非公会成员,拒绝获取公会成员信息,直接返回空");
            return PageResult.empty();
        }

        Page<LeagueMembersPageReqVO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        IPage<LeagueMembersVO> members = leagueMemberMapper.pageLeagueMembers(page, reqVO);
        return new PageResult<>(members.getRecords(), members.getTotal());
    }

    @Override
    public PageResult<UserLeaguesVO> userLeagues(UserLeaguesPageReqVO reqVO) {
        Page<UserLeaguesVO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        IPage<UserLeaguesVO> pageUserLeagues = null;
        if (reqVO.getType()) {
            //查询用户已加入公会，且已被认证的公会
            pageUserLeagues = leagueMemberMapper.pageUserLeagues(page, getLoginUserId(), true);
        } else {
            //查询用户所有加入的公会
            pageUserLeagues = leagueMemberMapper.pageUserLeagues(page, getLoginUserId(), null);
        }
        return new PageResult<>(pageUserLeagues.getRecords(), pageUserLeagues.getTotal());
    }

    @Override
    public LeagueDO getById(Long leagueId) {
        LeagueDO leagueDO = leagueMapper.selectById(leagueId);
        if (ObjectUtil.isNull(leagueDO)) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.LEAGUE_NAME_NOT_EXISTS);
        }
        return leagueDO;
    }

    /**
     * 检查用户是否是公会成员
     *
     * @param leagueId
     * @param userIds
     * @return true:是公会成员  false:不是公会成员
     */
    @Override
    public Boolean isLeagueMember(Long leagueId, Long... userIds) {
        if (ObjectUtil.isNull(leagueId)) {
            log.info("公会ID为空");
            throw exception(BusinessErrorCodeConstants.LEAGUE_ID_IS_NULL);
        }
        if (ObjectUtil.isNull(userIds)) {
            log.info("公会成员ID为空");
            throw exception(BusinessErrorCodeConstants.LEAGUE_MEMBER_IS_NULL);
        }

        int peopleNumber = userIds.length;
        if (peopleNumber == 0) {
            log.info("公会成员ID为空");
            throw exception(BusinessErrorCodeConstants.LEAGUE_MEMBER_IS_NULL);
        }

        List<Long> list = new ArrayList<>(peopleNumber);
        for (Long userId : userIds) {
            list.add(userId);
        }

        Long num = leagueMemberMapper.isUserMember(leagueId, list);
        Long lPeopleNumber = (long) peopleNumber;

        return lPeopleNumber.equals(num);
    }

    @Override
    public Boolean isLeagueAdmin(Long leagueId, Long userId) {
        if (ObjectUtil.isNull(leagueId)) {
            log.info("公会ID为空");
            throw exception(BusinessErrorCodeConstants.LEAGUE_ID_IS_NULL);
        }

        if (ObjectUtil.isNull(userId)) {
            log.info("公会成员ID为空");
            throw exception(BusinessErrorCodeConstants.LEAGUE_MEMBER_IS_NULL);
        }

        //查询到成员信息
        LeagueMemberDO member = leagueMemberMapper.isLeagueAdmin(leagueId, userId);
        //非公会成员,默认成非公会管理员
        if (ObjectUtil.isNull(member)) {
            return false;
        }

        if (member.getLevel().equals(LeagueMemberLevelEnums.SUPER_ADM.getType())) {
            return true;
        }

        if (member.getLevel().equals(LeagueMemberLevelEnums.ADM.getType())) {
            return true;
        }

        return false;
    }

    @Override
    public LeagueAccountRespVO leagueAccount(LeagueAccountReqVO reqVO) {
        //设置响应对象
        LeagueAccountRespVO respVO = new LeagueAccountRespVO();
        //只有正式公会才可能有额度,所以直接查询公会正式表
        LeagueDO league = leagueMapper.selectById(reqVO.getLeagueId());
        if (ObjectUtil.isNull(league)) {
            return respVO;
        }

        //公会为认证状态时(认证后才会开户),去获取公会的账户余额
        if (league.getAuthFlag()) {
            try {
                // 查询公会钱包账户余额
                BigDecimal leagueBalance = leagueAccountApi.getLeagueBalance(reqVO.getLeagueId());
                respVO.setAccount(leagueBalance);
            } catch (Exception e) {
                log.error("公会[{}]余额查询失败(不报错,返回空给前端前端打--)", reqVO.getLeagueId(), e);
            }
        }

        return respVO;
    }

    @Override
    public CheckMailIsLeagueMemberRespVO checkMailIsLeagueMember(CheckMailIsLeagueMemberReqVO reqVO) {
        CheckMailIsLeagueMemberRespVO rspVO = new CheckMailIsLeagueMemberRespVO();
        //默认邮箱用户非公会成员
        rspVO.setMemberFlag(false);
        UserInfoRespDTO user = dukeUserApi.getUserByEmail(reqVO.getMail());

        //邮箱为已注册用户时,直接拿用户ID和公会ID去检查用户是否为公会成员
        if (ObjectUtil.isNotNull(user)) {
            rspVO.setMemberFlag(isLeagueMember(reqVO.getLeagueId(), user.getId()));
        }

        return rspVO;
    }

    @Override
    public List<Long> queryUserAllJoinLeagueId(Long userId) {
        return leagueMapper.selectUserAllJoinLeagueId(userId);
    }

    /**
     * 根据公会ID查询公会信息
     *
     * @return
     */
    private LeagueViewsDO queryLeagueById(Long id) {
        LeagueViewsDO leagueViewsDO = new LeagueViewsDO();
        //默认查询的公会为正式公会,未查询到数据则查询未激活公会信息
        LeagueDO leagueDO = leagueMapper.selectById(id);
        if (ObjectUtil.isNull(leagueDO)) {
            LeagueUnfinishedDO unfinished = leagueUnfinishedMapper.selectById(id);
            if (ObjectUtil.isNull(unfinished)) {
                log.info("公会[{}]信息不存在(待激活公会)", id);
                throw exception(BusinessErrorCodeConstants.LEAGUE_NAME_NOT_EXISTS);
            }
            leagueViewsDO = Convert.convert(LeagueViewsDO.class, unfinished);
            //若公会未激活,激活设置成false,且一定是未认证状态,无公会资产，即金额为0
            leagueViewsDO.setActivationFlag(false);
            leagueViewsDO.setAuthFlag(false);
        } else {
            leagueViewsDO = Convert.convert(LeagueViewsDO.class, leagueDO);
            leagueViewsDO.setUserId(leagueDO.getCreateUserId());
            leagueViewsDO.setActivationFlag(true);
        }

        return leagueViewsDO;
    }

    /**
     * 已激活公会加入流程
     *
     * @param leagueId
     * @param reqVO
     */
    private void activateLeagueJoin(long leagueId, JoinLeagueReqVO reqVO) {
        //正式公会校验公会是否存在
        LeagueDO league = leagueMapper.selectById(leagueId);
        if (ObjectUtil.isNull(league)) {
            log.info("公会[{}]信息不存在(正式公会)", leagueId);
            throw exception(BusinessErrorCodeConstants.LEAGUE_NAME_NOT_EXISTS);
        }

        if (isLeagueMember(leagueId, getLoginUserId())) {
            log.info("用户[{}]已经是公会[{}]成员,无法次加入公会", getLoginUserId(), leagueId);
            throw exception(BusinessErrorCodeConstants.NOT_REPEAT_JOIN_LEAGUE);
        }

        //添加用户至公会
        joinLeagueSave(leagueId, reqVO);
    }

    /**
     * 未激活公会加入流程
     *
     * @param leagueViewsDO
     * @param reqVO
     */
    private void notActivateLeagueJoin(LeagueViewsDO leagueViewsDO, JoinLeagueReqVO reqVO) {
        if (leagueViewsDO.getUserId().equals(getLoginUserId())) {
            log.info("公会创建者无需自己加入公会,当前用户[{}],创建者[{}]", getLoginUserId(), leagueViewsDO.getId());
            throw exception(BusinessErrorCodeConstants.NOT_REPEAT_JOIN_LEAGUE);
        }

        //激活公会
        activationLeague(leagueViewsDO, reqVO);
        //分账
        firstJoinLedger(leagueViewsDO);
        //发送公会创建成功通知,给公会创建者
        leagueAsyncService.firstJoinLeagueNotice(getLoginUserId(), leagueViewsDO);
    }

    /**
     * 首个加入公会的用户分账
     */
    private void firstJoinLedger(LeagueViewsDO leagueViewsDO) {
        //查询付款信息
        TaskCreateLeagueDO order = taskCreateLeagueMapper.selectByOrder(leagueViewsDO.getDeductOrderNo());
        if (ObjectUtil.isNull(order)) {
            return;
        }

        //非付费订单不做分账
        if (!order.getPayFlag()) {
            return;
        }

        //非默认状态不做分账
        if (order.getAmountStatus() != 0) {
            return;
        }

        //订单需要付费,且未分账的订单做分账
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        //将订单更新至分账中
        taskCreateLeagueMapper.updateLedgerIn(userId, order.getAppOrderNo());
        try {
            //设置被打款人，及打款信息
            List receiver = new ArrayList<ReceiverInfo>();
            receiver.add(new ReceiverInfo().setReceiverId(userId)
                    .setAmount(order.getAmount())
                    .setAccountType(AccountType.USER));
            //给用户打款
            orderApi.splitAccount(new SplitAccountReqDTO()
                    .setOrderType(OrderType.JOIN_LEAGUE)
                    .setAppOrderNo(order.getAppOrderNo())
                    .setReceiverList(receiver)
            );

            //将订单更新至分账成功
            taskCreateLeagueMapper.updateLedger(order.getAppOrderNo());

            //发送收款通知,给第一个收款人
            leagueAsyncService.firstJoinLeagueLedgerNotice(userId, leagueViewsDO);
        } catch (Exception e) {
            //分账出错时,不影响业务影响,忽略报错
            log.error("首个加入公会的用户[{}]分账异常", userId, e);
        }
    }

    /**
     * 查询短链接信息
     *
     * @param id
     * @return
     */
    private LeagueInviteUrlRespVO queryInviteUrl(long id) {
        QueryShortUrlRspDTO shortUrlRspDTO = shortUrlApi.queryShortUrlById(id);
        if (ObjectUtil.isNull(shortUrlRspDTO)) {
            log.info("获取邀请连接信息失败,根据[id:{}]无法查询到连接信息", id);
            throw exception(BusinessErrorCodeConstants.LEAGUE_INVITE_URL_NOT);
        }
        LeagueInviteUrlRespVO rspDTO = new LeagueInviteUrlRespVO();
        rspDTO.setUrl(shortUrlRspDTO.getShortUrl());
        return rspDTO;
    }

    /**
     * 创建邀请短链接
     *
     * @param reqVO
     * @param userId
     * @return
     */
    private LeagueInviteUrlRespVO createInviteUrl(LeagueInviteUrlReqVO reqVO, long userId) {
        //查询失效单位(未查询到数据,则说明不需生成的短链接,无需失效日期)
        SystemParamsRespDTO unit = systemParamsApi.getSystemParams(LEAGUE_INVITE_URI_EFFECTIVE_UNIT.getType(), LEAGUE_INVITE_URI_EFFECTIVE_UNIT.getParamKey());
        //查询失效值(未查询到数据,则说明不需生成的短链接,无需失效日期)
        SystemParamsRespDTO period = systemParamsApi.getSystemParams(LEAGUE_INVITE_URI_EFFECTIVE_PERIOD.getType(), LEAGUE_INVITE_URI_EFFECTIVE_PERIOD.getParamKey());

        String uuid = IdUtil.fastSimpleUUID();
        //获取用户信息
        CreateShortUrlReqDTO createShortUrlReqDTO = new CreateShortUrlReqDTO();
        createShortUrlReqDTO.setUrl(reqVO.getUrl() + "/" + uuid);
        createShortUrlReqDTO.setType(ShortUrlTypeEnum.TYPE_G);
        //失效单位与失效值,不为空时,则在生成短链的时候,传递失效参数
        if (checkInviteEffective(unit, period)) {
            createShortUrlReqDTO.setEffectiveUnit(EffectiveUnitEnum.getByCode(unit.getValue()));
            createShortUrlReqDTO.setEffectivePeriod(Integer.valueOf(period.getValue()));
        }

        CreateShortUrlRspDTO shortUrlDTO = shortUrlApi.createShortUrl(createShortUrlReqDTO);

        //记录绑定关系
        LeagueInviteUrlBindDO bind = new LeagueInviteUrlBindDO();
        bind.setUuid(uuid);
        bind.setShortUrlId(shortUrlDTO.getId());
        bind.setLeagueId(reqVO.getLeagueId());
        bind.setUserId(userId);
        bind.setCreateTime(LocalDateTime.now().withNano(0));
        bind.setExpireTime(shortUrlDTO.getExpireTime());

        int res = leagueInviteUrlBindMapper.insert(bind);
        if (res != 1) {
            throw exception(BusinessErrorCodeConstants.LEAGUE_INVITE_BIND_ERR);
        }
        LeagueInviteUrlRespVO rspDTO = new LeagueInviteUrlRespVO();
        rspDTO.setUrl(shortUrlDTO.getShortUrl());
        rspDTO.setBindId(bind.getId());
        return rspDTO;
    }


    /**
     * 检查失效单位、期限是不是否有效
     *
     * @return
     */
    private boolean checkInviteEffective(SystemParamsRespDTO unit, SystemParamsRespDTO period) {
        //对象为空时无有效期设置
        if (ObjectUtil.isNull(unit) || ObjectUtil.isNull(period)) {
            return false;
        }
        //值为空时无有效期设置
        if (StrUtil.isBlank(unit.getValue()) || StrUtil.isBlank(period.getValue())) {
            return false;
        }
        //配置枚举类型错误时,则无失效期
        if (ObjectUtil.isNull(EffectiveUnitEnum.getByCode(unit.getValue()))) {
            log.info("失效单位[{}],枚举类型错误,应配置成(s、m、H、D、W、M、Y)中的一种,忽略失效期参数,生成的短链将无失效期", unit.getValue());
            return false;
        }
        //配置值,不为整数时,则无失效期
        if (!NumberUtil.isInteger(period.getValue())) {
            log.info("失效期[{}],不为整数,忽略失效期参数,生成的短链将无失效期", period.getValue());
            return false;
        }

        return true;
    }

    /**
     * 加入公会数据持久化
     *
     * @param leagueId
     * @param reqVO
     */
    private void joinLeagueSave(long leagueId, JoinLeagueReqVO reqVO) {
        LeagueMemberJoinVO joinVO = new LeagueMemberJoinVO();
        joinVO.setLeagueId(leagueId);
        joinVO.setUserId(getLoginUserId());
        joinVO.setLevel(LeagueMemberLevelEnums.MEMBER.getType());
        joinVO.setRelationUserId(reqVO.getInviteUserId());
        joinVO.setJoinType(LeagueJoinTypeEnums.INVITE.getType());
        joinVO.setJoinTime(LocalDateTime.now());
        joinVO.setUpdateTime(LocalDateTime.now());
        memberService.joinLeague(joinVO);
    }

    /**
     * 公会激活
     * 激活公会与产个成员入会需要在一个事务里,避名数据不一至
     *
     * @param leagueViewsDO
     * @param reqVO
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void activationLeague(LeagueViewsDO leagueViewsDO, JoinLeagueReqVO reqVO) {
        //生成正式公会
        LeagueDO insertL = new LeagueDO();
        insertL.setId(leagueViewsDO.getId());
        insertL.setCreateTime(LocalDateTime.now().withNano(0));
        insertL.setUpdateTime(LocalDateTime.now().withNano(0));
        insertL.setLeagueName(leagueViewsDO.getLeagueName());
        insertL.setLeagueDesc(leagueViewsDO.getLeagueDesc());
        insertL.setLeagueAvatar(leagueViewsDO.getLeagueAvatar());
        insertL.setCreateUserId(reqVO.getInviteUserId());
        int res0 = leagueMapper.insert(insertL);
        if (res0 != 1) {
            log.info("插入公会信息表[duke_league]失败,需要插入数据如下[{}]", insertL);
            throw exception(BusinessErrorCodeConstants.LEAGUE_INSERT_ERR);
        }

        //公会生成同步初始化公会等级信息
        growthLevelMapper.insertInitInfo(insertL.getId());

        int res1 = leagueMapper.initLeagueAccount(insertL.getId(), new BigDecimal("0.00"));
        if (res1 != 1) {
            log.info("插入公会账户表[duke_league_account]失败,需要插入数据如下[公会ID:{}]", insertL.getId());
            throw exception(BusinessErrorCodeConstants.LEAGUE_INSERT_ERR);
        }

        //生成默认公会规则
        LeagueRuleDO insertR = new LeagueRuleDO();
        insertR.setLeagueId(insertL.getId());
        insertR.setCreateTime(LocalDateTime.now().withNano(0));
        insertR.setUpdateTime(LocalDateTime.now().withNano(0));
        insertR.setUpdateUserId(reqVO.getInviteUserId());
        insertR.setEnabledAuth(false);
        insertR.setAuthPrice(queryInitAmt(LEAGUE_INIT_AUTH_PRICE));
        insertR.setReportPrice(queryInitAmt(LEAGUE_INIT_REPORT_PRICE));
        insertR.setChatPrice(queryInitAmt(LEAGUE_INIT_CHAT_PRICE));
        insertR.setUserJoinPrice(queryInitAmt(LEAGUE_INIT_USER_JOIN_PRICE));
        insertR.setEnabledUserJoin(false);
        int res2 = leagueRuleMapper.insert(insertR);
        if (res2 != 1) {
            log.info("插入公会规则表[duke_league_rule]失败,需要插入数据如下[{}]", insertR);
            throw exception(BusinessErrorCodeConstants.LEAGUE_INSERT_ERR);
        }

        //插入公会创建者为第一个成员,且默认为超级管理员
        LeagueMemberJoinVO joinVO = new LeagueMemberJoinVO();
        joinVO.setLeagueId(insertL.getId());
        joinVO.setUserId(leagueViewsDO.getUserId());
        joinVO.setLevel(LeagueMemberLevelEnums.SUPER_ADM.getType());
        joinVO.setJoinType(LeagueJoinTypeEnums.CREATE.getType());
        joinVO.setJoinTime(LocalDateTime.now());
        joinVO.setUpdateTime(LocalDateTime.now());
        int res3 = memberService.joinLeague(joinVO);
        if (res3 != 1) {
            log.info("插入表[duke_league_member]失败(公会创建者),需要插入数据如下[{}]", joinVO);
            throw exception(BusinessErrorCodeConstants.LEAGUE_INSERT_ERR);
        }

        //插入公会的第二个成员,并默认成公会管理员
        LeagueMemberJoinVO joinVO2 = new LeagueMemberJoinVO();
        joinVO2.setLeagueId(insertL.getId());
        joinVO2.setUserId(getLoginUserId());
        joinVO2.setLevel(LeagueMemberLevelEnums.ADM.getType());
        joinVO2.setRelationUserId(reqVO.getInviteUserId());
        joinVO2.setJoinType(LeagueJoinTypeEnums.INVITE.getType());
        joinVO2.setJoinTime(LocalDateTime.now());
        joinVO2.setUpdateTime(LocalDateTime.now());
        int res4 = memberService.joinLeague(joinVO2);
        if (res4 != 1) {
            log.info("插入表[duke_league_member]失败(公会首个成员),需要插入数据如下[{}]", joinVO2);
            throw exception(BusinessErrorCodeConstants.LEAGUE_INSERT_ERR);
        }

        //修改公会未完成表,公会已完成创建
        LeagueUnfinishedDO updateU = new LeagueUnfinishedDO();
        updateU.setId(leagueViewsDO.getId());
        updateU.setEntryUserId(getLoginUserId());
        updateU.setEntryTime(LocalDateTime.now().withNano(0));
        updateU.setUpdateTime(LocalDateTime.now().withNano(0));
        updateU.setActivationStatus(true);
        int res5 = leagueUnfinishedMapper.updateById(updateU);
        if (res5 != 1) {
            log.info("修改表[duke_league_unfinished]失败,修改[status]为ture,修改参数[{}]", updateU);
            throw exception(BusinessErrorCodeConstants.LEAGUE_INSERT_ERR);
        }
    }

    /**
     * 根据Key查询初始金额
     *
     * @return
     */
    private BigDecimal queryInitAmt(SystemParamEnum paramEnum) {
        SystemParamsRespDTO sysParam = systemParamsApi.getSystemParams(paramEnum.getType(), paramEnum.getParamKey());
        if (ObjectUtil.isNull(sysParam)) {
            return null;
        }

        return Optional.ofNullable(sysParam.getValue()).map(BigDecimal::new).orElse(null);
    }

    /**
     * 更新为创建完成状态
     *
     * @return
     */
    public void updateLeagueUnfinished(Long leagueId, String appOrderNo) {
        int res0 = leagueUnfinishedMapper.updateById(new LeagueUnfinishedDO()
                .setId(leagueId)
                .setPreStatus(true)
                .setUpdateTime(LocalDateTime.now())
        );

        if (res0 != 1) {
            log.info("修改[{}]公会时出错", leagueId);
            throw exception(BusinessErrorCodeConstants.LEAGUE_INSERT_ERR);
        }

        //更新支付结果，并将订单绑定公会ID
        int res1 = taskCreateLeagueMapper.updateByOrder(appOrderNo);
        if (res1 != 1) {
            log.info("更新订单[{}]失败", appOrderNo);
            throw exception(BusinessErrorCodeConstants.LEAGUE_INSERT_ERR);
        }
    }

    /**
     * 获取创建付费金额
     *
     * @return
     */
    private BigDecimal createPaymentAmount() {
        //查询创建公会所需费用
        SystemParamsRespDTO systemParams = systemParamsApi.getSystemParams(CREATE_LEAGUE_COST.getType(), CREATE_LEAGUE_COST.getParamKey());

        if (ObjectUtil.isNull(systemParams)) {
            log.info("公会创建费为未配置(未配置参数),无需支付公会创建费用!");
            return new BigDecimal("0");
        }

        if (StrUtil.isBlank(systemParams.getValue())) {
            log.info("公会创建费为未配置(未配置值),无需支付公会创建费用!");
            return new BigDecimal("0");
        }

        return new BigDecimal(systemParams.getValue());
    }

    /**
     * 检查公会名是否重复
     *
     * @param leagueName
     */
    private void isNameRepeat(String leagueName) {
        LeagueUnfinishedDO unfinished = leagueUnfinishedMapper.selectByLeagueName(leagueName);
        if (ObjectUtil.isNotNull(unfinished)) {
            //如果有同名且预创建完成的公会则报公会名重复
            if (unfinished.getPreStatus()) {
                log.info("公会名[{}],已存在", leagueName);
                throw exception(BusinessErrorCodeConstants.LEAGUE_NAME_EXISTS);
            }

            //如果当前公会名处于预创建前,但当前创建用户与占用此公会名的用户不一致时报公会名重复
            if (!unfinished.getUserId().equals(getLoginUserId())) {
                log.info("公会名[{}],已存在", leagueName);
                throw exception(BusinessErrorCodeConstants.LEAGUE_NAME_EXISTS);
            }
        }

        LeagueDO league = leagueMapper.selectByLeagueName(leagueName);
        if (ObjectUtil.isNotNull(league)) {
            log.info("公会名[{}],已存在", leagueName);
            throw exception(BusinessErrorCodeConstants.LEAGUE_NAME_EXISTS);
        }
    }

    /**
     * 支付创建公会的订单
     *
     * @param appOrderNo
     * @param password
     * @param publicKey
     */
    private Long createPay(String appOrderNo, String password, String publicKey) {
        TaskCreateLeagueDO order = taskCreateLeagueMapper.selectByOrder(appOrderNo);
        if (ObjectUtil.isNull(order)) {
            log.info("订单[{}]不存在", appOrderNo);
            throw exception(BusinessErrorCodeConstants.ORDER_NOT_EXISTS);
        }

        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if (!order.getUserId().equals(userId)) {
            log.info("订单[{}]用户[{}]归属错误,非当前用户[{}]订单", appOrderNo, order.getUserId(), userId);
            throw exception(BusinessErrorCodeConstants.ORDER_NOT_EXISTS);
        }

        if (ObjectUtil.equal(PayStatusEnum.PAY.getValue(), order.getPayStatus())) {
            log.info("订单[{}]已支付", appOrderNo);
            throw exception(BusinessErrorCodeConstants.ORDER_NOT_EXISTS);
        }

        AccountPwdVerifyDTO accountPwdVerifyDTO = new AccountPwdVerifyDTO();
        accountPwdVerifyDTO.setPassword(password);
        accountPwdVerifyDTO.setPublicKey(publicKey);
        if (!userAccountApi.verifyPayPassword(accountPwdVerifyDTO)) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.PAY_PASSWORD_WRONG);
        }

        //实际支付
        orderApi.pay(new PayReqDTO().setAppOrderNo(appOrderNo));

        return order.getLeagueId();
    }

    /**
     * 将待创建的公会记录到,公会完成表
     *
     * @param leagueBO
     */
    private Long saveLeagueUnfinished(LeagueCreateBO leagueBO) {
        //哎,这里需要考虑下捡起的情况,查询用户是否已经存在同名待创建的公会
        LeagueUnfinishedDO leagueUnfinishe = leagueUnfinishedMapper.selectByUserAndName(getLoginUserId(), leagueBO.getLeagueName());
        //预创建公会不存在时做插入,存在时做更新
        if (ObjectUtil.isNull(leagueUnfinishe)) {
            LeagueUnfinishedDO insertLeague = new LeagueUnfinishedDO();
            insertLeague.setLeagueName(leagueBO.getLeagueName());
            insertLeague.setCreateTime(LocalDateTime.now().withNano(0));
            insertLeague.setUpdateTime(LocalDateTime.now().withNano(0));
            insertLeague.setLeagueDesc(leagueBO.getLeagueDesc());
            insertLeague.setLeagueAvatar(leagueBO.getLeagueAvatar());
            insertLeague.setUserId(leagueBO.getUserId());
            insertLeague.setActivationStatus(false);
            insertLeague.setDeductOrderNo(leagueBO.getAppOrderNo());
            insertLeague.setDeductAmount(leagueBO.getAmount());
            if (StrUtil.isBlank(leagueBO.getAppOrderNo())) {
                //没有订单号的,表示创建公会时不需要支付,所以直接设置公会预创建成功
                insertLeague.setPreStatus(true);
            }

            int res0 = leagueUnfinishedMapper.insert(insertLeague);
            if (res0 != 1) {
                log.info("创建公会,插入数据库存失败,[{}]公会", leagueBO.getLeagueName());
                throw exception(BusinessErrorCodeConstants.LEAGUE_INSERT_ERR);
            }

            leagueUnfinishe = BeanUtil.copyProperties(insertLeague, LeagueUnfinishedDO.class);
        } else {
            int res0 = leagueUnfinishedMapper.updateById(new LeagueUnfinishedDO()
                    .setId(leagueUnfinishe.getId())
                    .setLeagueName(leagueBO.getLeagueName())
                    .setLeagueDesc(leagueBO.getLeagueDesc())
                    .setLeagueAvatar(leagueBO.getLeagueAvatar())
                    .setDeductOrderNo(leagueBO.getAppOrderNo())
                    .setDeductAmount(leagueBO.getAmount())
            );
            if (res0 != 1) {
                log.info("创建公会,插入数据库存失败,[{}]公会", leagueBO.getLeagueName());
                throw exception(BusinessErrorCodeConstants.LEAGUE_INSERT_ERR);
            }
        }

        //存在下单信息时,记录一笔订单号
        if (StrUtil.isNotBlank(leagueBO.getAppOrderNo())) {
            //插入订单信息
            TaskCreateLeagueDO insert = new TaskCreateLeagueDO();
            insert.setUserId(SecurityFrameworkUtils.getLoginUserId());
            insert.setLeagueId(leagueUnfinishe.getId());
            insert.setBusinessStatus(0);
            insert.setCreateTime(LocalDateTime.now());
            insert.setUpdateTime(LocalDateTime.now());
            insert.setDeleted(false);
            insert.setAppOrderNo(leagueBO.getAppOrderNo());
            insert.setAmount(leagueBO.getAmount());
            insert.setPayStatus(0);
            insert.setAmountStatus(0);
            insert.setPayFlag(true);
            int res1 = taskCreateLeagueMapper.insert(insert);
            if (res1 != 1) {
                log.info("预创建公会下单失败,[{}]公会", leagueBO.getLeagueName());
                throw exception(BusinessErrorCodeConstants.LEAGUE_INSERT_ERR);
            }
        }

        if (leagueBO.getLeagueAvatar() != null && !StrUtil.isBlank(leagueBO.getLeagueAvatar())) {
            //公会创建成功，拷贝头像到公会的头像目录，temp目录下的头像则可以删除
            String leagueAvatar = s3FileUploadApi.copyLeagueAvatar(leagueUnfinishe.getId(), leagueBO.getLeagueAvatar());
            // 公会头像拷贝成功后 更新头像
            leagueUnfinishedMapper.updateById(LeagueUnfinishedDO.builder()
                    .id(leagueUnfinishe.getId())
                    .leagueAvatar(leagueAvatar)
                    .build());
        }

        return leagueUnfinishe.getId();
    }
}
