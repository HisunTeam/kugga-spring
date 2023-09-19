package com.hisun.kugga.framework.pay.config;

import com.hisun.kugga.framework.pay.core.client.PayClientFactory;
import com.hisun.kugga.framework.pay.core.client.impl.PayClientFactoryImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 支付配置类
 *
 * @author 芋道源码
 */
@Configuration
@EnableConfigurationProperties(PayProperties.class)
public class KuggaPayAutoConfiguration {

    @Bean
    public PayClientFactory payClientFactory() {
        return new PayClientFactoryImpl();
    }

}
