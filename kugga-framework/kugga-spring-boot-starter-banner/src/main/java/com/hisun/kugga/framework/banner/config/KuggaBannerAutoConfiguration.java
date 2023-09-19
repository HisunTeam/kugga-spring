package com.hisun.kugga.framework.banner.config;

import com.hisun.kugga.framework.banner.core.BannerApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Banner 的自动配置类
 *
 * @author 芋道源码
 */
@Configuration
public class KuggaBannerAutoConfiguration {

    @Bean
    public BannerApplicationRunner bannerApplicationRunner() {
        return new BannerApplicationRunner();
    }

}
