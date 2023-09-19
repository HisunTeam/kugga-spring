package com.hisun.kugga.duke.league.dal.mysql;

import com.hisun.kugga.duke.league.dal.dataobject.TaskChatDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-09-16 15:04
 */
@Mapper
public interface TaskChatMapper extends BaseMapperX<TaskChatDO> {

    default TaskChatDO selectByNoticeId(Long id) {
        return selectOne(TaskChatDO::getNoticeId, id);
    }
}

