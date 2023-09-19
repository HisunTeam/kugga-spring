package com.hisun.kugga.duke.system.dal.mysql;

import com.hisun.kugga.duke.system.dal.dataobject.MessageTemplateDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 消息模板参数 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MessageTemplateMapper extends BaseMapperX<MessageTemplateDO> {

}
