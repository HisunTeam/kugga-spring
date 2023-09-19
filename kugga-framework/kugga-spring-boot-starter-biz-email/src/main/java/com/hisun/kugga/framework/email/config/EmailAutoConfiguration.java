package com.hisun.kugga.framework.email.config;

import com.hisun.kugga.framework.email.core.MailExecutor;
import com.hisun.kugga.framework.email.core.property.EmailProperties;
import com.hisun.kugga.framework.email.core.roundrobin.RoundRobin;
import com.hisun.kugga.framework.email.core.roundrobin.RoundRobinFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zhou_xiong
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "kugga.email", value = "enable", matchIfMissing = true)
@EnableConfigurationProperties(EmailProperties.class)
public class EmailAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RoundRobin getRoundRobin(EmailProperties emailProperties) {
        RoundRobin roundRobin = RoundRobinFactory.create(emailProperties.getRoundRobinType(),
                emailProperties.getProperties());
        return roundRobin;
    }

    @Bean
    @ConditionalOnMissingBean
    public MailExecutor mailExecutor(EmailProperties emailProperties, RoundRobin roundRobin) {
        return new MailExecutor(roundRobin, emailProperties);
    }

}
