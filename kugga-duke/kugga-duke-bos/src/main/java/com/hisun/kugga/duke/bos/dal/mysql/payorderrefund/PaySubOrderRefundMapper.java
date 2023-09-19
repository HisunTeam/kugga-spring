package com.hisun.kugga.duke.bos.dal.mysql.payorderrefund;

import com.hisun.kugga.duke.bos.dal.dataobject.payorderrefund.PayOrderRefundDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 支付订单退款 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PaySubOrderRefundMapper extends BaseMapperX<PayOrderRefundDO> {

    /**
     * 查询退款子订单
     *
     * @return
     */
    default List<PayOrderRefundDO> querySubRefundOrder(String appOrderNo) {
        return selectList(new LambdaQueryWrapperX<PayOrderRefundDO>()
                .eq(PayOrderRefundDO::getAppOrderNo, appOrderNo)
        );
    }

}
