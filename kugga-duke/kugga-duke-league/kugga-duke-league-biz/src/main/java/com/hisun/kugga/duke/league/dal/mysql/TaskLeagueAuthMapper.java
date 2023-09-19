package com.hisun.kugga.duke.league.dal.mysql;

import com.hisun.kugga.duke.league.dal.dataobject.TaskLeagueAuthDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-09-08 15:14
 */
@Mapper
public interface TaskLeagueAuthMapper extends BaseMapperX<TaskLeagueAuthDO> {

    default TaskLeagueAuthDO selectByNoticeId(Long leagueNoticeId) {
        return selectOne(TaskLeagueAuthDO::getNoticeId, leagueNoticeId);
    }
}
