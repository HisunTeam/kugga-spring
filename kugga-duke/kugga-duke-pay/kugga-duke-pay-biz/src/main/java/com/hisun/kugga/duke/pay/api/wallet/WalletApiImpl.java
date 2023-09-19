package com.hisun.kugga.duke.pay.api.wallet;

import com.hisun.kugga.duke.pay.api.wallet.dto.*;
import com.hisun.kugga.framework.common.enums.WalletEnum;
import com.hisun.kugga.framework.pay.core.client.impl.wallet.WalletInvoker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author: zhou_xiong
 */
@Slf4j
@Service
public class WalletApiImpl implements WalletApi {
    @Resource
    private WalletInvoker walletInvoker;

    @Override
    public AppDetailRspBody appDetail(AppDetailReqBody appDetailReqBody) {
        log.info("app费率详情:{}", appDetailReqBody);
        String appDetailUrl = walletInvoker.getUrl(WalletEnum.APP_DETAIL);
        return walletInvoker.invokePost(appDetailUrl, appDetailReqBody, AppDetailRspBody.class);
    }

    @Override
    public CreateAccountRspBody createAccount(CreateAccountReqBody createAccountReqBody) {
        log.info("账户建立:{}", createAccountReqBody);
        String createAccountUrl = walletInvoker.getUrl(WalletEnum.CREATE_ACCOUNT);
        return walletInvoker.invokePost(createAccountUrl, createAccountReqBody, CreateAccountRspBody.class);
    }

    @Override
    public AccountDetailRspBody accountDetail(AccountDetailReqBody accountDetailReqBody) {
        log.info("账户详情查询:{}", accountDetailReqBody);
        String accountDetailUrl = walletInvoker.getUrl(WalletEnum.ACCOUNT_DETAIL);
        return walletInvoker.invokePost(accountDetailUrl, accountDetailReqBody, AccountDetailRspBody.class);
    }

    @Override
    public PrepayRspBody prepay(PrepayReqBody prepayReqBody) {
        log.info("预消费支付:{}", prepayReqBody);
        String prepayUrl = walletInvoker.getUrl(WalletEnum.PAYMENT_PREPAY);
        return walletInvoker.invokePost(prepayUrl, prepayReqBody, PrepayRspBody.class);
    }

    @Override
    public PayByBalanceRspBody payByBalance(PayByBalanceReqBody payByBalanceReqBody) {
        log.info("消费-余额支付:{}", payByBalanceReqBody);
        String payByBalanceUrl = walletInvoker.getUrl(WalletEnum.PAYMENT_PAY_BY_BALANCE);
        return walletInvoker.invokePost(payByBalanceUrl, payByBalanceReqBody, PayByBalanceRspBody.class);
    }

    @Override
    public void payCancel(PaymentCancelReqBody paymentCancelReqBody) {
        log.info("关闭消费支付:{}", paymentCancelReqBody);
        String payCancelUrl = walletInvoker.getUrl(WalletEnum.PAYMENT_CANCEL);
        walletInvoker.invokePost(payCancelUrl, paymentCancelReqBody, Object.class);
    }

    @Override
    public PreChargeRspBody preCharge(PreChargeReqBody preChargeReqBody) {
        log.info("预充值:{}", preChargeReqBody);
        String preChargeUrl = walletInvoker.getUrl(WalletEnum.CHARGE_PRECHARGE);
        return walletInvoker.invokePost(preChargeUrl, preChargeReqBody, PreChargeRspBody.class);
    }

    @Override
    public ChargeDetailRspBody chargeDetail(ChargeDetailReqBody chargeDetailReqBody) {
        log.info("查询充值详情:{}", chargeDetailReqBody);
        String chargeDetailUrl = walletInvoker.getUrl(WalletEnum.CHARGE_DETAIL);
        return walletInvoker.invokePost(chargeDetailUrl, chargeDetailReqBody, ChargeDetailRspBody.class);
    }

    @Override
    public void chargeCancel(ChargeCancelReqBody chargeCancelReqBody) {
        log.info("取消充值:{}", chargeCancelReqBody);
        String chargeCancelUrl = walletInvoker.getUrl(WalletEnum.CHARGE_CANCEL);
        walletInvoker.invokePost(chargeCancelUrl, chargeCancelReqBody, Object.class);
    }

    @Override
    public AllocationApplyRspBody allocationApply(AllocationApplyReqBody allocationApplyReqBody) {
        log.info("请求分账:{}", allocationApplyReqBody);
        String allocationApplyUrl = walletInvoker.getUrl(WalletEnum.ALLOCATION_APPLY);
        return walletInvoker.invokePost(allocationApplyUrl, allocationApplyReqBody, AllocationApplyRspBody.class);
    }

    @Override
    public AllocationResultRspBody allocationResult(AllocationResultReqBody allocationResultReqBody) {
        log.info("分账结果查询:{}", allocationResultReqBody);
        String allocationResultUrl = walletInvoker.getUrl(WalletEnum.ALLOCATION_RESULT);
        return walletInvoker.invokePost(allocationResultUrl, allocationResultReqBody, AllocationResultRspBody.class);
    }

    @Override
    public DrawbackApplyRspBody drawbackApply(@Valid DrawbackApplyReqBody drawbackApplyReqBody) {
        log.info("请求退款:{}", drawbackApplyReqBody);
        String drawBackApplyUrl = walletInvoker.getUrl(WalletEnum.DRAWBACK_APPLY);
        return walletInvoker.invokePost(drawBackApplyUrl, drawbackApplyReqBody, DrawbackApplyRspBody.class);
    }

    @Override
    public WithdrawToPayPalRspBody withdrawToPayPal(@Valid WithdrawToPayPalReqBody withdrawToPayPalReqBody) {
        log.info("请求提现:{}", withdrawToPayPalReqBody);
        String withdrawApplyUrl = walletInvoker.getUrl(WalletEnum.WITHDRAW_TO_PAYPAL);
        return walletInvoker.invokePost(withdrawApplyUrl, withdrawToPayPalReqBody, WithdrawToPayPalRspBody.class);
    }

    @Override
    public WithdrawDetailRspBody withdrawDetail(@Valid WithdrawDetailReqBody withdrawDetailReqBody) {
        log.info("查询提现详情:{}", withdrawDetailReqBody);
        String withdrawDetailUrl = walletInvoker.getUrl(WalletEnum.WITHDRAW_DETAIL);
        return walletInvoker.invokePost(withdrawDetailUrl, withdrawDetailReqBody, WithdrawDetailRspBody.class);
    }

    @Override
    public RedPacketApplyRspBody redPacketApply(@Valid RedPacketApplyReqBody redPacketApplyReqBody) {
        log.info("红包发放申请:{}", redPacketApplyReqBody);
        String redPacketApplyUrl = walletInvoker.getUrl(WalletEnum.REDPACKET_HANDOUT);
        return walletInvoker.invokePost(redPacketApplyUrl, redPacketApplyReqBody, RedPacketApplyRspBody.class);
    }

    @Override
    public RedPacketDetailRspBody redPacketDetail(@Valid RedPacketDetailReqBody redPacketDetailReqBody) {
        log.info("查询红包发放结果:{}", redPacketDetailReqBody);
        String redPacketDetailUrl = walletInvoker.getUrl(WalletEnum.REDPACKET_DETAIL);
        return walletInvoker.invokePost(redPacketDetailUrl, redPacketDetailReqBody, RedPacketDetailRspBody.class);
    }


}
