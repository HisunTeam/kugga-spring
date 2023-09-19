package com.hisun.kugga.duke.league.dal.mysql;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.league.dal.dataobject.TaskCreateLeagueDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * <p>
 * 创建公会订单表 Mapper 接口
 * </p>
 *
 * @author zuocheng
 * @since 2022-07-26 19:25:54
 */
@Mapper
public interface TaskCreateLeagueMapper extends BaseMapperX<TaskCreateLeagueDO> {
    /**
     * 根据内部订单号查询订单信息
     *
     * @param orderNo
     * @return
     */
    default TaskCreateLeagueDO selectByOrder(String orderNo) {
        return selectOne(new LambdaQueryWrapperX<TaskCreateLeagueDO>()
                .eq(TaskCreateLeagueDO::getAppOrderNo, orderNo));
    }

    /**
     * 根据订单号修改订单支付成功
     *
     * @param orderNo
     * @return
     */
    default int updateByOrder(String orderNo) {
        return update(null, new LambdaUpdateWrapper<TaskCreateLeagueDO>()
                .set(TaskCreateLeagueDO::getPayStatus, 1)
                .set(TaskCreateLeagueDO::getBusinessStatus, 1)
                .set(TaskCreateLeagueDO::getUpdateTime, LocalDateTime.now())
                .eq(TaskCreateLeagueDO::getAppOrderNo, orderNo)
                .eq(TaskCreateLeagueDO::getPayStatus, 0)
                .eq(TaskCreateLeagueDO::getBusinessStatus, 0)
        );
    }

    /**
     * 更新订单到分账中
     *
     * @param orderNo
     * @return
     */
    default int updateLedgerIn(Long userId, String orderNo) {
        return update(null, new LambdaUpdateWrapper<TaskCreateLeagueDO>()
                .set(TaskCreateLeagueDO::getLedgerUserId, userId)
                .set(TaskCreateLeagueDO::getAmountStatus, 1)
                .set(TaskCreateLeagueDO::getUpdateTime, LocalDateTime.now())
                .set(TaskCreateLeagueDO::getLedgerTime, LocalDateTime.now())
                .eq(TaskCreateLeagueDO::getAppOrderNo, orderNo)
                .eq(TaskCreateLeagueDO::getAmountStatus, 0)
        );
    }

    /**
     * 订单更新成分账完成
     *
     * @param orderNo
     * @return
     */
    default int updateLedger(String orderNo) {
        return update(null, new LambdaUpdateWrapper<TaskCreateLeagueDO>()
                .set(TaskCreateLeagueDO::getAmountStatus, 2)
                .set(TaskCreateLeagueDO::getUpdateTime, LocalDateTime.now())
                .set(TaskCreateLeagueDO::getLedgerTime, LocalDateTime.now())
                .eq(TaskCreateLeagueDO::getAppOrderNo, orderNo)
                .eq(TaskCreateLeagueDO::getAmountStatus, 1)
        );
    }
}
