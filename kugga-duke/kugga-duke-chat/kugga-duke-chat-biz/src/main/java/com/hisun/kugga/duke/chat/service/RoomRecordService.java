package com.hisun.kugga.duke.chat.service;

import com.hisun.kugga.duke.chat.controller.app.vo.roomrecord.RoomRecordPageReqVO;
import com.hisun.kugga.duke.chat.controller.app.vo.roomrecord.RoomRecordReadReqVO;
import com.hisun.kugga.duke.chat.dal.dataobject.RoomRecordDO;
import com.hisun.kugga.framework.common.pojo.PageResult;

/**
 * 聊天记录 Service 接口
 *
 * @author toi
 */
public interface RoomRecordService {
    /**
     * 获得聊天记录分页
     */
    PageResult<RoomRecordDO> getRoomRecordPage(RoomRecordPageReqVO pageReqVO);

    void read(RoomRecordReadReqVO reqVO);
}
