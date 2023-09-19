package com.hisun.kugga.duke.system.service.messages.mq;

import com.hisun.kugga.duke.system.service.messages.bo.MessageBo;
import com.hisun.kugga.framework.mq.AbstractRocketMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.hisun.kugga.duke.system.service.messages.mq.MessageConsumer.MESSAGE_TOPIC;

/**
 * 站内通知消息发送
 */
@Service
public class MessageProducer extends AbstractRocketMQProducer<MessageBo> {

    @Autowired
    public MessageProducer(RocketMQTemplate rocketMQTemplate) {
        super(rocketMQTemplate);
    }

//    @Override
//    public void send(MessageBo messageBo) {
//        super.send(messageBo);
//    }

    @Override
    protected String getDestination() {
        return MESSAGE_TOPIC;
    }

}
