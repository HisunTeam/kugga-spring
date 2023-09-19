package com.hisun.kugga.duke.system.service.messages.client;

import com.hisun.kugga.duke.dto.SendMessageReqDTO;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/7/30 12:01
 */
public interface MessageClient {


    /**
     * 发送消息
     *
     * @param messageReqDTO
     */
    void sendMessage(SendMessageReqDTO messageReqDTO);

}
