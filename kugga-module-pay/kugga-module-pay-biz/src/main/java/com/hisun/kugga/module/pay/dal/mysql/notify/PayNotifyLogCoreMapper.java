package com.hisun.kugga.module.pay.dal.mysql.notify;

import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.module.pay.dal.dataobject.notify.PayNotifyLogDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayNotifyLogCoreMapper extends BaseMapperX<PayNotifyLogDO> {
}
