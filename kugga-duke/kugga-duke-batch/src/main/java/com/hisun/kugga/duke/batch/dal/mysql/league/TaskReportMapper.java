package com.hisun.kugga.duke.batch.dal.mysql.league;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.batch.dal.dataobject.league.TaskReportDO;
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
public interface TaskReportMapper extends BaseMapperX<TaskReportDO> {

    /**
     * 根据taskId查询,推荐报告列表
     *
     * @param taskId
     * @return
     */
    default List<TaskReportDO> selectByTaskId(Long taskId) {
        return selectList(new LambdaQueryWrapperX<TaskReportDO>()
                .eq(TaskReportDO::getTaskId, taskId)
                .eq(TaskReportDO::getPayType, TaskPayTypeEnum.PAY)
                .eq(TaskReportDO::getStatus, PayStatusEnum.PAY)
        );
    }

    /**
     * 根据taskId更新成退款
     *
     * @param taskId
     * @return
     */
    default int updateRefundByTaskId(Long taskId) {
        return update(null, new LambdaUpdateWrapper<TaskReportDO>()
                .set(TaskReportDO::getStatus, PayStatusEnum.REFUND)
                .set(TaskReportDO::getUpdateTime, LocalDateTime.now())
                .eq(TaskReportDO::getTaskId, taskId)
                .eq(TaskReportDO::getPayType, TaskPayTypeEnum.PAY)
        );
    }

    /**
     * 根据noticeId查询,推荐报告列表
     *
     * @param noticeId
     * @return
     */
    default TaskReportDO selectByNoticeId(Long noticeId) {
        return selectOne(new LambdaQueryWrapperX<TaskReportDO>()
                .eq(TaskReportDO::getNoticeId, noticeId)
                .eq(TaskReportDO::getPayType, TaskPayTypeEnum.PAY)
                .eq(TaskReportDO::getStatus, PayStatusEnum.PAY)
        );
    }

    /**
     * 根据noticeId更新成退款
     *
     * @param noticeId
     * @return
     */
    default int updateRefundByNoticeId(Long noticeId) {
        return update(null, new LambdaUpdateWrapper<TaskReportDO>()
                .set(TaskReportDO::getStatus, PayStatusEnum.REFUND)
                .set(TaskReportDO::getUpdateTime, LocalDateTime.now())
                .eq(TaskReportDO::getNoticeId, noticeId)
                .eq(TaskReportDO::getPayType, TaskPayTypeEnum.PAY)
        );
    }
}
