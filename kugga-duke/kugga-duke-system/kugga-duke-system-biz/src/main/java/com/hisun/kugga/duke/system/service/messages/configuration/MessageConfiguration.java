package com.hisun.kugga.duke.system.service.messages.configuration;

import com.hisun.kugga.duke.system.service.messages.client.AsyncMessageClient;
import com.hisun.kugga.duke.system.service.messages.client.MessageClient;
import com.hisun.kugga.duke.system.service.messages.client.SyncMessageClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置同步或者异步发送消息
 */
@Configuration
public class MessageConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "kugga.message.async", value = "enable", matchIfMissing = true)
    public MessageClient asyncMessageClient() {
        return new AsyncMessageClient();
    }

    @Bean
    @ConditionalOnMissingBean
//    @ConditionalOnProperty(prefix = "kugga.message.sync", value = "enable")
    public MessageClient syncMessageClient() {
        return new SyncMessageClient();
    }

}
