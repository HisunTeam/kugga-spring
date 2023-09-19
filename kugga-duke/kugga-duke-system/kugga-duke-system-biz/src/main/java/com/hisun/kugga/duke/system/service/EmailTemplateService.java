package com.hisun.kugga.duke.system.service;

import com.hisun.kugga.duke.enums.email.EmailScene;
import com.hisun.kugga.duke.system.dal.dataobject.EmailTemplateDO;

/**
 * 邮件模板参数 Service 接口
 *
 * @author zhou_xiong
 */
public interface EmailTemplateService {

    /**
     * 通过邮件场景和国际化标识查询一条记录
     *
     * @param emailScene
     * @param locale
     * @return
     */
    EmailTemplateDO selectOneByEmailSceneAndLocale(EmailScene emailScene, String locale);
}
