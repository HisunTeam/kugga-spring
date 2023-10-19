package com.hisun.kugga.duke.batch.dal.mysql.payordersub;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.batch.dal.dataobject.payordersub.PayOrderSubDO;
import com.hisun.kugga.duke.enums.PayOrderSubStatus;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Subset of Payment Orders Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PayOrderSubMapper extends BaseMapperX<PayOrderSubDO> {

    default void updateStatus(List<Long> idList, PayOrderSubStatus payOrderSubStatus) {
        update(null, new LambdaUpdateWrapper<PayOrderSubDO>()
                .set(PayOrderSubDO::getStatus, payOrderSubStatus)
                .in(PayOrderSubDO::getId, idList));
    }

    List<PayOrderSubDO> selectPreSplitOrderSub();
}
