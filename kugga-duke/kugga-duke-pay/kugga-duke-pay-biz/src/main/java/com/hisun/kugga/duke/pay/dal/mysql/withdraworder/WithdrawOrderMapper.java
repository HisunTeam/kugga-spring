package com.hisun.kugga.duke.pay.dal.mysql.withdraworder;

import com.hisun.kugga.duke.pay.dal.dataobject.withdraworder.WithdrawOrderDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 提现订单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface WithdrawOrderMapper extends BaseMapperX<WithdrawOrderDO> {

}
