package com.hisun.kugga.duke.batch.channel.wallet;

import com.hisun.kugga.duke.batch.channel.wallet.dto.*;
import com.hisun.kugga.framework.common.enums.WalletEnum;
import com.hisun.kugga.framework.pay.core.client.impl.wallet.WalletInvoker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: zhou_xiong
 */
@Slf4j
@Component
public class WalletClient {

    @Resource
    private WalletInvoker walletInvoker;

    /**
     * Query recharge details
     *
     * @param chargeDetailReqBody
     * @return
     */
    public ChargeDetailRspBody chargeDetail(ChargeDetailReqBody chargeDetailReqBody) {
        log.info("Query recharge details: {}", chargeDetailReqBody);
        String chargeDetailUrl = walletInvoker.getUrl(WalletEnum.CHARGE_DETAIL);
        return walletInvoker.invokePost(chargeDetailUrl, chargeDetailReqBody, ChargeDetailRspBody.class);
    }

    public AllocationResultRspBody allocationResult(AllocationResultReqBody allocationResultReqBody) {
        log.info("Query allocation result: {}", allocationResultReqBody);
        String allocationResultUrl = walletInvoker.getUrl(WalletEnum.ALLOCATION_RESULT);
        return walletInvoker.invokePost(allocationResultUrl, allocationResultReqBody, AllocationResultRspBody.class);
    }

    public DrawbackDetailRspBody drawbackDetail(DrawbackDetailReqBody drawbackDetailReqBody) {
        log.info("Query refund details: {}", drawbackDetailReqBody);
        String drawbackDetailUrl = walletInvoker.getUrl(WalletEnum.DRAWBACK_DETAIL);
        return walletInvoker.invokePost(drawbackDetailUrl, drawbackDetailReqBody, DrawbackDetailRspBody.class);
    }

    public PayDetailRspBody payDetail(PayDetailReqBody payDetailReqBody) {
        log.info("Query payment details: {}", payDetailReqBody);
        String payDetailUrl = walletInvoker.getUrl(WalletEnum.PAYMENT_DETAIL);
        return walletInvoker.invokePost(payDetailUrl, payDetailReqBody, PayDetailRspBody.class);
    }

    public WithdrawDetailRspBody withdrawDetail(WithdrawDetailReqBody withdrawDetailReqBody) {
        log.info("Query withdrawal details: {}", withdrawDetailReqBody);
        String withdrawDetailUrl = walletInvoker.getUrl(WalletEnum.WITHDRAW_DETAIL);
        return walletInvoker.invokePost(withdrawDetailUrl, withdrawDetailReqBody, WithdrawDetailRspBody.class);
    }

    public DrawbackApplyRspBody drawbackApply(DrawbackApplyReqBody drawbackApplyReqBody) {
        log.info("Request refund: {}", drawbackApplyReqBody);
        String drawBackApplyUrl = walletInvoker.getUrl(WalletEnum.DRAWBACK_APPLY);
        return walletInvoker.invokePost(drawBackApplyUrl, drawbackApplyReqBody, DrawbackApplyRspBody.class);
    }

    public RedPacketDetailRspBody redPacketDetail(RedPacketDetailReqBody redPacketDetailReqBody) {
        log.info("Query red packet issuance result: {}", redPacketDetailReqBody);
        String redPacketDetailUrl = walletInvoker.getUrl(WalletEnum.REDPACKET_DETAIL);
        return walletInvoker.invokePost(redPacketDetailUrl, redPacketDetailReqBody, RedPacketDetailRspBody.class);
    }
}