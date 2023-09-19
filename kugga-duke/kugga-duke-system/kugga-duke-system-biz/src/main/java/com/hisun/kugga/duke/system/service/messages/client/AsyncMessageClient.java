package com.hisun.kugga.duke.system.service.messages.client;

import com.hisun.kugga.duke.system.api.message.dto.RedDotReqDTO;
import com.hisun.kugga.duke.system.service.messages.RedDotService;
import com.hisun.kugga.duke.system.service.messages.bo.MessageBo;
import com.hisun.kugga.duke.system.service.messages.mq.MessageProducer;

import javax.annotation.Resource;
import java.util.List;

/**
 * 站内信通知消息 异步发送
 */
public class AsyncMessageClient extends AbstractMessageClient {

    @Resource
    private MessageProducer messageProducer;
    @Resource
    private RedDotService redDotService;

    @Override
    protected void doSendMessage(List<MessageBo> messageBos) {

        for (MessageBo messageBo : messageBos) {
            messageProducer.send(messageBo);
            redDotService.publish(new RedDotReqDTO()
                    .setUserId(messageBo.getReceiverId())
                    .setMessageRedDot(true));
        }
    }
}
