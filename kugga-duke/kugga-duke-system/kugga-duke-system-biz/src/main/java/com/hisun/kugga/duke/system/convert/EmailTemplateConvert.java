package com.hisun.kugga.duke.system.convert;

import com.hisun.kugga.duke.system.controller.app.email.vo.AppEmailTemplateCreateReqVO;
import com.hisun.kugga.duke.system.controller.app.email.vo.AppEmailTemplateRespVO;
import com.hisun.kugga.duke.system.controller.app.email.vo.AppEmailTemplateUpdateReqVO;
import com.hisun.kugga.duke.system.dal.dataobject.EmailTemplateDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 邮件模板参数 Convert
 *
 * @author zhou_xiong
 */
@Mapper
public interface EmailTemplateConvert {

    EmailTemplateConvert INSTANCE = Mappers.getMapper(EmailTemplateConvert.class);

    EmailTemplateDO convert(AppEmailTemplateCreateReqVO bean);

    EmailTemplateDO convert(AppEmailTemplateUpdateReqVO bean);

    AppEmailTemplateRespVO convert(EmailTemplateDO bean);

    List<AppEmailTemplateRespVO> convertList(List<EmailTemplateDO> list);
}
