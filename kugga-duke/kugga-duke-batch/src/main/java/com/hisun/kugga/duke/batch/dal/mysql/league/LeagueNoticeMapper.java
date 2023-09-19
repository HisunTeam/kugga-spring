package com.hisun.kugga.duke.batch.dal.mysql.league;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.batch.dal.dataobject.league.LeagueNoticeDO;
import com.hisun.kugga.duke.enums.LeagueNoticeStatusEnum;
import com.hisun.kugga.duke.enums.LeagueNoticeTypeEnum;
import com.hisun.kugga.duke.enums.TaskTypeEnum;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 16:41
 */
@Mapper
public interface LeagueNoticeMapper extends BaseMapperX<LeagueNoticeDO> {
    /**
     * 根据taskId修改通知状态(更新成失效)
     *
     * @param taskId
     * @return
     */
    default int updateInvalidByTaskId(Long taskId) {
        return update(null, new LambdaUpdateWrapper<LeagueNoticeDO>()
                .set(LeagueNoticeDO::getStatus, LeagueNoticeStatusEnum.NOTICE_STATUS_4)
                .set(LeagueNoticeDO::getUpdateTime, LocalDateTime.now())
                .eq(LeagueNoticeDO::getTaskId, taskId));
    }

    /**
     * 查询过期数据
     *
     * @return
     */
    default List<LeagueNoticeDO> queryExpire(List<LeagueNoticeStatusEnum> status) {
        return selectList(new LambdaQueryWrapperX<LeagueNoticeDO>()
                .between(LeagueNoticeDO::getCreateTime, LocalDateTime.now().withNano(0).minusWeeks(2), LocalDateTime.now().withNano(0))
                .eq(LeagueNoticeDO::getTaskType, TaskTypeEnum.TASK_TYPE_1)
                .eq(LeagueNoticeDO::getType, LeagueNoticeTypeEnum.NOTICE_TYPE_1)
                .le(LeagueNoticeDO::getExpiresTime, LocalDateTime.now().withNano(0))
                .in(LeagueNoticeDO::getStatus, status)
                .orderByAsc(LeagueNoticeDO::getId).last("limit 1000")
        );
    }

    /**
     * 根据ID修改通知为失效
     *
     * @param notice
     * @return
     */
    default int updateInvalidById(LeagueNoticeDO notice) {
        return updateById(notice);
    }
}
