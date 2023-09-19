package com.hisun.kugga.framework.file.config;

import com.hisun.kugga.framework.file.core.client.FileClientFactory;
import com.hisun.kugga.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author 芋道源码
 */
@Configuration
public class KuggaFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
