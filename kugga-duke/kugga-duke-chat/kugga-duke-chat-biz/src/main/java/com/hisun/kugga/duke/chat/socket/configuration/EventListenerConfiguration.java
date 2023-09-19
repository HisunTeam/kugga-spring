package com.hisun.kugga.duke.chat.socket.configuration;

import com.corundumstudio.socketio.SocketIOServer;
import com.hisun.kugga.duke.chat.socket.listener.event.BeforeMessageEvent;
import com.hisun.kugga.duke.chat.socket.listener.event.MessageEvent;
import com.hisun.kugga.duke.chat.socket.listener.event.ReadMessageEvent;
import com.hisun.kugga.duke.chat.socket.listener.init.InitSocketEventHandler;
import com.hisun.kugga.framework.socketio.SocketioServerAutoConfiguration;
import com.hisun.kugga.framework.socketio.handler.AbstractSocketEventHandler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 */
@Configuration
@AutoConfigureAfter(SocketioServerAutoConfiguration.class)
@Import({
        /* 事件监听器 **/
//        RequestChatEvent.class,
        BeforeMessageEvent.class,
        MessageEvent.class,
        ReadMessageEvent.class
})
public class EventListenerConfiguration {

    @Bean
    public AbstractSocketEventHandler abstractSocketEventHandler(SocketIOServer socketIOServer) {
        return new InitSocketEventHandler(socketIOServer);
    }
}
