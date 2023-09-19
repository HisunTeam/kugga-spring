package com.hisun.kugga.duke.chat.convert;

import com.hisun.kugga.duke.chat.controller.app.vo.room.RoomCreateReqVO;
import com.hisun.kugga.duke.chat.controller.app.vo.room.RoomRespVO;
import com.hisun.kugga.duke.chat.controller.app.vo.room.RoomUpdateReqVO;
import com.hisun.kugga.duke.chat.dal.dataobject.RoomDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 聊天室 Convert
 *
 * @author toi
 */
@Mapper
public interface RoomConvert {

    RoomConvert INSTANCE = Mappers.getMapper(RoomConvert.class);

    RoomDO convert(RoomCreateReqVO bean);

    RoomDO convert(RoomUpdateReqVO bean);

    RoomRespVO convert(RoomDO bean);

    List<RoomRespVO> convertList(List<RoomDO> list);

    PageResult<RoomRespVO> convertPage(PageResult<RoomDO> page);

}
