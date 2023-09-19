package com.hisun.kugga.duke.batch.dal.mysql.payorder;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.batch.dal.dataobject.payorder.PayOrderDO;
import com.hisun.kugga.duke.enums.PayOrderStatus;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.cursor.Cursor;

import java.math.BigDecimal;

/**
 * 订单 Mapper
 *
 * @author zhou_xiong
 */
@Mapper
public interface PayOrderMapper extends BaseMapperX<PayOrderDO> {

    void updateSplitAmount(@Param("payOrderDO") PayOrderDO payOrderUpdate);

    default void updateStatus(Long id, PayOrderStatus payOrderStatus) {
        update(null, new LambdaUpdateWrapper<PayOrderDO>().set(PayOrderDO::getStatus, payOrderStatus)
                .eq(PayOrderDO::getId, id)
                .eq(PayOrderDO::getStatus, PayOrderStatus.PAY_SUCCESS));
    }

    void updateStatusRefundSuccess(@Param("id") Long id, @Param("amount") BigDecimal amount);

    Cursor<PayOrderDO> selectPrepayOrders();
}
