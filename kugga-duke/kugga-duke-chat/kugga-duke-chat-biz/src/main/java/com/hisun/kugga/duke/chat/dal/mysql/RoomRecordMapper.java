package com.hisun.kugga.duke.chat.dal.mysql;

import com.hisun.kugga.duke.chat.dal.dataobject.RoomRecordDO;
import com.hisun.kugga.framework.common.pojo.PageParam;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 聊天记录 Mapper
 *
 * @author toi
 */
@Mapper
public interface RoomRecordMapper extends BaseMapperX<RoomRecordDO> {

    default int insertOne(RoomRecordDO entry) {
        return insert(entry);
    }

    default PageResult<RoomRecordDO> selectPage(Long roomId,
                                                Long recordId,
                                                PageParam reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RoomRecordDO>()
                .eqIfPresent(RoomRecordDO::getRoomId, roomId)
                .leIfPresent(RoomRecordDO::getId, recordId)
                .orderByDesc(RoomRecordDO::getId));
    }

}
