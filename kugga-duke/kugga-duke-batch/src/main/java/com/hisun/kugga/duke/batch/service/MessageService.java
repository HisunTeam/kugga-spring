package com.hisun.kugga.duke.batch.service;

import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.enums.message.MessageTemplateEnum;

/**
 * Message Notification Service
 */
public interface MessageService {
    /**
     * Get template content based on template enumeration (with caching)
     *
     * @param templateEnum
     * @return
     */
    String getContent(MessageTemplateEnum templateEnum);

    /**
     * Common batch message sending interface
     * @param sendMessageReqDTO
     */
    void sendMessage(SendMessageReqDTO sendMessageReqDTO);
}
