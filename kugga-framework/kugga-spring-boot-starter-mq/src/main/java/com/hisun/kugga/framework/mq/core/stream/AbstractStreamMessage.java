package com.hisun.kugga.framework.mq.core.stream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hisun.kugga.framework.mq.core.message.AbstractRedisMessage;

/**
 * Redis Stream Message 抽象类
 *
 * @author 芋道源码
 */
public abstract class AbstractStreamMessage extends AbstractRedisMessage {

    /**
     * 获得 Redis Stream Key
     *
     * @return Channel
     */
    @JsonIgnore // 避免序列化
    public abstract String getStreamKey();

}
