package com.hisun.kugga.duke.chat.convert;

import com.hisun.kugga.duke.chat.controller.app.vo.session.SessionCreateReqVO;
import com.hisun.kugga.duke.chat.controller.app.vo.session.SessionRespVO;
import com.hisun.kugga.duke.chat.controller.app.vo.session.SessionUpdateReqVO;
import com.hisun.kugga.duke.chat.dal.dataobject.SessionDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 聊天会话列 Convert
 *
 * @author toi
 */
@Mapper
public interface SessionConvert {

    SessionConvert INSTANCE = Mappers.getMapper(SessionConvert.class);

    SessionDO convert(SessionCreateReqVO bean);

    SessionDO convert(SessionUpdateReqVO bean);

    SessionRespVO convert(SessionDO bean);

    List<SessionRespVO> convertList(List<SessionDO> list);

    PageResult<SessionRespVO> convertPage(PageResult<SessionDO> page);

}
