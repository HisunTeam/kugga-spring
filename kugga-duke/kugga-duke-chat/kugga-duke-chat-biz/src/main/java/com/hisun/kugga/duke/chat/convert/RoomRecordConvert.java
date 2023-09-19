package com.hisun.kugga.duke.chat.convert;

import com.hisun.kugga.duke.chat.controller.app.vo.roomrecord.RoomRecordCreateReqVO;
import com.hisun.kugga.duke.chat.controller.app.vo.roomrecord.RoomRecordRespVO;
import com.hisun.kugga.duke.chat.controller.app.vo.roomrecord.RoomRecordUpdateReqVO;
import com.hisun.kugga.duke.chat.dal.dataobject.RoomRecordDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 聊天记录 Convert
 *
 * @author toi
 */
@Mapper
public interface RoomRecordConvert {

    RoomRecordConvert INSTANCE = Mappers.getMapper(RoomRecordConvert.class);

    RoomRecordDO convert(RoomRecordCreateReqVO bean);

    RoomRecordDO convert(RoomRecordUpdateReqVO bean);

    RoomRecordRespVO convert(RoomRecordDO bean);

    List<RoomRecordRespVO> convertList(List<RoomRecordDO> list);

    PageResult<RoomRecordRespVO> convertPage(PageResult<RoomRecordDO> page);

}
