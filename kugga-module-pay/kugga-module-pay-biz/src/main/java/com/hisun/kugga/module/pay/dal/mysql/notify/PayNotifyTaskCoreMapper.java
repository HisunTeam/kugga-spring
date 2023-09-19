package com.hisun.kugga.module.pay.dal.mysql.notify;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.module.pay.dal.dataobject.notify.PayNotifyTaskDO;
import com.hisun.kugga.module.pay.enums.notify.PayNotifyStatusEnum;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface PayNotifyTaskCoreMapper extends BaseMapperX<PayNotifyTaskDO> {

    /**
     * 获得需要通知的 PayNotifyTaskDO 记录。需要满足如下条件：
     * <p>
     * 1. status 非成功
     * 2. nextNotifyTime 小于当前时间
     *
     * @return PayTransactionNotifyTaskDO 数组
     */
    default List<PayNotifyTaskDO> selectListByNotify() {
        return selectList(new QueryWrapper<PayNotifyTaskDO>()
                .in("status", PayNotifyStatusEnum.WAITING.getStatus(), PayNotifyStatusEnum.REQUEST_SUCCESS.getStatus(),
                        PayNotifyStatusEnum.REQUEST_FAILURE.getStatus())
                .le("next_notify_time", new Date()));
    }

}
