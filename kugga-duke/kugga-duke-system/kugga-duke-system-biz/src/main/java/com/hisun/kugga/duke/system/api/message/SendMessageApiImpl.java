package com.hisun.kugga.duke.system.api.message;

import com.hisun.kugga.duke.system.api.message.dto.MessagesUpdateReqDTO;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.system.service.messages.MessagesService;
import com.hisun.kugga.duke.system.service.messages.client.MessageClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @Description:
 * @author： Lin
 * @Date 2022/7/29 15:13
 */
@Service
public class SendMessageApiImpl implements SendMessageApi {

    @Resource
    private MessageClient messageClient;

    @Resource
    private MessagesService messagesService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void sendMessage(SendMessageReqDTO messageReqDTO) {
        messageClient.sendMessage(messageReqDTO);
    }

    @Override
    public void messageDeal(MessagesUpdateReqDTO reqDTO) {
        messagesService.dealMessagesInner(reqDTO);
    }

    @Override
    public boolean unreadFlag(Long userId) {
        return messagesService.getAllUnRead(userId);
    }


}
