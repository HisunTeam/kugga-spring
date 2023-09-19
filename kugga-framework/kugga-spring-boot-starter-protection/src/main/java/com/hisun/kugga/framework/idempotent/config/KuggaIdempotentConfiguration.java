package com.hisun.kugga.framework.idempotent.config;

import com.hisun.kugga.framework.idempotent.core.aop.IdempotentAspect;
import com.hisun.kugga.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import com.hisun.kugga.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import com.hisun.kugga.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import com.hisun.kugga.framework.idempotent.core.redis.IdempotentRedisDAO;
import com.hisun.kugga.framework.redis.config.KuggaRedisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(KuggaRedisAutoConfiguration.class)
public class KuggaIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
