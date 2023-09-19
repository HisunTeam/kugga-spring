package com.hisun.kugga.framework.mq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public abstract class AbstractRocketMQProducer<T> {

    private final RocketMQTemplate rocketMQTemplate;

    public AbstractRocketMQProducer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public void send(T message) {
        Message<T> build = MessageBuilder.withPayload(message).build();
        getRocketMQTemplate().send(getDestination(), build);
    }

    protected RocketMQTemplate getRocketMQTemplate() {
        return this.rocketMQTemplate;
    }

    abstract protected String getDestination();

}
