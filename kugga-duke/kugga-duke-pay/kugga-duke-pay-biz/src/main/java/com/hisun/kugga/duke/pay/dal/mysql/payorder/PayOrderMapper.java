package com.hisun.kugga.duke.pay.dal.mysql.payorder;

import com.hisun.kugga.duke.pay.dal.dataobject.payorder.PayOrderDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 订单 Mapper
 *
 * @author zhou_xiong
 */
@Mapper
public interface PayOrderMapper extends BaseMapperX<PayOrderDO> {
    int fastFail(@Param("orderNo") String orderNo, @Param("groupNo") String groupNo, @Param("status") String status);

    BigDecimal getRefundAmount(@Param("orderNo") String orderNo,
                               @Param("groupNo") String groupNo,
                               @Param("byOrderNo") Boolean byOrderNo);
}
