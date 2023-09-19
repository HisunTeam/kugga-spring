package com.hisun.kugga.framework.lock;

import cn.hutool.core.lang.Assert;
import com.hisun.kugga.framework.common.util.date.DateTimeUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Redison实现 分布式锁
 *
 * @author toi from lemon
 * @date 2017年7月19日
 * @time 下午7:40:44
 */
public class RedisDistributedLocker implements DistributedLocker, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(RedisDistributedLocker.class);

    private final static String LOCKER_PREFIX = "LOCK:";

    private Integer defaultLeaseTime;

    private Integer defaultWaitTime;

    private RedissonClient redissonClient;

    /**
     * @param redissonClient   redisson client
     * @param defaultLeaseTime 默认自动解锁时间
     * @param defaultWaitTime  默认的获取锁等待时间
     */
    public RedisDistributedLocker(RedissonClient redissonClient,
                                  Integer defaultLeaseTime,
                                  Integer defaultWaitTime) {
        this.redissonClient = redissonClient;
        this.defaultLeaseTime = defaultLeaseTime;
        this.defaultWaitTime = defaultWaitTime;
    }

    @Override
    public <T> T lock(String lockName, Supplier<T> supplier)
            throws UnableToAcquireLockException {
        return lock(lockName, this.defaultLeaseTime, defaultWaitTime, supplier);
    }

    @Override
    public <T> T lock(String lockName, int leaseTime, int waitTime, Supplier<T> supplier)
            throws UnableToAcquireLockException {
        RLock lock = redissonClient.getLock(LOCKER_PREFIX + lockName);
        Instant startInstant = Instant.now();
        boolean acquired;
        try {
            acquired = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new UnableToAcquireLockException(e);
        }

        if (acquired) {
            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("Acquired distributed lock with name \"{}\", wait time {}/ms. ", lockName, DateTimeUtils.durationMillis(startInstant, Instant.now()));
                }
                return supplier.get();
            } finally {
                lock.unlock();
                if (logger.isDebugEnabled()) {
                    logger.debug("Release distributed lock with name \"{}\". lock time {}/ms", lockName, DateTimeUtils.durationMillis(startInstant, Instant.now()));
                }
            }
        }
        throw new UnableToAcquireLockException();
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.defaultLeaseTime, "Default expire time must not be null, please configure with key \"kugga.lock.defaultLeaseTime\"");
        Assert.notNull(this.defaultWaitTime, "Default expire time must not be null, please configure with key \"kugga.lock.defaultWaitTime\"");
        Assert.notNull(this.redissonClient, "Redisson client can not be null.");
    }

}
