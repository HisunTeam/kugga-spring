package com.hisun.kugga.duke.pay.service.redpacket;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.entity.ContentParamVo;
import com.hisun.kugga.duke.enums.*;
import com.hisun.kugga.duke.enums.message.MessageDealStatusEnum;
import com.hisun.kugga.duke.enums.message.MessageSceneEnum;
import com.hisun.kugga.duke.enums.message.MessageTemplateEnum;
import com.hisun.kugga.duke.enums.message.MessageTypeEnum;
import com.hisun.kugga.duke.league.api.LeagueApi;
import com.hisun.kugga.duke.league.api.dto.leaguemember.BonusUserDTO;
import com.hisun.kugga.duke.league.api.dto.leaguenotice.LeagueNoticeDTO;
import com.hisun.kugga.duke.league.api.leaguenotice.LeagueNoticeApi;
import com.hisun.kugga.duke.pay.api.order.dto.RedPacketApplyReqDTO;
import com.hisun.kugga.duke.pay.api.order.dto.RedPacketApplyRspDTO;
import com.hisun.kugga.duke.pay.api.order.dto.RedPacketReceiverInfo;
import com.hisun.kugga.duke.pay.api.wallet.WalletApi;
import com.hisun.kugga.duke.pay.api.wallet.dto.*;
import com.hisun.kugga.duke.pay.bo.BillBO;
import com.hisun.kugga.duke.pay.controller.app.redpacket.vo.BonusCalculateRspVO;
import com.hisun.kugga.duke.pay.controller.app.redpacket.vo.RedPacketInfo;
import com.hisun.kugga.duke.pay.dal.dataobject.redpacket.RedPacketDO;
import com.hisun.kugga.duke.pay.dal.dataobject.redpacketorder.RedPacketOrderDO;
import com.hisun.kugga.duke.pay.dal.dataobject.redpacketorderdetail.RedPacketOrderDetailDO;
import com.hisun.kugga.duke.pay.dal.mysql.redpacket.RedPacketMapper;
import com.hisun.kugga.duke.pay.dal.mysql.redpacketorder.RedPacketOrderMapper;
import com.hisun.kugga.duke.pay.dal.mysql.redpacketorderdetail.RedPacketOrderDetailMapper;
import com.hisun.kugga.duke.pay.service.leagueaccount.LeagueAccountService;
import com.hisun.kugga.duke.pay.service.useraccount.UserAccountService;
import com.hisun.kugga.duke.pay.service.userbill.BillService;
import com.hisun.kugga.duke.system.api.message.SendMessageApi;
import com.hisun.kugga.duke.vo.RedPacketDetailRspVO;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.util.amount.AmountUtil;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hisun.kugga.duke.common.CommonConstants.SNOWFLAKE;

/**
 * @author: zhou_xiong
 */
@Service
public class RedPacketServiceImpl implements RedPacketService {
    private static final BigDecimal MIN_BOUND = new BigDecimal("0.01");
    @Resource
    private WalletApi walletApi;
    @Resource
    private RedPacketOrderMapper redPacketOrderMapper;
    @Resource
    private UserAccountService accountService;
    @Resource
    private RedPacketOrderDetailMapper redPacketOrderDetailMapper;
    @Resource
    private BillService billService;
    @Resource
    private LeagueApi leagueApi;
    @Resource
    private LeagueAccountService leagueAccountService;
    @Resource
    private RedPacketMapper redPacketMapper;
    @Resource
    private SendMessageApi sendMessageApi;
    @Resource
    private LeagueNoticeApi leagueNoticeApi;

    @Override
    public BonusCalculateRspVO calculate(Long leagueId) {
        // 判断当前登录人是否是该公会管理员
        Boolean leagueAdmin = leagueApi.isLeagueAdmin(leagueId, SecurityFrameworkUtils.getLoginUserId());
        if (!leagueAdmin) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.LEAGUE_NOT_ADMIN);
        }
        // 获取有资格分到红包的公会用户
        List<BonusUserDTO> bonusUserDTOList = leagueApi.canBonus(leagueId);
        // 获取公会当前可发红包金额
        BigDecimal balance = leagueAccountService.getLeagueBalance(leagueId);
        // 红包金额计算
        List<RedPacketInfo> redPacketInfos = doCalculate(balance, bonusUserDTOList);
        // 保存红包分配结果
        RedPacketDO redPacketDO = new RedPacketDO()
                .setUserId(SecurityFrameworkUtils.getLoginUserId())
                .setLeagueId(leagueId)
                .setRedPacketParam(JSONUtil.toJsonStr(redPacketInfos))
                .setAmount(balance)
                .setBusinessStatus(RedPacketStatus.INIT);
        redPacketMapper.insert(redPacketDO);
        return new BonusCalculateRspVO()
                .setRedPacketInfos(redPacketInfos)
                .setRedPacketId(redPacketDO.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RedPacketApplyRspDTO handout(Long redPacketId) {
        // 查询红包记录
        RedPacketDO redPacketDO = redPacketMapper.selectById(redPacketId);
        if (!redPacketDO.getUserId().equals(SecurityFrameworkUtils.getLoginUserId())) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.ILLEGAL_OPERATOR);
        }
        // 获取公会当前可发红包金额
        BigDecimal balance = leagueAccountService.getLeagueBalance(redPacketDO.getLeagueId());
        if (balance.compareTo(redPacketDO.getAmount()) < 0) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.LEAGUE_BALANCE_NOT_ENOUGH);
        }
        List<RedPacketReceiverInfo> redPacketReceiverInfos = JSONObject.parseArray(redPacketDO.getRedPacketParam(), RedPacketInfo.class)
                .stream().map(redPacketInfo ->
                        new RedPacketReceiverInfo()
                                .setReceiverId(redPacketInfo.getUserId())
                                .setAmount(redPacketInfo.getAmount())
                                .setAccountType(AccountType.USER)
                ).collect(Collectors.toList());
        RedPacketApplyReqDTO redPacketApplyReqDTO = new RedPacketApplyReqDTO()
                .setPayerId(redPacketDO.getLeagueId())
                .setAccountType(AccountType.LEAGUE)
                .setReceiverInfoList(redPacketReceiverInfos);
        RedPacketApplyRspDTO rspDTO = redPacketApply(redPacketApplyReqDTO);
        // 保存内部订单号
        redPacketMapper.updateById(new RedPacketDO().setId(redPacketDO.getId())
                .setAppOrderNo(rspDTO.getAppOrderNo()).setBusinessStatus(RedPacketStatus.ORDERED));
        return rspDTO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RedPacketApplyRspDTO redPacketApply(RedPacketApplyReqDTO redPacketApplyReqDTO) {
        // 红包总金额
        BigDecimal redPacketAmount = redPacketApplyReqDTO.getReceiverInfoList().stream().map(RedPacketReceiverInfo::getAmount)
                .filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 查询钱包账户
        String accountId = accountService.findWalletAccount(redPacketApplyReqDTO.getAccountType(), redPacketApplyReqDTO.getPayerId());
        // 红包发放金额不能大于账户余额
        accountService.balanceEnough(redPacketAmount, accountId);
        // 红包订单详情
        String appOrderNo = SNOWFLAKE.nextIdStr();
        List<RedPacketReceiver> redPacketReceivers = new ArrayList<>(16);
        List<RedPacketOrderDetailDO> collect = redPacketApplyReqDTO.getReceiverInfoList().stream().map(redPacketReceiverInfo -> {
            String walletAccount = accountService.findWalletAccount(redPacketReceiverInfo.getAccountType(), redPacketReceiverInfo.getReceiverId());
            RedPacketReceiver redPacketReceiver = new RedPacketReceiver().setAccount(walletAccount)
                    .setBalance(AmountUtil.yuanToFen(redPacketReceiverInfo.getAmount()));
            redPacketReceivers.add(redPacketReceiver);
            return new RedPacketOrderDetailDO()
                    .setAppOrderNo(appOrderNo)
                    .setReceiverId(redPacketReceiverInfo.getReceiverId())
                    .setAccountType(redPacketReceiverInfo.getAccountType())
                    .setAccountId(walletAccount)
                    .setAmount(redPacketReceiverInfo.getAmount());
        }).collect(Collectors.toList());
        // 钱包红包申请
        RedPacketApplyReqBody redPacketApplyReqBody = new RedPacketApplyReqBody()
                .setAccount(accountId)
                .setBalance(AmountUtil.yuanToFen(redPacketAmount))
                .setRedPacketCnt(redPacketApplyReqDTO.getReceiverInfoList().size())
                .setCallbackUrl("https://www.baidu.com")
                .setReceiverList(redPacketReceivers);
        RedPacketApplyRspBody redPacketApplyRspBody = walletApi.redPacketApply(redPacketApplyReqBody);
        // 保存红包订单
        RedPacketOrderDO redPacketOrderDO = new RedPacketOrderDO()
                .setAppOrderNo(appOrderNo)
                .setWalletOrderNo(redPacketApplyRspBody.getOrderNo())
                .setPayerId(redPacketApplyReqDTO.getPayerId())
                .setAccountId(accountId)
                .setAccountType(redPacketApplyReqDTO.getAccountType())
                .setAmount(redPacketAmount)
                .setStatus(RedPacketOrderStatus.DRAFT);
        redPacketOrderMapper.insert(redPacketOrderDO);
        // 保存红包订单详情
        redPacketOrderDetailMapper.insertBatch(collect);
        return new RedPacketApplyRspDTO().setAppOrderNo(appOrderNo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RedPacketDetailRspVO getRedPacketDetail(String appOrderNo, boolean fromBatch) {
        // 通过内部订单号查询红包发放订单
        LambdaQueryWrapper<RedPacketOrderDO> queryWrapper = new LambdaQueryWrapper<RedPacketOrderDO>()
                .eq(RedPacketOrderDO::getAppOrderNo, appOrderNo);
        if (!fromBatch) {
            queryWrapper.eq(RedPacketOrderDO::getCreator, SecurityFrameworkUtils.getLoginUserId());
        }
        RedPacketOrderDO redPacketOrderDO = redPacketOrderMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNull(redPacketOrderDO)) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.ORDER_NOT_EXISTS);
        }
        if (finalStatusJudge(redPacketOrderDO.getStatus().getKey())) {
            // 订单终态，直接返回
            return new RedPacketDetailRspVO()
                    .setAppOrderNo(redPacketOrderDO.getAppOrderNo())
                    .setStatus(redPacketOrderDO.getStatus().getKey());
        } else {
            // 查询钱包红包发放状态
            RedPacketDetailRspBody redPacketDetailRspBody = walletApi.redPacketDetail(new RedPacketDetailReqBody()
                    .setOrderNo(redPacketOrderDO.getWalletOrderNo()));
            if (finalStatusJudge(redPacketDetailRspBody.getStatus())) {
                statusProcess(appOrderNo, redPacketDetailRspBody);
            }
            if (StrUtil.equals(RedPacketOrderStatus.SUCCESS.getKey(), redPacketDetailRspBody.getStatus())) {
                billProcess(appOrderNo, redPacketOrderDO);
                notifyProcess(appOrderNo, redPacketOrderDO);
            }
            return new RedPacketDetailRspVO()
                    .setAppOrderNo(redPacketOrderDO.getAppOrderNo())
                    .setStatus(redPacketDetailRspBody.getStatus());
        }
    }

    private void notifyProcess(String appOrderNo, RedPacketOrderDO redPacketOrderDO) {
        // 通过内部订单号查询红包信息
        RedPacketDO redPacketDO = redPacketMapper.selectOne(new LambdaQueryWrapper<RedPacketDO>().eq(RedPacketDO::getAppOrderNo, appOrderNo));
        List<RedPacketInfo> redPacketInfoList = JSONObject.parseArray(redPacketDO.getRedPacketParam(), RedPacketInfo.class);
        LeagueNoticeDTO leagueNoticeDTO = new LeagueNoticeDTO()
                .setLeagueId(redPacketOrderDO.getPayerId())
                .setType(LeagueNoticeTypeEnum.NOTICE_TYPE_8)
                .setStatus(LeagueNoticeStatusEnum.NOTICE_STATUS_3)
                .setAmount(redPacketOrderDO.getAmount());
        // 发公告栏消息
        leagueNoticeApi.notice(leagueNoticeDTO);
        redPacketInfoList.stream().forEach(redPacketInfo -> {
            SendMessageReqDTO message = SendMessageReqDTO.builder()
                    .messageTemplate(MessageTemplateEnum.LEAGUE_RED_PACKAGE)
                    .messageScene(MessageSceneEnum.LEAGUE_RED_PACKAGE)
                    .messageType(MessageTypeEnum.INVITE)
                    .businessId(redPacketOrderDO.getId())
                    .receivers(Arrays.asList(redPacketInfo.getUserId()))
                    .messageParam(
                            new ContentParamVo()
                                    .setInitiatorLeagueId(redPacketDO.getLeagueId())
                                    .setInitiatorId(redPacketDO.getUserId())
                                    .setReceiverId(redPacketInfo.getUserId())
                                    .setAmount(redPacketInfo.getAmount())
                    )
                    .language(LanguageEnum.en_US)
                    .dealStatus(MessageDealStatusEnum.NO_DEAL)
                    .build();
            // 发个人中心消息
            sendMessageApi.sendMessage(message);
        });
    }

    private void billProcess(String appOrderNo, RedPacketOrderDO redPacketOrderDO) {
        // 生成付款方账单
        BillBO billBO = new BillBO()
                .setAccountType(redPacketOrderDO.getAccountType())
                .setWalletOrderNo(redPacketOrderDO.getWalletOrderNo())
                .setAmount(redPacketOrderDO.getAmount().negate())
                .setRemark(OrderType.BONUS.getDesc())
                .setObjectId(redPacketOrderDO.getPayerId());
        billService.saveBillByAccountType(billBO);
        // 查询红包详情
        List<RedPacketOrderDetailDO> packetOrderDetailDOList = redPacketOrderDetailMapper.selectList(new LambdaQueryWrapper<RedPacketOrderDetailDO>()
                .select(RedPacketOrderDetailDO::getReceiverId, RedPacketOrderDetailDO::getAccountType, RedPacketOrderDetailDO::getAmount)
                .eq(RedPacketOrderDetailDO::getAppOrderNo, appOrderNo));
        // 生成收款方账单
        packetOrderDetailDOList.stream().forEach(detail -> {
            BillBO bill = new BillBO()
                    .setAccountType(detail.getAccountType())
                    .setWalletOrderNo(redPacketOrderDO.getWalletOrderNo())
                    .setAmount(detail.getAmount())
                    .setRemark(OrderType.BONUS.getDesc())
                    .setObjectId(detail.getReceiverId());
            billService.saveBillByAccountType(bill);
        });
    }

    private void statusProcess(String appOrderNo, RedPacketDetailRspBody redPacketDetailRspBody) {
        // 修改红包订单状态
        redPacketOrderMapper.update(null, new LambdaUpdateWrapper<RedPacketOrderDO>()
                .set(RedPacketOrderDO::getStatus, RedPacketOrderStatus.getByKey(redPacketDetailRspBody.getStatus()))
                .set(RedPacketOrderDO::getCompleteTime, redPacketDetailRspBody.getCompleteTime())
                .eq(RedPacketOrderDO::getAppOrderNo, appOrderNo)
                .ne(RedPacketOrderDO::getStatus, RedPacketOrderStatus.getByKey(redPacketDetailRspBody.getStatus())));
        // 修改红包状态
        RedPacketStatus redPacketStatus = redPacketDetailRspBody.getStatus().equals(RedPacketOrderStatus.SUCCESS.getKey()) ?
                RedPacketStatus.SUCCESS : RedPacketStatus.FAILED;
        redPacketMapper.update(null, new LambdaUpdateWrapper<RedPacketDO>()
                .set(RedPacketDO::getBusinessStatus, redPacketStatus)
                .eq(RedPacketDO::getAppOrderNo, appOrderNo)
                .ne(RedPacketDO::getBusinessStatus, redPacketStatus));
    }

    private boolean finalStatusJudge(String status) {
        return StrUtil.equalsAny(status, RedPacketOrderStatus.FAILED.getKey(),
                RedPacketOrderStatus.SUCCESS.getKey());
    }

    /**
     * 红包金额计算
     *
     * @param balance
     * @param bonusUserDTOList
     * @return
     */
    private List<RedPacketInfo> doCalculate(BigDecimal balance, List<BonusUserDTO> bonusUserDTOList) {
        if (CollectionUtil.isEmpty(bonusUserDTOList)) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.NO_USER_MET_CONDITION);
        }
        if (balance.compareTo(BigDecimal.ZERO) == 0) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.BONUS_AMOUNT_NOT_ENOUGH);
        }
        // 总成长值
        Long allGrowthValue = bonusUserDTOList.stream().map(BonusUserDTO::getGrowthValue)
                .reduce(0L, (a, b) -> a + b);
        // 已分配金额
        BigDecimal hasSplit = BigDecimal.ZERO;
        List<RedPacketInfo> redPacketInfos = new ArrayList<>(bonusUserDTOList.size());
        for (int i = 0; i < bonusUserDTOList.size(); i++) {
            BonusUserDTO bonusUserDTO = bonusUserDTOList.get(i);
            Long growthValue = bonusUserDTO.getGrowthValue();
            RedPacketInfo redPacketInfo = BeanUtil.copyProperties(bonusUserDTO, RedPacketInfo.class);
            if (i < bonusUserDTOList.size() - 1) {
                BigDecimal amount = AmountUtil.mul(balance, NumberUtil.div(growthValue, allGrowthValue));
                if (i == 0) {
                    // 可发放金额最小额度判断（成长值最小的那个人最少要分到0.01元）
                    if (amount.compareTo(MIN_BOUND) < 0) {
                        ServiceException.throwServiceException(BusinessErrorCodeConstants.BONUS_AMOUNT_NOT_ENOUGH);
                    }
                }
                redPacketInfo.setAmount(amount);
                hasSplit = AmountUtil.add(hasSplit, amount);
            } else {
                // 成长值最高的用户获得金额 = 红包可分配总金额-已分配金额
                BigDecimal highest = AmountUtil.sub(balance, hasSplit);
                if (highest.compareTo(BigDecimal.ZERO) <= 0) {
                    ServiceException.throwServiceException(BusinessErrorCodeConstants.BONUS_AMOUNT_NOT_ENOUGH);
                }
                redPacketInfo.setAmount(highest);
            }
            redPacketInfos.add(redPacketInfo);
        }
        return CollectionUtil.reverse(redPacketInfos);
    }
}
