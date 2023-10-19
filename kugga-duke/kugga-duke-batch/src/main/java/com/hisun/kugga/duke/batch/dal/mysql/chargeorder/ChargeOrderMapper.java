package com.hisun.kugga.duke.batch.dal.mysql.chargeorder;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.batch.dal.dataobject.chargeorder.ChargeOrderDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cursor.Cursor;

/**
 * Recharge Order Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ChargeOrderMapper extends BaseMapperX<ChargeOrderDO> {

    default void updateStatus(String walletOrderNo, String status, String receivedTime) {
        update(null, new LambdaUpdateWrapper<ChargeOrderDO>()
                .set(ChargeOrderDO::getStatus, status)
                .set(ChargeOrderDO::getReceivedTime, receivedTime)
                .eq(ChargeOrderDO::getWalletOrderNo, walletOrderNo)
                .eq(ChargeOrderDO::getDeleted, false));
    }

    Cursor<ChargeOrderDO> selectInitChargeOrders();
}
