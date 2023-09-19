package com.hisun.kugga.duke.system.service.messages;

import com.hisun.kugga.duke.enums.message.MessageSceneEnum;
import com.hisun.kugga.duke.enums.message.MessageTypeEnum;
import com.hisun.kugga.duke.system.dal.dataobject.MessageTemplateDO;

import java.util.List;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/7/29 10:29
 */
public interface MessageTemplateService {

    /**
     * @param messageSceneEnum
     * @param messageTypeEnum
     * @param locale
     * @return
     */
    MessageTemplateDO selectOneBySceneAndTypeAndLocale(MessageSceneEnum messageSceneEnum,
                                                       MessageTypeEnum messageTypeEnum,
                                                       String locale);


    /**
     * 通过语言查询所有
     *
     * @param language
     * @return
     */
    List<MessageTemplateDO> selectListByLanguage(String language);
}
