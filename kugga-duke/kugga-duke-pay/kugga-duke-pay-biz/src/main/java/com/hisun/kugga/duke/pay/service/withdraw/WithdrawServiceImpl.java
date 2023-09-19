package com.hisun.kugga.duke.pay.service.withdraw;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.common.CommonConstants;
import com.hisun.kugga.duke.enums.OrderType;
import com.hisun.kugga.duke.enums.WithdrawChannel;
import com.hisun.kugga.duke.enums.WithdrawStatus;
import com.hisun.kugga.duke.pay.api.useraccount.UserAccountApi;
import com.hisun.kugga.duke.pay.api.useraccount.dto.AccountPwdVerifyDTO;
import com.hisun.kugga.duke.pay.api.wallet.WalletApi;
import com.hisun.kugga.duke.pay.api.wallet.dto.WithdrawDetailReqBody;
import com.hisun.kugga.duke.pay.api.wallet.dto.WithdrawDetailRspBody;
import com.hisun.kugga.duke.pay.api.wallet.dto.WithdrawToPayPalReqBody;
import com.hisun.kugga.duke.pay.api.wallet.dto.WithdrawToPayPalRspBody;
import com.hisun.kugga.duke.pay.controller.app.withdraw.vo.WithdrawDetailRspVO;
import com.hisun.kugga.duke.pay.controller.app.withdraw.vo.WithdrawToPayPalReqVO;
import com.hisun.kugga.duke.pay.controller.app.withdraw.vo.WithdrawToPayPalRspVO;
import com.hisun.kugga.duke.pay.dal.dataobject.userbill.UserBillDO;
import com.hisun.kugga.duke.pay.dal.dataobject.withdraworder.WithdrawOrderDO;
import com.hisun.kugga.duke.pay.dal.mysql.withdraworder.WithdrawOrderMapper;
import com.hisun.kugga.duke.pay.service.useraccount.UserAccountService;
import com.hisun.kugga.duke.pay.service.userbill.UserBillService;
import com.hisun.kugga.duke.pay.utils.Sm2Util;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.util.amount.AmountUtil;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static com.hisun.kugga.duke.common.CommonConstants.SNOWFLAKE;

/**
 * @author: zhou_xiong
 */
@Service
public class WithdrawServiceImpl implements WithdrawService {
    private static final BigDecimal WITHDRAW_MIN = new BigDecimal("1");
    @Resource
    private UserAccountService userAccountService;
    @Resource
    private WalletApi walletApi;
    @Resource
    private WithdrawOrderMapper withdrawOrderMapper;
    @Resource
    private UserBillService userBillService;
    @Resource
    private UserAccountApi userAccountApi;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public WithdrawToPayPalRspVO toPayPal(WithdrawToPayPalReqVO withdrawToPayPalReqVO) {
        if (!StrUtil.equals(withdrawToPayPalReqVO.getPayPalNo(), withdrawToPayPalReqVO.getRePayPalNo())) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.CARD_DIFFERENT);
        }
        if (withdrawToPayPalReqVO.getAmount().compareTo(WITHDRAW_MIN) < 0) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.WITHDRAW_MIN_ERROR);
        }
        // 校验支付密码
        AccountPwdVerifyDTO accountPwdVerifyDTO = new AccountPwdVerifyDTO();
        accountPwdVerifyDTO.setPassword(withdrawToPayPalReqVO.getPayPassword());
        accountPwdVerifyDTO.setPublicKey(withdrawToPayPalReqVO.getPublicKey());
        if (!userAccountApi.verifyPayPassword(accountPwdVerifyDTO)) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.PAY_PASSWORD_WRONG);
        }
        String accountId = SecurityFrameworkUtils.getLoginUser().getAccountId();
        String appOrderNo = CommonConstants.SNOWFLAKE.nextIdStr();
        // 判断用户余额是否足够
        BigDecimal balance = userAccountService.getBalance(accountId);
        if (balance.compareTo(withdrawToPayPalReqVO.getAmount()) < 0) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.BALANCE_NOT_ENOUGH);
        }
        // 调用钱包提现
        WithdrawToPayPalReqBody withdrawToPayPalReqBody = new WithdrawToPayPalReqBody()
                .setAccount(accountId)
                .setPaypalAccountEmail(withdrawToPayPalReqVO.getPayPalNo())
                .setAmount(AmountUtil.yuanToFen(withdrawToPayPalReqVO.getAmount()))
                .setAppOrderNo(appOrderNo);
        WithdrawToPayPalRspBody withdrawToPayPalRspBody = walletApi.withdrawToPayPal(withdrawToPayPalReqBody);
        WithdrawToPayPalRspVO withdrawToPayPalRspVO = new WithdrawToPayPalRspVO()
                .setOrderNo(withdrawToPayPalRspBody.getOrderNo())
                .setFee(AmountUtil.fenToYuan(withdrawToPayPalRspBody.getFee()));
        // 保存提现记录
        WithdrawOrderDO withdrawOrderDO = new WithdrawOrderDO()
                .setAppOrderNo(appOrderNo)
                .setWalletOrderNo(withdrawToPayPalRspBody.getOrderNo())
                .setUserId(SecurityFrameworkUtils.getLoginUserId())
                .setAccountId(accountId)
                .setAmount(withdrawToPayPalReqVO.getAmount())
                .setFee(withdrawToPayPalRspVO.getFee())
                .setWithdrawChannel(WithdrawChannel.PAYPAL)
                .setCardNo(Sm2Util.encrypt(withdrawToPayPalReqVO.getPayPalNo()))
                .setStatus(WithdrawStatus.DRAFT);
        withdrawOrderMapper.insert(withdrawOrderDO);
        return withdrawToPayPalRspVO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public WithdrawDetailRspVO getWithdrawDetail(String walletOrderNo) {
        // 通过钱包订单号查询提现订单
        WithdrawOrderDO withdrawOrderDO = withdrawOrderMapper.selectOne(new LambdaQueryWrapper<WithdrawOrderDO>()
                .eq(WithdrawOrderDO::getWalletOrderNo, walletOrderNo)
                .eq(WithdrawOrderDO::getUserId, SecurityFrameworkUtils.getLoginUserId()));
        if (ObjectUtil.isNull(withdrawOrderDO)) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.ORDER_NOT_EXISTS);
        }
        if (finalStatusJudge(withdrawOrderDO.getStatus().getKey())) {
            // 订单终态，直接返回
            return new WithdrawDetailRspVO()
                    .setOrderNo(withdrawOrderDO.getWalletOrderNo())
                    .setStatus(withdrawOrderDO.getStatus().getKey())
                    .setActualAmount(withdrawOrderDO.getActualAmount());
        } else {
            // 查询钱包充值订单状态
            WithdrawDetailReqBody withdrawDetailReqBody = new WithdrawDetailReqBody().setOrderNo(walletOrderNo);
            WithdrawDetailRspBody withdrawDetailRspBody = walletApi.withdrawDetail(withdrawDetailReqBody);
            BigDecimal actualAmount = AmountUtil.fenToYuan(withdrawDetailRspBody.getActualAmount());
            if (finalStatusJudge(withdrawDetailRspBody.getStatus())) {
                // 修改订单状态
                withdrawOrderMapper.update(null, new LambdaUpdateWrapper<WithdrawOrderDO>()
                        .set(WithdrawOrderDO::getStatus, withdrawDetailRspBody.getStatus())
                        .set(WithdrawOrderDO::getActualAmount, actualAmount)
                        .set(WithdrawOrderDO::getFee, AmountUtil.fenToYuan(withdrawDetailRspBody.getFee()))
                        .set(WithdrawOrderDO::getReceivedTime, withdrawDetailRspBody.getReceivedTime())
                        .eq(WithdrawOrderDO::getWalletOrderNo, walletOrderNo));
                if (StrUtil.equals(WithdrawStatus.SUCCESS.getKey(), withdrawDetailRspBody.getStatus())) {
                    // 生成账单
                    UserBillDO userBillDO = new UserBillDO()
                            .setBillNo(SNOWFLAKE.nextIdStr())
                            .setWalletOrderNo(withdrawOrderDO.getWalletOrderNo())
                            .setUserId(withdrawOrderDO.getUserId())
                            .setAmount(actualAmount.negate())
                            .setFee(AmountUtil.fenToYuan(withdrawDetailRspBody.getFee()).negate())
                            .setStatus(CommonConstants.BillStatus.SUCCESS)
                            .setRemark(OrderType.WITHDRAW.getDesc());
                    userBillService.insertIfNotExist(userBillDO);
                }
            }
            return new WithdrawDetailRspVO()
                    .setOrderNo(withdrawOrderDO.getWalletOrderNo())
                    .setStatus(withdrawDetailRspBody.getStatus())
                    .setActualAmount(actualAmount);
        }
    }

    private boolean finalStatusJudge(String status) {
        return StrUtil.equalsAny(status, WithdrawStatus.SUCCESS.getKey(),
                WithdrawStatus.FAILED.getKey(), WithdrawStatus.CLOSED.getKey());
    }
}
