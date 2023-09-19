package com.hisun.kugga.duke.system.dal.mysql;

import com.hisun.kugga.duke.system.dal.dataobject.EmailSendJournalDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件发送流水 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface EmailSendJournalMapper extends BaseMapperX<EmailSendJournalDO> {

}
