package com.hisun.kugga.framework.apilog.config;

import com.hisun.kugga.framework.apilog.core.filter.ApiAccessLogFilter;
import com.hisun.kugga.framework.apilog.core.service.ApiAccessLogFrameworkServiceImpl;
import com.hisun.kugga.framework.apilog.core.service.ApiErrorLogFrameworkService;
import com.hisun.kugga.framework.apilog.core.service.ApiErrorLogFrameworkServiceImpl;
import com.hisun.kugga.framework.common.api.logger.ApiAccessLogApi;
import com.hisun.kugga.framework.common.api.logger.ApiErrorLogApi;
import com.hisun.kugga.framework.common.enums.WebFilterOrderEnum;
import com.hisun.kugga.framework.web.config.KuggaWebAutoConfiguration;
import com.hisun.kugga.framework.web.config.WebProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
@AutoConfigureAfter(KuggaWebAutoConfiguration.class)
public class KuggaApiLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ApiAccessLogApi apiAccessLogApi() {
        return i -> {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiErrorLogApi apiErrorLogApi() {
        return i -> {
        };
    }

    @Bean
    public ApiErrorLogFrameworkService apiErrorLogFrameworkService(ApiErrorLogApi apiErrorLogApi) {
        return new ApiErrorLogFrameworkServiceImpl(apiErrorLogApi);
    }

    /**
     * 创建 ApiAccessLogFilter Bean，记录 API 请求日志
     */
    @Bean
    @ConditionalOnProperty(prefix = "kugga.access-log", value = "enable", matchIfMissing = true)
    // 允许使用 kugga.access-log.enable=false 禁用访问日志
    public FilterRegistrationBean<ApiAccessLogFilter> apiAccessLogFilter(WebProperties webProperties,
                                                                         @Value("${spring.application.name}") String applicationName,
                                                                         ApiAccessLogApi apiAccessLogApi) {
        ApiAccessLogFrameworkServiceImpl apiAccessLogFrameworkService = new ApiAccessLogFrameworkServiceImpl(apiAccessLogApi);
        ApiAccessLogFilter filter = new ApiAccessLogFilter(webProperties, applicationName, apiAccessLogFrameworkService);
        return createFilterBean(filter, WebFilterOrderEnum.API_ACCESS_LOG_FILTER);
    }

    private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(order);
        return bean;
    }

}
