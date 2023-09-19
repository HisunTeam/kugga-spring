package com.hisun.kugga.duke.system.service.messages;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.enums.message.MessageSceneEnum;
import com.hisun.kugga.duke.enums.message.MessageTypeEnum;
import com.hisun.kugga.duke.system.dal.dataobject.MessageTemplateDO;
import com.hisun.kugga.duke.system.dal.mysql.MessageTemplateMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/7/29 10:29
 */
@Service
public class MessageTemplateServiceImpl implements MessageTemplateService {
    @Resource
    private MessageTemplateMapper templateMapper;

    @Override
    public MessageTemplateDO selectOneBySceneAndTypeAndLocale(MessageSceneEnum messageScene, MessageTypeEnum messageType, String language) {
//        MessageTemplateDO templateDO = templateMapper.selectOne(new LambdaQueryWrapper<MessageTemplateDO>()
//                .eq(MessageTemplateDO::getMessageScene, messageScene)
//                .eq(MessageTemplateDO::getMessageType, messageType)
//                .eq(MessageTemplateDO::getLanguage, language));
//        return templateDO;

        LambdaQueryWrapper<MessageTemplateDO> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(messageScene)) {
            wrapper.eq(MessageTemplateDO::getMessageScene, messageScene);
        }
        if (ObjectUtil.isNotNull(messageType)) {
            wrapper.eq(MessageTemplateDO::getMessageType, messageType);
        }
        if (ObjectUtil.isNotNull(language)) {
            wrapper.eq(MessageTemplateDO::getLanguage, language);
        }
        return templateMapper.selectOne(wrapper);
    }

    @Override
    public List<MessageTemplateDO> selectListByLanguage(String language) {
        LambdaQueryWrapper<MessageTemplateDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MessageTemplateDO::getLanguage, language);
        return templateMapper.selectList(wrapper);
    }


}
