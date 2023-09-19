package com.hisun.kugga.module.pay.dal.mysql.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.module.pay.dal.dataobject.order.PayOrderExtensionDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayOrderExtensionMapper extends BaseMapperX<PayOrderExtensionDO> {

    default PayOrderExtensionDO selectByNo(String no) {
        return selectOne(PayOrderExtensionDO::getNo, no);
    }

    default int updateByIdAndStatus(Long id, Integer status, PayOrderExtensionDO update) {
        return update(update, new LambdaQueryWrapper<PayOrderExtensionDO>()
                .eq(PayOrderExtensionDO::getId, id).eq(PayOrderExtensionDO::getStatus, status));
    }

}
