package com.hisun.kugga.framework.config;

import com.hisun.kugga.framework.lock.DistributedLocker;
import com.hisun.kugga.framework.lock.DistributedLockerAspect;
import com.hisun.kugga.framework.lock.RedisDistributedLocker;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 分布式锁配置
 *
 * @author toi from lemon
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({RedissonClient.class, DistributedLocker.class})
@ConditionalOnBean(RedissonClient.class)
@EnableConfigurationProperties(LockProperties.class)
@AutoConfigureAfter(RedissonAutoConfiguration.class)
public class DistributedLockAutoConfiguration {

    public static final int DEFAULT_LEASE_TIME = 100;
    public static final int DEFAULT_WAIT_TIME = 30;

    private LockProperties lockProperties;

    public DistributedLockAutoConfiguration(LockProperties lockProperties) {
        this.lockProperties = lockProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public DistributedLocker distributedLocker(RedissonClient redissonClient) {
        return new RedisDistributedLocker(redissonClient,
                getDefaultIfNull(this.lockProperties.getDefaultLeaseTime(), DEFAULT_LEASE_TIME),
                getDefaultIfNull(this.lockProperties.getDefaultWaitTime(), DEFAULT_WAIT_TIME));
    }

    @Configuration(proxyBeanMethods = false)
    @Import(DistributedLockerAspect.class)
    public static class LockAspectConfiguration {

    }

    public static Integer getDefaultIfNull(Integer value, Integer defaultVal) {
        if (null == value) {
            return defaultVal;
        }
        return value;
    }

}
