package com.hisun.kugga.duke.batch.dal.mysql.league;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.batch.dal.dataobject.league.TaskDO;
import com.hisun.kugga.duke.enums.TaskStatusEnum;
import com.hisun.kugga.duke.enums.TaskTypeEnum;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Guild Rules Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TaskMapper extends BaseMapperX<TaskDO> {
    /**
     * Query data for expired invitation authentication tasks (within two weeks to avoid excessive query load)
     *
     * @return
     */
    default List<TaskDO> queryAuthExpire() {
        return selectList(new LambdaQueryWrapperX<TaskDO>()
                .between(TaskDO::getCreateTime, LocalDateTime.now().withNano(0).minusWeeks(2), LocalDateTime.now().withNano(0))
                .eq(TaskDO::getType, TaskTypeEnum.TASK_TYPE_2)
                .eq(TaskDO::getStatus, TaskStatusEnum.TASK_STATUS_1)
                .le(TaskDO::getExpiresTime, LocalDateTime.now().withNano(0))
                .orderByAsc(TaskDO::getId).last("limit 1000")
        );
    }

    /**
     * Query data for expired chat tasks (within two weeks to avoid excessive query load)
     *
     * @return
     */
    default List<TaskDO> queryChatExpire() {
        return selectList(new LambdaQueryWrapperX<TaskDO>()
                .between(TaskDO::getCreateTime, LocalDateTime.now().withNano(0).minusWeeks(2), LocalDateTime.now().withNano(0))
                .eq(TaskDO::getType, TaskTypeEnum.TASK_TYPE_3)
                .eq(TaskDO::getStatus, TaskStatusEnum.TASK_STATUS_1)
                .le(TaskDO::getExpiresTime, LocalDateTime.now().withNano(0))
                .orderByAsc(TaskDO::getId).last("limit 1000")
        );
    }

    /**
     * Query data for expired report tasks (within two weeks to avoid excessive query load)
     *
     * @return
     */
    default List<TaskDO> queryReportExpire() {
        return selectList(new LambdaQueryWrapperX<TaskDO>()
                .between(TaskDO::getCreateTime, LocalDateTime.now().withNano(0).minusWeeks(2), LocalDateTime.now().withNano(0))
                .eq(TaskDO::getType, TaskTypeEnum.TASK_TYPE_1)
                .eq(TaskDO::getStatus, TaskStatusEnum.TASK_STATUS_1)
                .le(TaskDO::getExpiresTime, LocalDateTime.now().withNano(0))
                .orderByAsc(TaskDO::getId).last("limit 300")
        );
    }

    /**
     * Update authentication tasks to refund status
     *
     * @param taskId
     * @return
     */
    default int updateAuthExpire(Long taskId) {
        return update(null, new LambdaUpdateWrapper<TaskDO>()
                .set(TaskDO::getStatus, TaskStatusEnum.TASK_STATUS_5)
                .set(TaskDO::getUpdateTime, LocalDateTime.now())
                .eq(TaskDO::getId, taskId)
                .eq(TaskDO::getStatus, TaskStatusEnum.TASK_STATUS_1)
                .eq(TaskDO::getType, TaskTypeEnum.TASK_TYPE_2)
        );
    }

    /**
     * Update chat tasks to refund status
     *
     * @param taskId
     * @return
     */
    default int updateChatExpire(Long taskId) {
        return update(null, new LambdaUpdateWrapper<TaskDO>()
                .set(TaskDO::getStatus, TaskStatusEnum.TASK_STATUS_5)
                .set(TaskDO::getUpdateTime, LocalDateTime.now())
                .eq(TaskDO::getId, taskId)
                .eq(TaskDO::getStatus, TaskStatusEnum.TASK_STATUS_1)
                .eq(TaskDO::getType, TaskTypeEnum.TASK_TYPE_3)
        );
    }

    /**
     * Update report tasks to refund status
     *
     * @param taskId
     * @return
     */
    default int updateReportExpire(Long taskId) {
        return update(null, new LambdaUpdateWrapper<TaskDO>()
                .set(TaskDO::getStatus, TaskStatusEnum.TASK_STATUS_5)
                .set(TaskDO::getUpdateTime, LocalDateTime.now())
                .eq(TaskDO::getId, taskId)
                .eq(TaskDO::getStatus, TaskStatusEnum.TASK_STATUS_1)
                .eq(TaskDO::getType, TaskTypeEnum.TASK_TYPE_1)
        );
    }

    /**
     * Query list of league authentication refunds
     *
     * @return
     */
    default List<TaskDO> queryLeagueAuthRefundList() {
        return selectList(
                new LambdaQueryWrapperX<TaskDO>()
                        .between(TaskDO::getCreateTime, LocalDateTime.now().withNano(0).minusWeeks(2), LocalDateTime.now().withNano(0))
                        .eq(TaskDO::getType, TaskTypeEnum.TASK_TYPE_2)
                        .eq(TaskDO::getStatus, TaskStatusEnum.TASK_STATUS_4)
                        .orderByAsc(TaskDO::getId).last("limit 1000")
        );
    }
}
