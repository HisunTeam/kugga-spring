package com.hisun.kugga.duke.batch.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.hisun.kugga.duke.batch.dal.dataobject.message.MessageTemplateDO;
import com.hisun.kugga.duke.batch.dal.mysql.message.MessageTemplateMapper;
import com.hisun.kugga.duke.batch.service.MessageService;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.enums.message.MessageTemplateEnum;
import com.hisun.kugga.duke.innercall.InnerCallHelper;
import com.hisun.kugga.duke.vo.RedPacketDetailRspVO;
import com.hisun.kugga.framework.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.ILLEGAL_PARAM;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * Message Service Implementation
 *
 * Author: Zuo Cheng
 */
@Slf4j
@Service
@Validated
public class MessageServiceImpl implements MessageService {
    private static final String MESSAGE_TEMPLATE = "message:template";

    @Value("${duke.league.backed.sendMessageClient:}")
    private String sendMessageUrl;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private MessageTemplateMapper messageTemplateMapper;
    @Resource
    private InnerCallHelper innerCallHelper;

    /**
     * Deprecated - Not recommended for use. Batch messaging calls sendMessage.
     *
     * @param templateEnum
     * @return
     */
    @Deprecated
    @Override
    public String getContent(MessageTemplateEnum templateEnum) {
        // Read the template from the cache. If not found, cache the template again.
        String templateStr = redisTemplate.opsForValue().get(MESSAGE_TEMPLATE);
        if (ObjectUtil.isEmpty(templateStr)) {
            initMessageTemplate();
            templateStr = redisTemplate.opsForValue().get(MESSAGE_TEMPLATE);
        }

        Map<String, String> templateMap = JSONUtil.toBean(templateStr, Map.class);
        return templateMap.get(templateEnum.name());
    }

    @Override
    public void sendMessage(SendMessageReqDTO sendMessageReqDTO) {
        Optional.ofNullable(sendMessageReqDTO.getBusinessId()).orElseThrow(() -> new ServiceException(ILLEGAL_PARAM));

        String uuid = innerCallHelper.genCert(sendMessageReqDTO.getBusinessId());
        sendMessageReqDTO.setUuid(uuid);

        try {
            innerCallHelper.post(sendMessageUrl, sendMessageReqDTO, SendMessageReqDTO.class);
        } catch (Exception exception) {
            log.error("Batch - Internal message notification exception: ", exception);
        }
    }

    /**
     * Cache message templates.
     */
    private void initMessageTemplate() {
        List<MessageTemplateDO> messageTemplateDos = messageTemplateMapper.selectListByLanguage("en-US");

        HashMap<String, String> map = new HashMap<>(messageTemplateDos.size());
        if (ObjectUtil.isNotEmpty(messageTemplateDos)) {
            for (MessageTemplateDO templateDo : messageTemplateDos) {
                // RECOMMENDATION_INVITE -> Invitation from Zhang San to write a recommendation report
                // map.put(templateDo.getMessageScene() + "_" + templateDo.getMessageType(), templateDo.getTemplate());

                // Use a unique key
                map.put(templateDo.getMessageKey(), templateDo.getTemplate());
            }
        }
        redisTemplate.opsForValue().set(MESSAGE_TEMPLATE, JSONUtil.toJsonPrettyStr(map), 24, TimeUnit.HOURS);
    }
}
