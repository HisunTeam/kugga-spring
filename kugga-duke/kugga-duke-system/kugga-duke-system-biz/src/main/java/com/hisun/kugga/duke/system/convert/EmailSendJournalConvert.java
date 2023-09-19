package com.hisun.kugga.duke.system.convert;

import com.hisun.kugga.duke.system.controller.app.email.vo.EmailSendJournalCreateReqVO;
import com.hisun.kugga.duke.system.controller.app.email.vo.EmailSendJournalRespVO;
import com.hisun.kugga.duke.system.controller.app.email.vo.EmailSendJournalUpdateReqVO;
import com.hisun.kugga.duke.system.dal.dataobject.EmailSendJournalDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 邮件发送流水 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface EmailSendJournalConvert {

    EmailSendJournalConvert INSTANCE = Mappers.getMapper(EmailSendJournalConvert.class);

    EmailSendJournalDO convert(EmailSendJournalCreateReqVO bean);

    EmailSendJournalDO convert(EmailSendJournalUpdateReqVO bean);

    EmailSendJournalRespVO convert(EmailSendJournalDO bean);

    List<EmailSendJournalRespVO> convertList(List<EmailSendJournalDO> list);
}
