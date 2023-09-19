package com.hisun.kugga.duke.chat.socket.configuration;

import com.hisun.kugga.duke.chat.socket.schedule.MessageSessionUpdateSchedule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 开启定时任务
 */
@Configuration
@EnableScheduling
@ConditionalOnProperty(prefix = "duck.chat.message.schedule", value = "enable", matchIfMissing = true)
public class SessionConfiguration {

    @Bean
    public MessageSessionUpdateSchedule schedule() {
        return new MessageSessionUpdateSchedule();
    }

}
