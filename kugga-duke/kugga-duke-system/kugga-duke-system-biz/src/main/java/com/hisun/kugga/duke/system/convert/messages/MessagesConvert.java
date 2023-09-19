package com.hisun.kugga.duke.system.convert.messages;

import com.hisun.kugga.duke.system.controller.app.message.vo.MessagesCreateReqVO;
import com.hisun.kugga.duke.system.controller.app.message.vo.MessagesUpdateReqVO;
import com.hisun.kugga.duke.system.dal.dataobject.MessagesDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 消息 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface MessagesConvert {

    MessagesConvert INSTANCE = Mappers.getMapper(MessagesConvert.class);

    MessagesDO convert(MessagesCreateReqVO bean);

    MessagesDO convert(MessagesUpdateReqVO bean);

//    MessagesRespVO convert(MessagesDO bean);

//    List<MessagesRespVO> convertList(List<MessagesDO> list);

//    PageResult<MessagesRespVO> convertPage(PageResult<MessagesDO> page);


}
