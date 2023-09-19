package com.hisun.kugga.duke.league.dal.mysql;

import com.hisun.kugga.duke.enums.TaskStatusEnum;
import com.hisun.kugga.duke.enums.TaskTypeEnum;
import com.hisun.kugga.duke.league.dal.dataobject.TaskDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 公会规则 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TaskMapper extends BaseMapperX<TaskDO> {
    /**
     * 查询已过期邀请认证过期的数据（查询两周内的数据,避免查询压力过大）
     *
     * @return
     */
    default List<TaskDO> queryAuthExpire() {
        return selectList(new LambdaQueryWrapperX<TaskDO>()
                .between(TaskDO::getCreateTime, LocalDateTime.now().withNano(0).minusWeeks(2), LocalDateTime.now().withNano(0))
                .eq(TaskDO::getType, TaskTypeEnum.TASK_TYPE_2.getValue())
                .eq(TaskDO::getStatus, TaskStatusEnum.TASK_STATUS_1.getValue())
                .le(TaskDO::getExpiresTime, LocalDateTime.now().withNano(0))
                .orderByAsc(TaskDO::getId).last("limit 1000")
        );
    }

    /**
     * 查询已过期邀请认证过期的数据（查询两周内的数据,避免查询压力过大）
     *
     * @return
     */
    default List<TaskDO> queryChatExpire() {
        return selectList(new LambdaQueryWrapperX<TaskDO>()
                .between(TaskDO::getCreateTime, LocalDateTime.now().withNano(0).minusWeeks(2), LocalDateTime.now().withNano(0))
                .eq(TaskDO::getType, TaskTypeEnum.TASK_TYPE_3.getValue())
                .eq(TaskDO::getStatus, TaskStatusEnum.TASK_STATUS_1.getValue())
                .le(TaskDO::getExpiresTime, LocalDateTime.now().withNano(0))
                .orderByAsc(TaskDO::getId).last("limit 300")
        );
    }
}
