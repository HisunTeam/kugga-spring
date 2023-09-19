package com.hisun.kugga.duke.chat.convert;

import com.hisun.kugga.duke.chat.controller.app.vo.roommember.RoomMemberCreateReqVO;
import com.hisun.kugga.duke.chat.controller.app.vo.roommember.RoomMemberRespVO;
import com.hisun.kugga.duke.chat.controller.app.vo.roommember.RoomMemberUpdateReqVO;
import com.hisun.kugga.duke.chat.dal.dataobject.RoomMemberDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 聊天室成员 Convert
 *
 * @author toi
 */
@Mapper
public interface RoomMemberConvert {

    RoomMemberConvert INSTANCE = Mappers.getMapper(RoomMemberConvert.class);

    RoomMemberDO convert(RoomMemberCreateReqVO bean);

    RoomMemberDO convert(RoomMemberUpdateReqVO bean);

    RoomMemberRespVO convert(RoomMemberDO bean);

    List<RoomMemberRespVO> convertList(List<RoomMemberDO> list);

    PageResult<RoomMemberRespVO> convertPage(PageResult<RoomMemberDO> page);

}
