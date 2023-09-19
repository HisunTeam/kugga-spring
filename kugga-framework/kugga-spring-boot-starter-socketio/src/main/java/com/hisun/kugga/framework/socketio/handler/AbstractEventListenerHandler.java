package com.hisun.kugga.framework.socketio.handler;

import com.corundumstudio.socketio.listener.DataListener;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public abstract class AbstractEventListenerHandler<T> {

    protected static Logger logger = LoggerFactory.getLogger(AbstractEventListenerHandler.class);

    /**
     * 事件名称
     */
    protected String eventName;

    /**
     *
     */
    protected DataListener<T> listener;

}
