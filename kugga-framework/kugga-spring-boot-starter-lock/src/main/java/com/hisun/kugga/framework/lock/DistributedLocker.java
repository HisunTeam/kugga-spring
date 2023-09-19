package com.hisun.kugga.framework.lock;

import java.util.function.Supplier;

/**
 * 分布式锁
 *
 * @author toi from lemon
 * @date 2017年7月19日
 * @time 下午7:30:05
 */
public interface DistributedLocker {
    /**
     * 获取锁
     *
     * @param lockName 锁名
     * @param supplier 获取锁后的回调
     * @return callback 返回数据
     * @throws UnableToAcquireLockException
     * @throws Exception
     */
    <T> T lock(String lockName, Supplier<T> supplier) throws UnableToAcquireLockException;

    /**
     * 获取锁
     *
     * @param lockName
     * @param supplier
     * @param leaseTime 锁到期自动解锁时间 * 自动解锁时间，单位秒；
     *                  自动解锁时间一定得大于方法执行时间，否则会导致锁提前释放
     * @param waitTime  等待锁时间
     * @return
     * @throws UnableToAcquireLockException
     * @throws Exception
     */
    <T> T lock(String lockName, int leaseTime, int waitTime, Supplier<T> supplier) throws UnableToAcquireLockException;
}
