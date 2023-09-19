package com.hisun.kugga.duke.pay.api.wallet;

import com.hisun.kugga.duke.pay.api.wallet.dto.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * @author: zhou_xiong
 */
@Validated
public interface WalletApi {
    /**
     * app费率详情
     *
     * @param appDetailReqBody
     * @return
     */
    AppDetailRspBody appDetail(AppDetailReqBody appDetailReqBody);

    /**
     * 用户/公会创建钱包账户
     *
     * @param createAccountReqBody
     * @return
     */
    CreateAccountRspBody createAccount(CreateAccountReqBody createAccountReqBody);

    /**
     * 账户详情查询
     *
     * @param accountDetailReqBody
     * @return
     */
    AccountDetailRspBody accountDetail(@Valid AccountDetailReqBody accountDetailReqBody);

    /**
     * 预支付
     *
     * @param prepayReqBody
     * @return
     */
    PrepayRspBody prepay(@Valid PrepayReqBody prepayReqBody);

    /**
     * 消费-余额支付
     *
     * @param payByBalanceReqBody
     * @return
     */
    PayByBalanceRspBody payByBalance(@Valid PayByBalanceReqBody payByBalanceReqBody);

    /**
     * 关闭消费支付
     *
     * @param paymentCancelReqBody
     */
    void payCancel(@Valid PaymentCancelReqBody paymentCancelReqBody);

    /**
     * 预充值
     *
     * @param preChargeReqBody
     * @return
     */
    PreChargeRspBody preCharge(@Valid PreChargeReqBody preChargeReqBody);

    /**
     * 查询充值详情
     *
     * @param chargeDetailReqBody
     * @return
     */
    ChargeDetailRspBody chargeDetail(@Valid ChargeDetailReqBody chargeDetailReqBody);

    /**
     * 取消未完成的充值
     *
     * @param chargeCancelReqBody
     */
    void chargeCancel(@Valid ChargeCancelReqBody chargeCancelReqBody);

    /**
     * 请求分账
     *
     * @param allocationApplyReqBody
     * @return
     */
    AllocationApplyRspBody allocationApply(@Valid AllocationApplyReqBody allocationApplyReqBody);

    /**
     * 分账结果查询
     *
     * @param allocationResultReqBody
     * @return
     */
    AllocationResultRspBody allocationResult(@Valid AllocationResultReqBody allocationResultReqBody);

    /**
     * 请求退款
     *
     * @param drawbackApplyReqBody
     * @return
     */
    DrawbackApplyRspBody drawbackApply(@Valid DrawbackApplyReqBody drawbackApplyReqBody);

    /**
     * 提现至PayPal
     *
     * @param withdrawToPayPalReqBody
     * @return
     */
    WithdrawToPayPalRspBody withdrawToPayPal(@Valid WithdrawToPayPalReqBody withdrawToPayPalReqBody);

    /**
     * 查询提现详情
     *
     * @param withdrawDetailReqBody
     * @return
     */
    WithdrawDetailRspBody withdrawDetail(@Valid WithdrawDetailReqBody withdrawDetailReqBody);

    /**
     * 红包发放申请
     *
     * @param redPacketApplyReqBody
     * @return
     */
    RedPacketApplyRspBody redPacketApply(@Valid RedPacketApplyReqBody redPacketApplyReqBody);

    /**
     * 查询红包发放结果
     *
     * @param redPacketDetailReqBody
     * @return
     */
    RedPacketDetailRspBody redPacketDetail(@Valid RedPacketDetailReqBody redPacketDetailReqBody);
}
