package com.hisun.kugga.duke.batch.job.allocation;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.batch.channel.wallet.WalletClient;
import com.hisun.kugga.duke.batch.channel.wallet.dto.AllocationResultReqBody;
import com.hisun.kugga.duke.batch.channel.wallet.dto.AllocationResultRspBody;
import com.hisun.kugga.duke.batch.dal.dataobject.leaguebill.LeagueBillDO;
import com.hisun.kugga.duke.batch.dal.dataobject.payorder.PayOrderDO;
import com.hisun.kugga.duke.batch.dal.dataobject.payordersub.PayOrderSubDO;
import com.hisun.kugga.duke.batch.dal.dataobject.userbill.UserBillDO;
import com.hisun.kugga.duke.batch.dal.mysql.leaguebill.LeagueBillMapper;
import com.hisun.kugga.duke.batch.dal.mysql.payorder.PayOrderMapper;
import com.hisun.kugga.duke.batch.dal.mysql.payordersub.PayOrderSubMapper;
import com.hisun.kugga.duke.batch.dal.mysql.userbill.UserBillMapper;
import com.hisun.kugga.duke.common.CommonConstants;
import com.hisun.kugga.duke.enums.AccountType;
import com.hisun.kugga.duke.enums.PayOrderStatus;
import com.hisun.kugga.duke.enums.PayOrderSubStatus;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.quartz.core.handler.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hisun.kugga.duke.common.CommonConstants.SNOWFLAKE;
import static com.hisun.kugga.duke.common.CommonConstants.WalletStatus.FAILED;
import static com.hisun.kugga.duke.common.CommonConstants.WalletStatus.SUCCESS;

/**
 * 分账结果定时轮询
 *
 * @author: zhou_xiong
 */
@Slf4j
@Component
public class AllocationResultJob implements JobHandler {
    @Resource
    private WalletClient walletClient;
    @Resource
    private PayOrderMapper payOrderMapper;
    @Resource
    private PayOrderSubMapper payOrderSubMapper;
    @Resource
    private UserBillMapper userBillMapper;
    @Resource
    private LeagueBillMapper leagueBillMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String execute(String param) throws Exception {
        // 查询preSplit状态的订单子集
        List<PayOrderSubDO> payOrderSubDOList = payOrderSubMapper.selectPreSplitOrderSub();
        if (CollUtil.isEmpty(payOrderSubDOList)) {
            return GlobalErrorCodeConstants.SUCCESS.getMsg();
        }
        Map<String, List<PayOrderSubDO>> groupMap = payOrderSubDOList.stream()
                .collect(Collectors.groupingBy(PayOrderSubDO::getSplitNo));
        groupMap.forEach((k, v) -> {
            // 查询分账结果
            AllocationResultRspBody allocationResultRspBody = walletClient.allocationResult(new AllocationResultReqBody().setSharingNo(k));
            List<Long> idList = v.stream().map(PayOrderSubDO::getId).collect(Collectors.toList());
            // 查询订单信息
            PayOrderDO payOrderDO = payOrderMapper.selectOne(new LambdaQueryWrapper<PayOrderDO>()
                    .eq(PayOrderDO::getAppOrderNo, v.get(0).getAppOrderNo()));
            BigDecimal splitAmount = v.stream().map(PayOrderSubDO::getAmount)
                    .filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (SUCCESS.equals(allocationResultRspBody.getProfitSharingStatus())) {
                // 修改订单子集分账成功
                payOrderSubMapper.updateStatus(idList, PayOrderSubStatus.SPLIT_SUCCESS);
                v.forEach(payOrderSubDO -> {
                    // 分账成功后，生成用户/公会账单   是否要生成平台账单
                    if (AccountType.USER.equals(payOrderSubDO.getAccountType())) {
                        // 生成用户账单
                        UserBillDO userBillDO = new UserBillDO()
                                .setBillNo(SNOWFLAKE.nextIdStr())
                                .setWalletOrderNo(payOrderSubDO.getWalletOrderNo())
                                .setUserId(payOrderSubDO.getReceiverId())
                                .setAmount(payOrderSubDO.getAmount())
                                .setStatus(CommonConstants.BillStatus.SUCCESS)
                                .setRemark(payOrderSubDO.getRemark());
                        userBillMapper.insert(userBillDO);
                    } else {
                        // 生成公会账单
                        LeagueBillDO leagueBillDO = new LeagueBillDO()
                                .setBillNo(SNOWFLAKE.nextIdStr())
                                .setWalletOrderNo(payOrderSubDO.getWalletOrderNo())
                                .setLeagueId(payOrderSubDO.getReceiverId())
                                .setAmount(payOrderSubDO.getAmount())
                                .setStatus(CommonConstants.BillStatus.SUCCESS)
                                .setRemark(payOrderSubDO.getRemark());
                        leagueBillMapper.insert(leagueBillDO);
                    }
                });
                // 修改订单已分账金额，如果分账完毕，修改订单状态为splitSuccess
                PayOrderDO payOrderUpdate = new PayOrderDO().setId(payOrderDO.getId())
                        .setSplitAmount(splitAmount);
                payOrderMapper.updateSplitAmount(payOrderUpdate);
                if (payOrderDO.getPayAmount().compareTo(payOrderUpdate.getSplitAmount()) == 0) {
                    payOrderMapper.updateStatus(payOrderDO.getId(), PayOrderStatus.SPLIT_SUCCESS);
                }
            } else if (FAILED.equals(allocationResultRspBody.getProfitSharingStatus())) {
                // 修改订单子集分账失败
                payOrderSubMapper.updateStatus(idList, PayOrderSubStatus.SPLIT_FAILED);
                // todo 如何确定订单是分账失败状态
            }
        });
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }
}
