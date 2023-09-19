package com.hisun.kugga.duke.pay.service.payorder;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.enums.*;
import com.hisun.kugga.duke.pay.api.order.dto.*;
import com.hisun.kugga.duke.pay.api.wallet.WalletApi;
import com.hisun.kugga.duke.pay.api.wallet.dto.*;
import com.hisun.kugga.duke.pay.bo.BillBO;
import com.hisun.kugga.duke.pay.bo.SplitAccountBO;
import com.hisun.kugga.duke.pay.dal.dataobject.payorder.PayOrderDO;
import com.hisun.kugga.duke.pay.dal.dataobject.payorderrefund.PayOrderRefundDO;
import com.hisun.kugga.duke.pay.dal.dataobject.payordersub.PayOrderSubDO;
import com.hisun.kugga.duke.pay.dal.mysql.payorder.PayOrderMapper;
import com.hisun.kugga.duke.pay.dal.mysql.payorderrefund.PayOrderRefundMapper;
import com.hisun.kugga.duke.pay.dal.mysql.payordersub.PayOrderSubMapper;
import com.hisun.kugga.duke.pay.service.useraccount.UserAccountService;
import com.hisun.kugga.duke.pay.service.userbill.BillService;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.util.amount.AmountUtil;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hisun.kugga.duke.common.CommonConstants.SNOWFLAKE;
import static com.hisun.kugga.duke.pay.mq.SplitAccountApplyConsumer.SPLIT_ACCOUNT_TOPIC;


/**
 * 订单 Service 实现类
 *
 * @author zhou_xiong
 */
@Slf4j
@Service
@Validated
public class PayOrderServiceImpl implements PayOrderService {
    @Resource
    private PayOrderMapper payOrderMapper;
    @Resource
    private PayOrderSubMapper payOrderSubMapper;
    @Resource
    private UserAccountService userAccountService;
    @Resource
    private BillService billService;
    @Resource
    private WalletApi walletApi;
    @Resource
    private PayOrderRefundMapper payOrderRefundMapper;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderCreateRspDTO createOrder(OrderCreateReqDTO orderCreateReqDTO) {
        // 查询钱包账户
        String accountId = userAccountService.findWalletAccount(orderCreateReqDTO.getAccountType(), orderCreateReqDTO.getPayerId());
        // 判断用户钱包账户余额是否足够
        userAccountService.balanceEnough(orderCreateReqDTO.getAmount(), accountId);
        // 生成内部订单号
        String appOrderNo = SNOWFLAKE.nextIdStr();
        // 钱包预支付
        OrderCreateRspDTO orderCreateRspDTO = prepay(orderCreateReqDTO, accountId, appOrderNo);
        // 保存内部订单
        savePayOrder(orderCreateReqDTO, accountId, appOrderNo, orderCreateRspDTO);
        return orderCreateRspDTO;
    }

    private void payByBalance(PayOrderDO payOrderDO) {
        // 钱包余额支付
        PayByBalanceReqBody payByBalanceReqBody = new PayByBalanceReqBody()
                .setAppOrderNo(payOrderDO.getAppOrderNo())
                .setOrderNo(payOrderDO.getWalletOrderNo());
        walletApi.payByBalance(payByBalanceReqBody);
    }

    private void savePayOrder(OrderCreateReqDTO orderCreateReqDTO, String accountId, String appOrderNo, OrderCreateRspDTO orderCreateRspDTO) {
        PayOrderDO payOrderDO = new PayOrderDO()
                .setAppOrderNo(appOrderNo)
                .setWalletOrderNo(orderCreateRspDTO.getWalletOrderNo())
                .setOrderType(orderCreateReqDTO.getOrderType())
                .setPayerId(orderCreateReqDTO.getPayerId())
                .setAccountType(orderCreateReqDTO.getAccountType())
                .setAccountId(accountId)
                .setPayChannel(PayChannel.BALANCE)
                .setPayAmount(orderCreateReqDTO.getAmount())
                // 预支付
                .setStatus(PayOrderStatus.PREPAY)
                .setRemark(orderCreateReqDTO.getOrderType().getDesc());
        payOrderMapper.insert(payOrderDO);
    }

    private OrderCreateRspDTO prepay(OrderCreateReqDTO orderCreateReqDTO, String accountId, String appOrderNo) {
        PrepayReqBody prepayReqBody = new PrepayReqBody()
                .setAccount(accountId)
                .setAmount(AmountUtil.yuanToFen(orderCreateReqDTO.getAmount()))
                .setToAccount(orderCreateReqDTO.getOrderType().getDesc())
                .setAppOrderNo(appOrderNo);
        PrepayRspBody prepayRspBody = walletApi.prepay(prepayReqBody);
        return new OrderCreateRspDTO()
                .setAppOrderNo(appOrderNo)
                .setWalletOrderNo(prepayRspBody.getOrderNo())
                .setFee(AmountUtil.fenToYuan(prepayRspBody.getFee()));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void pay(PayReqDTO payReqDTO) {
        // 查询交易订单
        PayOrderDO payOrderDO = payOrderMapper.selectOne(new LambdaQueryWrapper<PayOrderDO>()
                .eq(PayOrderDO::getAppOrderNo, payReqDTO.getAppOrderNo()));
        if (ObjectUtil.isNull(payOrderDO) || !PayOrderStatus.PREPAY.equals(payOrderDO.getStatus())) {
            return;
        }
        // todo 后续根据支付方式调用不同支付，目前只支持余额支付
        payByBalance(payOrderDO);
        // 修改订单状态为paySuccess
        payOrderMapper.update(null, new LambdaUpdateWrapper<PayOrderDO>()
                .set(PayOrderDO::getStatus, PayOrderStatus.PAY_SUCCESS)
                .eq(PayOrderDO::getAppOrderNo, payOrderDO.getAppOrderNo()));
        // 余额支付是同步的，直接生成账单
        BillBO billBO = new BillBO()
                .setAccountType(payOrderDO.getAccountType())
                .setWalletOrderNo(payOrderDO.getWalletOrderNo())
                .setObjectId(payOrderDO.getPayerId())
                .setAmount(payOrderDO.getPayAmount().negate())
                .setRemark(payOrderDO.getOrderType().getDesc());
        billService.saveBillByAccountType(billBO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void splitAccount(SplitAccountReqDTO splitAccountReqDTO) {
        // 查询交易订单
        PayOrderDO payOrderDO = payOrderMapper.selectOne(new LambdaQueryWrapper<PayOrderDO>()
                .eq(PayOrderDO::getAppOrderNo, splitAccountReqDTO.getAppOrderNo()));
        if (ObjectUtil.isNull(payOrderDO) || !PayOrderStatus.PAY_SUCCESS.equals(payOrderDO.getStatus())) {
            return;
        }
        // 过滤掉分账金额为0的
        List<ReceiverInfo> receiverInfoList = splitAccountReqDTO.getReceiverList().stream().filter(receiverInfo ->
                ObjectUtil.isNotNull(receiverInfo.getAmount()) && receiverInfo.getAmount().compareTo(BigDecimal.ZERO) > 0
        ).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(receiverInfoList)) {
            return;
        }
        // 分账金额
        BigDecimal splitAmount = receiverInfoList.stream().map(ReceiverInfo::getAmount)
                .filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 分账金额不能大于订单可分账金额
        BigDecimal canSplitAmount = AmountUtil.sub(payOrderDO.getPayAmount(), payOrderDO.getSplitAmount());
        if (splitAmount.compareTo(canSplitAmount) > 0) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.ILLEGAL_SPLIT_AMOUNT);
        }
        // 保存分账订单
        OrderType orderType = Optional.ofNullable(splitAccountReqDTO.getOrderType()).orElse(payOrderDO.getOrderType());
        List<Long> orderSubIds = receiverInfoList.stream().map(receiverInfo -> {
            PayOrderSubDO payOrderUpdateDO = new PayOrderSubDO()
                    .setAppOrderNo(payOrderDO.getAppOrderNo())
                    .setOrderType(orderType)
                    .setReceiverId(receiverInfo.getReceiverId())
                    .setAccountType(receiverInfo.getAccountType())
                    .setAccountId(userAccountService.findWalletAccount(receiverInfo.getAccountType(), receiverInfo.getReceiverId()))
                    .setAmount(receiverInfo.getAmount())
                    .setStatus(PayOrderSubStatus.PRE_SPLIT)
                    .setRemark(orderType.getDesc());
            payOrderSubMapper.insert(payOrderUpdateDO);
            return payOrderUpdateDO.getId();
        }).collect(Collectors.toList());
        // 异步调用钱包分账
        List<Receiver> receiverList = receiverInfoList.stream().map(receiverInfo -> {
            Receiver receiver = new Receiver();
            receiver.setProfitSharingAmount(AmountUtil.yuanToFen(receiverInfo.getAmount()));
            receiver.setProfitSharingReceiver(userAccountService.findWalletAccount(receiverInfo.getAccountType(), receiverInfo.getReceiverId()));
            return receiver;
        }).collect(Collectors.toList());
        SplitAccountBO splitAccountBO = new SplitAccountBO()
                .setWalletOrderNo(payOrderDO.getWalletOrderNo())
                .setReceiverList(receiverList)
                .setOrderSubIds(orderSubIds);
        rocketMQTemplate.syncSend(SPLIT_ACCOUNT_TOPIC, splitAccountBO);
    }

    @Override
    public void refund(RefundReqDTO refundReqDTO) {
        // 查询交易订单
        PayOrderDO payOrderDO = payOrderMapper.selectOne(new LambdaQueryWrapper<PayOrderDO>()
                .eq(PayOrderDO::getAppOrderNo, refundReqDTO.getAppOrderNo()));
        if (ObjectUtil.isNull(payOrderDO) || !PayOrderStatus.PAY_SUCCESS.equals(payOrderDO.getStatus())) {
            return;
        }
        // 退款金额不能大于可退款金额
        BigDecimal canRefundAmount = AmountUtil.sub(payOrderDO.getPayAmount(), payOrderDO.getRefundAmount());
        if (refundReqDTO.getRefundAmount().compareTo(canRefundAmount) > 0) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.ILLEGAL_REFUND_AMOUNT);
        }
        // 请求钱包退款
        DrawbackApplyReqBody drawbackApplyReqBody = new DrawbackApplyReqBody()
                .setDrawbackAmount(AmountUtil.yuanToFen(refundReqDTO.getRefundAmount()))
                .setAccount(payOrderDO.getAccountId())
                .setCallbackUrl("https://www.baidu.com")
                .setOrderType("pay")
                .setOriginalOrderNo(payOrderDO.getWalletOrderNo());
        DrawbackApplyRspBody drawbackApplyRspBody = walletApi.drawbackApply(drawbackApplyReqBody);
        // 保存退款记录
        PayOrderRefundDO payOrderRefundDO = new PayOrderRefundDO()
                .setAppOrderNo(payOrderDO.getAppOrderNo())
                .setRefundNo(drawbackApplyRspBody.getOrderNo())
                .setAmount(refundReqDTO.getRefundAmount())
                .setRemark(OrderType.REFUND.getDesc() + payOrderDO.getRemark())
                .setStatus(PayOrderRefundStatus.PRE_REFUND);
        payOrderRefundMapper.insert(payOrderRefundDO);
    }

    @Override
    public void cancel(String appOrderNo) {
        // 通过钱包订单号查询订单
        PayOrderDO payOrderDO = payOrderMapper.selectOne(new LambdaQueryWrapper<PayOrderDO>()
                .eq(PayOrderDO::getAppOrderNo, appOrderNo)
                .eq(PayOrderDO::getStatus, PayOrderStatus.PREPAY)
                .eq(PayOrderDO::getPayerId, SecurityFrameworkUtils.getLoginUserId())
                .eq(PayOrderDO::getAccountType, AccountType.USER));
        if (ObjectUtil.isNull(payOrderDO)) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.ORDER_NOT_EXISTS);
        }
        // 修改订单状态为closed
        PayOrderDO payOrderUpdateDO = new PayOrderDO()
                .setId(payOrderDO.getId())
                .setStatus(PayOrderStatus.CLOSED);
        payOrderMapper.updateById(payOrderUpdateDO);
        // 关闭消费订单
        walletApi.payCancel(new PaymentCancelReqBody().setOrderNo(payOrderDO.getWalletOrderNo()));
    }

}
