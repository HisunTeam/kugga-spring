package com.hisun.kugga.framework.operatelog.config;

import com.hisun.kugga.framework.operatelog.core.aop.OperateLogAspect;
import com.hisun.kugga.framework.operatelog.core.service.OperateLogFrameworkService;
import com.hisun.kugga.framework.operatelog.core.service.OperateLogFrameworkServiceImpl;
import com.hisun.kugga.module.system.api.logger.OperateLogApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KuggaOperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService(OperateLogApi operateLogApi) {
        return new OperateLogFrameworkServiceImpl(operateLogApi);
    }

}
