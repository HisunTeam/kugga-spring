package com.hisun.kugga.duke.batch.service;


import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.enums.message.MessageTemplateEnum;

/**
 * 消息通知服务
 */
public interface MessageService {
    /**
     * 根据模板枚举获取模板内容(带缓存的)
     *
     * @param templateEnum
     * @return
     */
    String getContent(MessageTemplateEnum templateEnum);

    /**
     * batch公共发消息接口
     * @param sendMessageReqDTO
     */
    void sendMessage(SendMessageReqDTO sendMessageReqDTO);
}
