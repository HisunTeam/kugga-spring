package com.hisun.kugga.duke.system.dal.mysql;


import com.hisun.kugga.duke.system.dal.dataobject.EmailTemplateDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件模板参数 Mapper
 *
 * @author zhou_xiong
 */
@Mapper
public interface EmailTemplateMapper extends BaseMapperX<EmailTemplateDO> {

}
