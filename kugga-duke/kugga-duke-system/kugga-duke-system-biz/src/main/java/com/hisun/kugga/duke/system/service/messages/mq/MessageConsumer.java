package com.hisun.kugga.duke.system.service.messages.mq;

import cn.hutool.core.bean.BeanUtil;
import com.hisun.kugga.duke.system.api.message.dto.RedDotReqDTO;
import com.hisun.kugga.duke.system.dal.dataobject.MessagesDO;
import com.hisun.kugga.duke.system.dal.mysql.MessagesMapper;
import com.hisun.kugga.duke.system.service.messages.RedDotService;
import com.hisun.kugga.duke.system.service.messages.bo.MessageBo;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.hisun.kugga.duke.system.service.messages.mq.MessageConsumer.MESSAGE_GROUP;
import static com.hisun.kugga.duke.system.service.messages.mq.MessageConsumer.MESSAGE_TOPIC;

/**
 * 消息异步登记
 */
@Service
@RocketMQMessageListener(topic = MESSAGE_TOPIC, consumerGroup = MESSAGE_GROUP)
public class MessageConsumer implements RocketMQListener<MessageBo> {

    public static final String MESSAGE_TOPIC = "KUGGA_NOTIFY_MESSAGE_TOPIC";
    public static final String MESSAGE_GROUP = "KUGGA_NOTIFY_MESSAGE_GROUP";
    protected static Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    @Resource
    private MessagesMapper messagesMapper;

    @Override
    public void onMessage(MessageBo messageBo) {
        MessagesDO messagesDO = MessagesDO.builder().build();
        BeanUtil.copyProperties(messageBo, messagesDO);
        messagesMapper.insertOne(messagesDO);
    }
}

