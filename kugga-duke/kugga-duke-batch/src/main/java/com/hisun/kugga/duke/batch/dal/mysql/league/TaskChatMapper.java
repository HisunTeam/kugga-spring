package com.hisun.kugga.duke.batch.dal.mysql.league;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.batch.dal.dataobject.league.TaskChatDO;
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
 * @Date: 2022-09-16 15:04
 */
@Mapper
public interface TaskChatMapper extends BaseMapperX<TaskChatDO> {
    /**
     * Query authentication list by taskId
     *
     * @param taskId
     * @return
     */
    default List<TaskChatDO> selectByTaskId(Long taskId) {
        return selectList(new LambdaQueryWrapperX<TaskChatDO>()
                .eq(TaskChatDO::getTaskId, taskId)
                .eq(TaskChatDO::getPayType, TaskPayTypeEnum.PAY)
                .eq(TaskChatDO::getStatus, PayStatusEnum.PAY)
        );
    }

    /**
     * Update to refund by taskId
     *
     * @param taskId
     * @return
     */
    default int updateRefundByTaskId(Long taskId) {
        return update(null, new LambdaUpdateWrapper<TaskChatDO>()
                .set(TaskChatDO::getStatus, PayStatusEnum.REFUND)
                .set(TaskChatDO::getUpdateTime, LocalDateTime.now())
                .eq(TaskChatDO::getTaskId, taskId)
                .eq(TaskChatDO::getPayType, TaskPayTypeEnum.PAY)
        );
    }
}
