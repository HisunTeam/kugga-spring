package com.hisun.kugga.duke.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.enums.email.EmailScene;
import com.hisun.kugga.duke.system.dal.dataobject.EmailTemplateDO;
import com.hisun.kugga.duke.system.dal.mysql.EmailTemplateMapper;
import com.hisun.kugga.duke.system.service.EmailTemplateService;
import com.hisun.kugga.framework.common.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.EMAIL_TEMPLATE_NOT_FOUND;


/**
 * 邮件模板参数 Service 实现类
 *
 * @author zhou_xiong
 */
@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

    @Resource
    private EmailTemplateMapper templateMapper;


    @Override
    public EmailTemplateDO selectOneByEmailSceneAndLocale(EmailScene emailScene, String locale) {
        EmailTemplateDO emailTemplateDO = templateMapper.selectOne(new LambdaQueryWrapper<EmailTemplateDO>()
                .eq(EmailTemplateDO::getEmailScene, emailScene.name())
                .eq(EmailTemplateDO::getLocale, locale));
        if (ObjectUtil.isNull(emailTemplateDO)) {
            throw new ServiceException(EMAIL_TEMPLATE_NOT_FOUND);
        }
        return emailTemplateDO;
    }
}
