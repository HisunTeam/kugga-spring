package com.hisun.kugga.duke.batch.dal.mysql.payorderrefund;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.batch.dal.dataobject.payorderrefund.PayOrderRefundDO;
import com.hisun.kugga.duke.enums.PayOrderRefundStatus;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cursor.Cursor;

/**
 * 支付订单退款 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PayOrderRefundMapper extends BaseMapperX<PayOrderRefundDO> {
    /**
     * 查询待退款的记录
     *
     * @return
     */
    Cursor<PayOrderRefundDO> selectPreRefund();

    default void updateStatus(Long id, PayOrderRefundStatus status, String time) {
        update(null, new LambdaUpdateWrapper<PayOrderRefundDO>()
                .set(PayOrderRefundDO::getStatus, status)
                .set(PayOrderRefundDO::getSuccessTime, time)
                .eq(PayOrderRefundDO::getStatus, PayOrderRefundStatus.PRE_REFUND));
    }
}
