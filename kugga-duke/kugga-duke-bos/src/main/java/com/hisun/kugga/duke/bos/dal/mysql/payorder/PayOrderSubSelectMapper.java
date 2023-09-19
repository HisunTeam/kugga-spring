package com.hisun.kugga.duke.bos.dal.mysql.payorder;

import com.hisun.kugga.duke.bos.dal.dataobject.payorder.PayOrderSubDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 支付订单子集 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PayOrderSubSelectMapper extends BaseMapperX<PayOrderSubDO> {

    /**
     * 查询子订单
     *
     * @return
     */
    default List<PayOrderSubDO> querySubOrder(String appOrderNo) {
        return selectList(new LambdaQueryWrapperX<PayOrderSubDO>()
                .eq(PayOrderSubDO::getAppOrderNo, appOrderNo)
        );
    }

}
