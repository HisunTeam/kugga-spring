package com.hisun.kugga.duke.pay.service.chargeorder;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.common.CommonConstants;
import com.hisun.kugga.duke.enums.ChargeOrderStatus;
import com.hisun.kugga.duke.enums.OrderType;
import com.hisun.kugga.duke.pay.api.wallet.WalletApi;
import com.hisun.kugga.duke.pay.api.wallet.dto.*;
import com.hisun.kugga.duke.pay.controller.app.charge.vo.ChargeDetailRspVO;
import com.hisun.kugga.duke.pay.controller.app.charge.vo.PreChargeReqVO;
import com.hisun.kugga.duke.pay.controller.app.charge.vo.PreChargeRspVO;
import com.hisun.kugga.duke.pay.dal.dataobject.chargeorder.ChargeOrderDO;
import com.hisun.kugga.duke.pay.dal.dataobject.userbill.UserBillDO;
import com.hisun.kugga.duke.pay.dal.mysql.chargeorder.ChargeOrderMapper;
import com.hisun.kugga.duke.pay.service.userbill.UserBillService;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.util.amount.AmountUtil;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static com.hisun.kugga.duke.common.CommonConstants.SNOWFLAKE;

/**
 * @author: zhou_xiong
 */
@Slf4j
@Service
public class ChargeServiceImpl implements ChargeService {
    @Value("${kugga.pay.charge-success-url}")
    private String chargeSuccessUrl;
    @Resource
    private WalletApi walletApi;
    @Resource
    private ChargeOrderMapper chargeOrderMapper;
    @Resource
    private UserBillService userBillService;

    @Override
    public PreChargeRspVO preCharge(PreChargeReqVO preChargeReqVO) {
        String appOrderNo = SNOWFLAKE.nextIdStr();
        String accountId = SecurityFrameworkUtils.getLoginUser().getAccountId();
        // 调用钱包预充值接口
        PreChargeReqBody reqBody = new PreChargeReqBody()
                .setAccount(accountId)
                .setAmount(AmountUtil.yuanToFen(preChargeReqVO.getAmount()))
                .setAppOrderNo(appOrderNo)
                .setReturnUrl(chargeSuccessUrl)
                .setCancelUrl(chargeSuccessUrl);
        PreChargeRspBody preChargeRspBody = walletApi.preCharge(reqBody);
        BigDecimal fee = AmountUtil.fenToYuan(preChargeRspBody.getFee());
        // 保存充值订单
        ChargeOrderDO chargeOrderDO = ChargeOrderDO.builder()
                .appOrderNo(appOrderNo)
                .walletOrderNo(preChargeRspBody.getOrderNo())
                .amount(preChargeReqVO.getAmount())
                .fee(fee)
                .accountId(accountId)
                .userId(SecurityFrameworkUtils.getLoginUser().getId())
                .status(ChargeOrderStatus.PREPAY).build();
        chargeOrderMapper.insert(chargeOrderDO);
        return new PreChargeRspVO()
                .setPaypalPrepayUrl(preChargeRspBody.getPaypalPrepayUrl())
                .setOrderNo(preChargeRspBody.getOrderNo())
                .setFee(fee);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ChargeDetailRspVO getChargeDetail(String walletOrderNo) {
        // 通过钱包订单号查询订单
        ChargeOrderDO chargeOrderDO = chargeOrderMapper.selectOne(new LambdaQueryWrapper<ChargeOrderDO>()
                .eq(ChargeOrderDO::getWalletOrderNo, walletOrderNo)
                .eq(ChargeOrderDO::getUserId, SecurityFrameworkUtils.getLoginUserId()));
        if (ObjectUtil.isNull(chargeOrderDO)) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.ORDER_NOT_EXISTS);
        }
        if (finalStatusJudge(chargeOrderDO.getStatus().getKey())) {
            // 订单终态，直接返回
            return new ChargeDetailRspVO()
                    .setOrderNo(chargeOrderDO.getWalletOrderNo())
                    .setStatus(chargeOrderDO.getStatus().getKey());
        } else {
            // 查询钱包充值订单状态
            ChargeDetailReqBody chargeDetailReqBody = new ChargeDetailReqBody().setOrderNo(walletOrderNo);
            ChargeDetailRspBody chargeDetailRspBody = walletApi.chargeDetail(chargeDetailReqBody);
            if (finalStatusJudge(chargeDetailRspBody.getStatus())) {
                // 修改订单状态
                chargeOrderMapper.update(null, new LambdaUpdateWrapper<ChargeOrderDO>()
                        .set(ChargeOrderDO::getStatus, chargeDetailRspBody.getStatus())
                        .set(ChargeOrderDO::getReceivedTime, chargeDetailRspBody.getReceivedTime())
                        .eq(ChargeOrderDO::getWalletOrderNo, walletOrderNo));
                if (StrUtil.equals(ChargeOrderStatus.CHARGE_SUCCESS.getKey(), chargeDetailRspBody.getStatus())) {
                    // 生成账单
                    UserBillDO userBillDO = new UserBillDO()
                            .setBillNo(SNOWFLAKE.nextIdStr())
                            .setWalletOrderNo(chargeOrderDO.getWalletOrderNo())
                            .setUserId(chargeOrderDO.getUserId())
                            .setAmount(AmountUtil.fenToYuan(chargeDetailRspBody.getActualAmount()))
                            .setFee(AmountUtil.fenToYuan(chargeDetailRspBody.getFee()).negate())
                            .setStatus(CommonConstants.BillStatus.SUCCESS)
                            .setRemark(OrderType.CHARGE.getDesc());
                    userBillService.insertIfNotExist(userBillDO);
                }
            }
            return new ChargeDetailRspVO()
                    .setOrderNo(chargeOrderDO.getWalletOrderNo())
                    .setStatus(chargeDetailRspBody.getStatus());
        }
    }

    @Override
    public void cancel(String walletOrderNo) {
        // 通过钱包订单号查询订单
        ChargeOrderDO chargeOrderDO = chargeOrderMapper.selectOne(new LambdaQueryWrapper<ChargeOrderDO>()
                .eq(ChargeOrderDO::getWalletOrderNo, walletOrderNo)
                .eq(ChargeOrderDO::getStatus, ChargeOrderStatus.PREPAY)
                .eq(ChargeOrderDO::getUserId, SecurityFrameworkUtils.getLoginUserId()));
        if (ObjectUtil.isNull(chargeOrderDO)) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.ORDER_NOT_EXISTS);
        }
        // 修改订单状态为closed
        ChargeOrderDO chargeOrderUpdateDO = new ChargeOrderDO()
                .setId(chargeOrderDO.getId())
                .setStatus(ChargeOrderStatus.CLOSED);
        chargeOrderMapper.updateById(chargeOrderUpdateDO);
        // 关闭钱包订单
        walletApi.chargeCancel(new ChargeCancelReqBody().setOrderNo(walletOrderNo));
    }

    private boolean finalStatusJudge(String status) {
        return StrUtil.equalsAny(status, ChargeOrderStatus.CHARGE_SUCCESS.getKey(),
                ChargeOrderStatus.CHARGE_FAILED.getKey(), ChargeOrderStatus.CLOSED.getKey());
    }
}
