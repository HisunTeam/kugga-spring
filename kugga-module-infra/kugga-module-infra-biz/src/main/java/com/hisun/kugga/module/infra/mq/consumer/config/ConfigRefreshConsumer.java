package com.hisun.kugga.module.infra.mq.consumer.config;

import com.hisun.kugga.framework.apollo.internals.DBConfigRepository;
import com.hisun.kugga.framework.mq.core.pubsub.AbstractChannelMessageListener;
import com.hisun.kugga.module.infra.mq.message.config.ConfigRefreshMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 针对 {@link ConfigRefreshMessage} 的消费者
 *
 * @author 芋道源码
 */
@Component
@Slf4j
public class ConfigRefreshConsumer extends AbstractChannelMessageListener<ConfigRefreshMessage> {

    @Override
    public void onMessage(ConfigRefreshMessage message) {
        log.info("[onMessage][收到 Config 刷新消息]");
        DBConfigRepository.noticeSync();
    }

}
