package com.hisun.kugga.duke.batch.dal.mysql.league;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.batch.dal.dataobject.league.TaskLeagueAuthDO;
import com.hisun.kugga.duke.enums.PayStatusEnum;
import com.hisun.kugga.duke.enums.TaskPayTypeEnum;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-09-08 15:14
 */
@Mapper
public interface TaskLeagueAuthMapper extends BaseMapperX<TaskLeagueAuthDO> {
    /**
     * 根据taskId查询,认证列表
     *
     * @param taskId
     * @return
     */
    default List<TaskLeagueAuthDO> selectByTaskId(Long taskId) {
        return selectList(new LambdaQueryWrapperX<TaskLeagueAuthDO>()
                .eq(TaskLeagueAuthDO::getTaskId, taskId)
                .eq(TaskLeagueAuthDO::getPayType, TaskPayTypeEnum.PAY)
                .eq(TaskLeagueAuthDO::getStatus, PayStatusEnum.PAY)
        );
    }

    /**
     * 根据taskId更新成退款
     *
     * @param taskId
     * @return
     */
    default int updateRefundByTaskId(Long taskId) {
        return update(null, new LambdaUpdateWrapper<TaskLeagueAuthDO>()
                .set(TaskLeagueAuthDO::getStatus, PayStatusEnum.REFUND)
                .set(TaskLeagueAuthDO::getUpdateTime, LocalDateTime.now())
                .eq(TaskLeagueAuthDO::getTaskId, taskId)
                .eq(TaskLeagueAuthDO::getPayType, TaskPayTypeEnum.PAY)
        );
    }
}
