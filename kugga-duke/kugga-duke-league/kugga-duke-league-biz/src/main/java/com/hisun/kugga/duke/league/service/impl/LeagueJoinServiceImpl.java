package com.hisun.kugga.duke.league.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.lock.annotation.Lock4j;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.enums.AccountType;
import com.hisun.kugga.duke.enums.LeagueJoinApprovalTypeEnum;
import com.hisun.kugga.duke.enums.OrderType;
import com.hisun.kugga.duke.enums.PayStatusEnum;
import com.hisun.kugga.duke.league.LeagueSubscribeType;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueJoinApprovalDO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueJoinDO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueMemberDO;
import com.hisun.kugga.duke.league.dal.mysql.LeagueJoinApprovalMapper;
import com.hisun.kugga.duke.league.dal.mysql.LeagueJoinMapper;
import com.hisun.kugga.duke.league.dal.mysql.LeagueMemberMapper;
import com.hisun.kugga.duke.league.dal.redis.league.OrderRedisRepository;
import com.hisun.kugga.duke.league.enums.LeagueJoinTypeEnums;
import com.hisun.kugga.duke.league.enums.LeagueMemberLevelEnums;
import com.hisun.kugga.duke.league.service.LeagueJoinService;
import com.hisun.kugga.duke.league.service.LeagueMemberService;
import com.hisun.kugga.duke.league.service.LeagueRuleService;
import com.hisun.kugga.duke.league.service.LeagueSubscribeService;
import com.hisun.kugga.duke.league.vo.LeagueMemberJoinVO;
import com.hisun.kugga.duke.league.vo.joinLeague.*;
import com.hisun.kugga.duke.league.vo.rule.LeagueRuleVO;
import com.hisun.kugga.duke.league.vo.rule.SubscribeSelectVo;
import com.hisun.kugga.duke.league.vo.subscribe.SubscribeVo;
import com.hisun.kugga.duke.pay.api.order.OrderApi;
import com.hisun.kugga.duke.pay.api.order.dto.*;
import com.hisun.kugga.duke.pay.api.useraccount.UserAccountApi;
import com.hisun.kugga.duke.pay.api.useraccount.dto.AccountPwdVerifyDTO;
import com.hisun.kugga.duke.utils.SplitAccountUtil;
import com.hisun.kugga.duke.utils.vo.SplitAccountVo;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.web.core.util.WebFrameworkUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.*;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/13 17:18
 */
@Service
public class LeagueJoinServiceImpl implements LeagueJoinService {
    @Resource
    private LeagueJoinMapper leagueJoinMapper;
    @Resource
    private LeagueRuleService ruleService;
    @Resource
    private LeagueMemberMapper memberMapper;
    @Resource
    private LeagueMemberService memberService;
    @Resource
    private OrderApi orderApi;
    @Resource
    private OrderRedisRepository orderRedisRepository;
    @Resource
    private UserAccountApi userAccountApi;
    @Resource
    private LeagueJoinApprovalMapper approvalMapper;
    @Resource
    private LeagueSubscribeService subscribeService;

    /**
     * 1、免费  不需要审批 joinLeagueInit接口直接入会  状态为1已同意 是否需要管理员审批为false 金额为0
     * 2、付费  不需要审批 joinLeagueInit初始化订单数据 joinLeaguePay支付接口直接入会  状态为1已同意 是否需要管理员审批为false 下单金额为100
     * <p>
     * 3、免费 需要审批  joinLeagueInit插入订单数据和审批记录，状态为0初始化未审批，是否需要管理员审批为true 金额为0
     * 4、付费 需要审批  joinLeagueInit初始化订单数据 joinLeaguePay初始化审批数据
     * <p>
     * 新增订阅功能
     * 场景一：joinLeagueInit 初始化订阅信息
     * 场景二：joinLeaguePay 初始化订阅信息
     * 场景三四：updateLeagueJoinApproval 需要审批的在审批通过后初始化
     *
     * @param vo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public LeagueJoinInitRespVO joinLeagueInit(LeagueJoinInitReqVO vo) {
        //加入公会前置校验
        LeagueRuleVO ruleVO = preCheck(vo);

        LeagueJoinInitRespVO respVO = new LeagueJoinInitRespVO();

        //构建 加入公会的基本业务信息
        LeagueJoinDO joinDO = buildBaseInfo(vo, ruleVO);

        //场景一、免费 不需要审批 ,直接加入公会。(如果是场景二 付费 不需要审批， 在支付接口加入公会)
        if (!ruleVO.getEnabledAdminApproval() && ObjectUtil.equal(BigDecimal.ZERO, vo.getAmount())) {
            //插入公会成员数据
            insertLeagueMember(joinDO);
            // 新增订阅
            subscribePackage(joinDO);
        }

        //场景三 免费 需要审批
        if (ruleVO.getEnabledAdminApproval() && ObjectUtil.equal(BigDecimal.ZERO, vo.getAmount())) {
            leagueJoinMapper.insert(joinDO);
            // 需要审批 插入审批记录
            insertLeagueJoinApproval(joinDO);
            return respVO;
        }

        //场景二四 金额不为0才需要下单
        if (ObjectUtil.notEqual(BigDecimal.ZERO, vo.getAmount())) {
            //下单
            OrderCreateRspDTO order = createOrder(vo.getAmount());
            joinDO.setAppOrderNo(order.getAppOrderNo());
            joinDO.setPayStatus(PayStatusEnum.NO_PAY.getValue());
            joinDO.setPayFlag(Boolean.TRUE);

            //付费才有订单号等数据返回
            String uuid = UUID.fastUUID().toString();
            respVO.setAppOrderNo(order.getAppOrderNo());
            respVO.setFee(order.getFee());
            //支付时幂等控制
            //respVO.setUuid(uuid);
            //orderRedisRepository.setRandom(OrderType.ACTIVE_JOIN_LEAGUE, uuid, uuid);
        }

        leagueJoinMapper.insert(joinDO);
        return respVO;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void joinLeaguePay(LeagueJoinPayReqVO vo) {
        LeagueJoinDO joinDO = getJoinDoByAppOrderNo(vo.getAppOrderNo());

        //订单号不能是已支付
        if (ObjectUtil.equal(PayStatusEnum.PAY.getValue(), joinDO.getPayStatus())) {
            throw exception(LEAGUE_JOIN_ORDER_PAY);
        }
        // 校验支付密码
        verifyPassword(vo);

        //实际支付
        orderApi.pay(new PayReqDTO().setAppOrderNo(vo.getAppOrderNo()));

        //支付了更新加入公会业务订单支付状态
        LeagueJoinDO updateDo = new LeagueJoinDO();
        updateDo.setId(joinDO.getId());
        updateDo.setPayStatus(PayStatusEnum.PAY.getValue());
        updateDo.setExpireTime(LocalDateTime.now().plusHours(72L));
        leagueJoinMapper.updateById(updateDo);

        /*
        调用这个接口的都是付费入会，此时根据是否需要审批来确定状态值
        需要审批，插入审批记录表 退出
        不需要审批，付费后直接加入公会 ，分账
         */
        if (joinDO.getEnabledAdminApproval()) {
            // 需要审批 插入审批记录
            insertLeagueJoinApproval(joinDO);
        } else {
            //场景二: 付费 不需要审批
            //不需要审批,付费直接加入公会 ，分账
            insertLeagueMember(joinDO);
            // 新增订阅
            subscribePackage(joinDO);

            splitAccount(joinDO);
        }
    }

    /**
     * 公会加入订阅
     *
     * @param joinDO
     */
    private void subscribePackage(LeagueJoinDO joinDO) {
        SubscribeVo build = SubscribeVo.builder()
                .leagueId(joinDO.getLeagueId())
                .userId(joinDO.getUserId())
                .amount(joinDO.getAmount())
                .subscribeType(LeagueSubscribeType.getByCode(joinDO.getSubscribeType()))
                .build();
        subscribeService.subscribePackage(build);
    }

    @NotNull
    private LeagueJoinDO buildBaseInfo(LeagueJoinInitReqVO vo, LeagueRuleVO ruleVO) {
        //查询公会创建者id
        Long leagueAdminId = memberService.getAdminIdByLeagueId(vo.getLeagueId());

        //加入公会的业务信息
        LeagueJoinDO joinDO = new LeagueJoinDO();
        joinDO.setUserId(getLoginUserId());
        joinDO.setLeagueId(vo.getLeagueId());
        joinDO.setJoinReason(vo.getJoinReason());
        joinDO.setSubscribeType(vo.getSubscribeType().getCode());
        joinDO.setAmount(vo.getAmount());
        joinDO.setLeagueCreateId(leagueAdminId);
        joinDO.setExpireTime(LocalDateTime.now().plusHours(72L));
        joinDO.setEnabledAdminApproval(ruleVO.getEnabledAdminApproval());
        //需要审批状态为0，不需要审批为1
        joinDO.setBusinessStatus(ruleVO.getEnabledAdminApproval() ?
                LeagueJoinApprovalTypeEnum.INIT.getValue() :
                LeagueJoinApprovalTypeEnum.AGREE.getValue());
        //付费为true(默认)
        joinDO.setPayFlag(Boolean.TRUE);
        //免费支付状态设置为0 免费为false
        if (ObjectUtil.equal(BigDecimal.ZERO, vo.getAmount())) {
            joinDO.setPayStatus(0);
            joinDO.setPayFlag(Boolean.FALSE);
        }
        return joinDO;
    }


    /**
     * 分账
     *
     * @param joinDO
     */
    private void splitAccount(LeagueJoinDO joinDO) {
        SplitAccountVo accountVo = SplitAccountUtil.splitByThree(joinDO.getAmount());
        //加入公会 公会创建者、公会、平台 532分账
        ReceiverInfo leagueAdmin = new ReceiverInfo().setReceiverId(joinDO.getLeagueCreateId()).setAccountType(AccountType.USER).setAmount(accountVo.getPersonAmount());
        ReceiverInfo league = new ReceiverInfo().setReceiverId(joinDO.getLeagueId()).setAccountType(AccountType.LEAGUE).setAmount(accountVo.getLeagueAmount());
        ReceiverInfo platform = new ReceiverInfo().setReceiverId(1L).setAccountType(AccountType.PLATFORM).setAmount(accountVo.getPlatformAmount());
        SplitAccountReqDTO splitAccountReqDTO = new SplitAccountReqDTO()
                .setOrderType(OrderType.ACTIVE_JOIN_LEAGUE_SPLIT)
                .setAppOrderNo(joinDO.getAppOrderNo())
                .setReceiverList(Arrays.asList(leagueAdmin, league, platform));
        orderApi.splitAccount(splitAccountReqDTO);
    }

    /**
     * 插入公会审批表
     *
     * @param joinDO
     */
    private void insertLeagueJoinApproval(LeagueJoinDO joinDO) {
        LeagueJoinApprovalDO approvalDO = new LeagueJoinApprovalDO();
        approvalDO.setBusinessId(joinDO.getId());
        approvalDO.setUserId(joinDO.getUserId());
        approvalDO.setLeagueId(joinDO.getLeagueId());
        approvalDO.setJoinReason(joinDO.getJoinReason());
        approvalDO.setStatus(LeagueJoinApprovalTypeEnum.INIT.getValue());
        approvalDO.setExpireTime(LocalDateTime.now().plusHours(72L));
        approvalMapper.insert(approvalDO);
    }

    @Override
    public PageResult<LeagueJoinApprovalRespVO> getLeagueJoinPage(LeagueJoinApprovalPageReqVO pageVO) {
        Page<LeagueJoinApprovalRespVO> pageParam = new Page<>(pageVO.getPageNo(), pageVO.getPageSize());
        pageVO.setUserId(getLoginUserId());
        Page<LeagueJoinApprovalRespVO> resPage = leagueJoinMapper.selectJoinLeaguePage(pageParam, pageVO);

        return new PageResult<LeagueJoinApprovalRespVO>().setList(resPage.getRecords()).setTotal(resPage.getTotal());
    }

    @Lock4j(keys = {"'testLock'"})
    @Override
    public void lockTest(LeagueJoinInitReqVO vo) {
        LeagueSubscribeType subscribeType = vo.getSubscribeType();
        String joinReason = vo.getJoinReason();
        System.out.println(vo);
    }

    /**
     * 用户加入公会
     *
     * @param joinDO
     */
    private void insertLeagueMember(LeagueJoinDO joinDO) {
        //加入公会前先校验是否已经加入
        // 已加入后审批报错
        LeagueMemberDO leagueUser = memberMapper.getByLeagueIdAndUserId(joinDO.getLeagueId(), getLoginUserId());
        if (ObjectUtil.isNotNull(leagueUser)) {
            throw exception(USER_ALREADY_JOIN_LEAGUE);
        }

        LeagueMemberJoinVO joinVO = new LeagueMemberJoinVO();
        joinVO.setLeagueId(joinDO.getLeagueId());
        joinVO.setUserId(joinDO.getUserId());
        joinVO.setLevel(LeagueMemberLevelEnums.MEMBER.getType());
        joinVO.setJoinType(LeagueJoinTypeEnums.APPLY.getType());
        //不需要审批时 入会关系人为公会创建者
        joinVO.setRelationUserId(joinDO.getLeagueCreateId());
        joinVO.setJoinTime(LocalDateTime.now());
        joinVO.setUpdateTime(LocalDateTime.now());
        memberService.joinLeague(joinVO);
    }

    /**
     * 校验支付密码
     *
     * @param vo
     */
    private void verifyPassword(LeagueJoinPayReqVO vo) {
        AccountPwdVerifyDTO accountPwdVerifyDTO = new AccountPwdVerifyDTO();
        accountPwdVerifyDTO.setPassword(vo.getPassword());
        accountPwdVerifyDTO.setPublicKey(vo.getPublicKey());
        if (!userAccountApi.verifyPayPassword(accountPwdVerifyDTO)) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.PAY_PASSWORD_WRONG);
        }
    }


    /**
     * 根据订单号查询 用户加入记录
     *
     * @param appOrderNo
     * @return
     */
    private LeagueJoinDO getJoinDoByAppOrderNo(String appOrderNo) {
        if (ObjectUtil.isEmpty(appOrderNo)) {
            throw new ServiceException(ILLEGAL_PARAMS, "appOrderNo");
        }

        LeagueJoinDO joinDO = leagueJoinMapper.selectOne(LeagueJoinDO::getAppOrderNo, appOrderNo);
        if (ObjectUtil.isNull(joinDO)) {
            throw exception(ORDER_NOT_EXISTS);
        }
        return joinDO;
    }


    /**
     * 下单接口
     *
     * @param amount
     * @return
     */
    private OrderCreateRspDTO createOrder(BigDecimal amount) {
        OrderCreateReqDTO req = new OrderCreateReqDTO()
                .setOrderType(OrderType.ACTIVE_JOIN_LEAGUE)
                .setPayerId(getLoginUserId())
                .setAccountType(AccountType.USER)
                .setAmount(amount);
        return orderApi.createOrder(req);
    }

    /**
     * 加入公会前置校验
     *
     * @param vo
     */
    private LeagueRuleVO preCheck(LeagueJoinInitReqVO vo) {
        // 校验公会存在、准许加入、价格相等
        Optional.ofNullable(vo.getLeagueId()).orElseThrow(() -> new ServiceException(ILLEGAL_PARAMS, "leagueId"));

        LeagueRuleVO leagueRuleVO = ruleService.getLeagueRuleInfo(vo.getLeagueId());

        if (ObjectUtil.isNull(leagueRuleVO)) {
            throw exception(LEAGUE_NOT_EXISTS);
        }
        //公会不允许主动加入
        if (ObjectUtil.notEqual(Boolean.TRUE, leagueRuleVO.getEnabledUserJoin())) {
            throw exception(LEAGUE_NOT_JOIN);
        }

        //根据订阅类型校验价格是否变动
        validatePriceAndIsOpenByType(vo, leagueRuleVO);
        // 四种订阅价格金额区间 price=0 || price>=0.1

        //已经存在 公会申请加入记录 且未进行审批状态的 且在有效期内 不能重复申请
        LeagueJoinDO joinDO = leagueJoinMapper.getCurrentValidApproval(getLoginUserId(), vo.getLeagueId(),
                LeagueJoinApprovalTypeEnum.INIT.getValue(), LocalDateTime.now());
        if (ObjectUtil.isNotNull(joinDO)) {
            throw exception(LEAGUE_JOIN_RECORD_EXIST);
        }

        //不能重复加入
        LeagueMemberDO leagueUser = memberMapper.getByLeagueIdAndUserId(vo.getLeagueId(), WebFrameworkUtils.getLoginUserId());
        if (ObjectUtil.isNotNull(leagueUser)) {
            throw exception(USER_ALREADY_JOIN_LEAGUE);
        }

        //如果金额为null 默认为0
        if (ObjectUtil.isNull(vo.getAmount())) {
            vo.setAmount(BigDecimal.ZERO);
        }

        return leagueRuleVO;
    }

    /**
     * 根据不同订阅类型校验价格是否变动 和 是否勾选生效
     *
     * @param vo
     * @param leagueRuleVO
     */
    private void validatePriceAndIsOpenByType(LeagueJoinInitReqVO vo, LeagueRuleVO leagueRuleVO) {
        boolean priceChange = false;
        boolean isOpen = true;
        String on = "1";
        SubscribeSelectVo subscribeSelectVo = new SubscribeSelectVo(leagueRuleVO.getSubscribeSelect());
        switch (vo.getSubscribeType()) {
            case SUBSCRIBE_MONTH:
                //拉跨的写法
                /*if (ObjectUtil.notEqual(leagueRuleVO.getSubscribeMonthPrice(), vo.getAmount())) {
                    priceChange = true;
                }*/
                // 判断价格是否变动 和 判断是否勾选该方式加入
                priceChange = ObjectUtil.notEqual(leagueRuleVO.getSubscribeMonthPrice(), vo.getAmount());
                isOpen = ObjectUtil.equal(on, subscribeSelectVo.getMonth());
                break;
            case SUBSCRIBE_QUARTER:
                priceChange = ObjectUtil.notEqual(leagueRuleVO.getSubscribeQuarterPrice(), vo.getAmount());
                isOpen = ObjectUtil.equal(on, subscribeSelectVo.getQuarter());
                break;
            case SUBSCRIBE_YEAR:
                priceChange = ObjectUtil.notEqual(leagueRuleVO.getSubscribeYearPrice(), vo.getAmount());
                isOpen = ObjectUtil.equal(on, subscribeSelectVo.getYear());
                break;
            case SUBSCRIBE_FOREVER:
                priceChange = ObjectUtil.notEqual(leagueRuleVO.getSubscribeForeverPrice(), vo.getAmount());
                isOpen = ObjectUtil.equal(on, subscribeSelectVo.getForever());
                break;
            default:
                throw exception(BusinessErrorCodeConstants.SUBSCRIBE_TYPE_ERROR);
        }
        if (priceChange) {
            throw exception(BusinessErrorCodeConstants.LEAGUE_AMOUNT_CHANGE);
        }
        if (!isOpen) {
            throw exception(BusinessErrorCodeConstants.LEAGUE_SUBSCRIBE_TYPE_OFF);
        }

        // 校验价格是否符合规则
        boolean flag = checkIsPriceRange(vo.getAmount());
        if (!flag) {
            throw exception(BusinessErrorCodeConstants.ABNORMAL_AMOUNT);
        }
    }

    /**
     * 校验价格是否符合规则
     * 四种订阅价格金额区间 price=0 || price>=0.1
     *
     * @param price
     * @return true 符合 false 不符合
     */
    private boolean checkIsPriceRange(BigDecimal price) {
        if (price == null) {
            price = BigDecimal.ZERO;
        }
        BigDecimal zeroPointOne = new BigDecimal("0.1");
        int res = price.compareTo(zeroPointOne);

        return ObjectUtil.equal(BigDecimal.ZERO, price) || res >= 0;
    }
}
