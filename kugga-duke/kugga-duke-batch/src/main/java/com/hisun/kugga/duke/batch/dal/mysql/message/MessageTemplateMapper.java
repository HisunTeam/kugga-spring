package com.hisun.kugga.duke.batch.dal.mysql.message;

import com.hisun.kugga.duke.batch.dal.dataobject.message.MessageTemplateDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 消息模板参数 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MessageTemplateMapper extends BaseMapperX<MessageTemplateDO> {
    /**
     * 根据语言查询模板列表
     *
     * @param language
     * @return
     */
    default List<MessageTemplateDO> selectListByLanguage(String language) {
        return selectList(new LambdaQueryWrapperX<MessageTemplateDO>().eq(MessageTemplateDO::getLanguage, language));
    }
}
