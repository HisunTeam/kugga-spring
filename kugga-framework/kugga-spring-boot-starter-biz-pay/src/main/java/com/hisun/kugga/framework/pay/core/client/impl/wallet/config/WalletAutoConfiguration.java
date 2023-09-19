package com.hisun.kugga.framework.pay.core.client.impl.wallet.config;

import com.hisun.kugga.framework.pay.core.client.impl.wallet.WalletInvoker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zhou_xiong
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(WalletProperties.class)
public class WalletAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public WalletInvoker walletInvoker(WalletProperties walletProperties) {
        return new WalletInvoker(walletProperties);
    }
}
