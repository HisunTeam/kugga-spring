package com.hisun.kugga.duke.pay.dal.mysql.payordersub;

import com.hisun.kugga.duke.pay.dal.dataobject.payordersub.PayOrderSubDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

/**
 * 支付订单子集 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PayOrderSubMapper extends BaseMapperX<PayOrderSubDO> {
    /**
     * 查询订单分账成功的金额
     *
     * @param appOrderNo
     * @return
     */
    BigDecimal sumSplitSuccess(String appOrderNo);
}
