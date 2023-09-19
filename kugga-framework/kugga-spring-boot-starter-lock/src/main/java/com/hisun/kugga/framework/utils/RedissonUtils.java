package com.hisun.kugga.framework.utils;

import com.hisun.kugga.framework.lock.UnableToAcquireLockException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-09-02 11:05
 * 不启用看门狗机制，手动设置释放锁时间
 * 1. 可重入锁（Reentrant Lock）  已实现
 * 2. 公平锁（Fair Lock）    已实现
 * 3. 联锁（MultiLock） 不实现
 * 4. 红锁（RedLock）   不实现
 * 5. 读写锁（ReadWriteLock）    已实现
 * 6. 信号量（Semaphore）    已实现，可实现限流
 * 7. 可过期性信号量（PermitExpirableSemaphore） 不实现，限流可实现 无单个信号量过期需求
 * 8. 闭锁（CountDownLatch）    不实现
 */
@Slf4j
@Component
public class RedissonUtils {

    private final static String LOCK_KEY_PREFIX = "lock:";

    @Resource
    private RedissonClient redissonClient;
    @Value("${spring.redisson.lock.wait-time}")
    private long waitTime;
    @Value("${spring.redisson.lock.lease-time}")
    private long leaseTime;
    private TimeUnit unit = TimeUnit.SECONDS;

    /**
     * 可重入锁
     * 迭代版本：可重载抛出自定义异常
     * 但是如果遇到需要其他进程也能解锁的情况，请使用分布式信号量Semaphore 对象
     *
     * @param lockKey key
     * @return
     */
    public boolean tryLock(String lockKey, Functions func) {
        RLock lock = redissonClient.getLock(LOCK_KEY_PREFIX + lockKey);
        return tryLock(waitTime, leaseTime, unit, lock, func);
    }

    public boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit, Functions func) {
        RLock lock = redissonClient.getLock(LOCK_KEY_PREFIX + lockKey);
        return tryLock(waitTime, leaseTime, unit, lock, func);
    }

    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit, RLock lock, Functions func) {
        // 尝试加锁，最多等待30秒，上锁以后10秒自动解锁
        boolean res;
        try {
            res = lock.tryLock(waitTime, leaseTime, unit);
            if (res) {
//                log.debug(Thread.currentThread().getId() + ",我抢到了一个锁");
                func.execute();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new UnableToAcquireLockException(e);
        } finally {
            lock.unlock();
        }
        return res;
    }

    /**
     * @param lockKey
     * @param sup
     * @param <T>
     * @return 自适应返回参数
     * @throws InterruptedException
     */
    public <T> T tryLock(String lockKey, Supplier<T> sup) {
        RLock lock = redissonClient.getLock(LOCK_KEY_PREFIX + lockKey);
        // 尝试加锁，最多等待30秒，上锁以后10秒自动解锁
        try {
            boolean tryLock = lock.tryLock(waitTime, leaseTime, unit);
            if (tryLock) {
//                log.debug(Thread.currentThread().getId() + ",我抢到了一个锁");
                return sup.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new UnableToAcquireLockException(e);
        } finally {
            lock.unlock();
        }
        return null;
    }

    /**
     * 公平锁
     *
     * @param lockKey
     * @param func
     * @return
     * @throws InterruptedException
     */
    public boolean tryFairLock(String lockKey, Functions func) {
        RLock rLock = redissonClient.getFairLock(LOCK_KEY_PREFIX + lockKey);
        return tryLock(waitTime, leaseTime, unit, rLock, func);
    }

    /**
     * 读写锁  写锁
     *
     * @param lockKey
     * @param func
     * @return
     * @throws InterruptedException
     */
    public boolean tryWriteLock(String lockKey, Functions func) {
        RReadWriteLock lock = redissonClient.getReadWriteLock(LOCK_KEY_PREFIX + lockKey);
        RLock rLock = lock.writeLock();
        return tryLock(waitTime, leaseTime, unit, rLock, func);
    }


    /**
     * 读写锁  读锁
     *
     * @param lockKey
     * @param func
     * @return
     * @throws InterruptedException
     */
    public boolean tryReadLock(String lockKey, Functions func) {
        RReadWriteLock lock = redissonClient.getReadWriteLock(LOCK_KEY_PREFIX + lockKey);
        RLock rLock = lock.readLock();
        return tryLock(waitTime, leaseTime, unit, rLock, func);
    }

    /**
     * 分布式信号量
     * 信号量:也可以用作限流
     *
     * @param lockKey
     * @param func
     * @return
     * @throws InterruptedException
     */
    public boolean trySemaphore(String lockKey, Functions func) {
        RSemaphore semaphore = redissonClient.getSemaphore(LOCK_KEY_PREFIX + lockKey);
        // 尝试加锁，最多等待30秒，上锁以后10秒自动解锁
        boolean tryLock;
        try {
            tryLock = semaphore.tryAcquire(waitTime, unit);
            if (tryLock) {
//                log.debug(Thread.currentThread().getId() + ",我抢到了一个锁." + lockKey);
                func.execute();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new UnableToAcquireLockException(e);
        } finally {
            semaphore.release();
        }
        return tryLock;
    }

    public void lock(String lockKey, Functions func) {
        RLock rLock = redissonClient.getLock(LOCK_KEY_PREFIX + lockKey);
        lock(rLock, func);
    }

    public void lock(RLock lock, Functions func) {
        lock(leaseTime, unit, lock, func);
    }

    public void lock(long leaseTime, TimeUnit unit, RLock lock, Functions func) {
        // 尝试加锁，一直等待，上锁以后10秒自动解锁
        lock.lock(leaseTime, unit);
        try {
//            log.debug(Thread.currentThread().getId() + ",我抢到了一个锁");
            func.execute();
        } finally {
            lock.unlock();
        }
    }
}
