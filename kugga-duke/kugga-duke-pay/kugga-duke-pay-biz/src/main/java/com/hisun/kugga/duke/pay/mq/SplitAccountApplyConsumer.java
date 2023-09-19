package com.hisun.kugga.duke.pay.mq;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.enums.PayOrderStatus;
import com.hisun.kugga.duke.pay.api.wallet.WalletApi;
import com.hisun.kugga.duke.pay.api.wallet.dto.AllocationApplyReqBody;
import com.hisun.kugga.duke.pay.api.wallet.dto.AllocationApplyRspBody;
import com.hisun.kugga.duke.pay.bo.SplitAccountBO;
import com.hisun.kugga.duke.pay.dal.dataobject.payorder.PayOrderDO;
import com.hisun.kugga.duke.pay.dal.dataobject.payordersub.PayOrderSubDO;
import com.hisun.kugga.duke.pay.dal.mysql.payorder.PayOrderMapper;
import com.hisun.kugga.duke.pay.dal.mysql.payordersub.PayOrderSubMapper;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.hisun.kugga.duke.pay.mq.SplitAccountApplyConsumer.SPLIT_ACCOUNT_GROUP;
import static com.hisun.kugga.duke.pay.mq.SplitAccountApplyConsumer.SPLIT_ACCOUNT_TOPIC;

/**
 * 分账消费
 */
@Component
@RocketMQMessageListener(topic = SPLIT_ACCOUNT_TOPIC, consumerGroup = SPLIT_ACCOUNT_GROUP)
public class SplitAccountApplyConsumer implements RocketMQListener<SplitAccountBO> {
    public static final String SPLIT_ACCOUNT_TOPIC = "KUGGA_SPLIT_ACCOUNT_APPLY_TOPIC";
    public static final String SPLIT_ACCOUNT_GROUP = "KUGGA_SPLIT_ACCOUNT_APPLY_GROUP";

    @Resource
    private WalletApi walletApi;
    @Resource
    private PayOrderMapper payOrderMapper;
    @Resource
    private PayOrderSubMapper payOrderSubMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void onMessage(SplitAccountBO splitAccountBO) {
        // 避免重复消费
        PayOrderDO payOrderDO = payOrderMapper.selectOne(new LambdaQueryWrapper<PayOrderDO>()
                .eq(PayOrderDO::getWalletOrderNo, splitAccountBO.getWalletOrderNo()));
        if (ObjectUtil.isNull(payOrderDO) || PayOrderStatus.SPLIT_SUCCESS.equals(payOrderDO.getStatus())
                || PayOrderStatus.PART_REFUND.equals(payOrderDO.getStatus())) {
            return;
        }
        // 调用钱包请求分账
        AllocationApplyReqBody allocationApplyReqBody = new AllocationApplyReqBody();
        allocationApplyReqBody.setOrderNo(splitAccountBO.getWalletOrderNo());
        // 回调接口暂时没有写
        allocationApplyReqBody.setCallbackUrl("https://www.baidu.com");
        allocationApplyReqBody.setReceiverList(splitAccountBO.getReceiverList());
        AllocationApplyRspBody allocationApplyRspBody = walletApi.allocationApply(allocationApplyReqBody);
        // 保存分账订单的钱包分账记录号
        splitAccountBO.getOrderSubIds().stream().map(id ->
                new PayOrderSubDO().setId(id)
                        .setSplitNo(allocationApplyRspBody.getSharingNo())
        ).forEach(payOrderSubMapper::updateById);
    }
}

