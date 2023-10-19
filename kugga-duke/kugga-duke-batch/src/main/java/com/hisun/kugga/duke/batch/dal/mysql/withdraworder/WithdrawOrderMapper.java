package com.hisun.kugga.duke.batch.dal.mysql.withdraworder;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.batch.channel.wallet.dto.WithdrawDetailRspBody;
import com.hisun.kugga.duke.batch.dal.dataobject.withdraworder.WithdrawOrderDO;
import com.hisun.kugga.framework.common.util.amount.AmountUtil;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cursor.Cursor;

/**
 * Withdrawal Order Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface WithdrawOrderMapper extends BaseMapperX<WithdrawOrderDO> {
    /**
     * All withdrawal orders in draft status
     *
     * @return
     */
    Cursor<WithdrawOrderDO> selectDraftOrders();

    default void updateStatus(String walletOrderNo, WithdrawDetailRspBody withdrawDetailRspBody) {
        update(null, new LambdaUpdateWrapper<WithdrawOrderDO>()
                .set(WithdrawOrderDO::getStatus, withdrawDetailRspBody.getStatus())
                .set(WithdrawOrderDO::getReceivedTime, withdrawDetailRspBody.getReceivedTime())
                .set(WithdrawOrderDO::getActualAmount, AmountUtil.fenToYuan(withdrawDetailRspBody.getActualAmount()))
                .eq(WithdrawOrderDO::getWalletOrderNo, walletOrderNo)
                .eq(WithdrawOrderDO::getDeleted, false));
    }
}
