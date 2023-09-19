package com.hisun.kugga.duke.pay.dal.mysql.chargeorder;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.pay.dal.dataobject.chargeorder.ChargeOrderDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import org.apache.ibatis.annotations.Mapper;

import static com.hisun.kugga.duke.common.CommonConstants.WalletStatus.PREPAY;
import static com.hisun.kugga.duke.common.CommonConstants.WalletStatus.PROCESSING;

/**
 * 充值订单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ChargeOrderMapper extends BaseMapperX<ChargeOrderDO> {

    default boolean existUnfinished() {
        Long count = selectCount(new LambdaQueryWrapper<ChargeOrderDO>()
                .eq(ChargeOrderDO::getUserId, SecurityFrameworkUtils.getLoginUserId())
                .in(ChargeOrderDO::getStatus, PREPAY, PROCESSING));
        return count > 0;
    }
}
