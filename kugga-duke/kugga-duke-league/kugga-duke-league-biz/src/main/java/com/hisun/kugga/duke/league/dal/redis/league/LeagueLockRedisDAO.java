package com.hisun.kugga.duke.league.dal.redis.league;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.hisun.kugga.duke.league.dal.redis.RedisKeyConstants.*;

/**
 * 公会交易加锁处理
 *
 * @author zuocheng
 */
@Repository
public class LeagueLockRedisDAO {
    @Resource
    private RedissonClient redissonClient;

    public void lock(Long id, Long timeoutMillis, Runnable runnable) {
        String lockKey = formatKey(id);
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock(timeoutMillis, TimeUnit.MILLISECONDS);
            // 执行逻辑
            runnable.run();
        } finally {
            lock.unlock();
        }
    }

    public void lockTask(Long id, Long timeoutMillis, Runnable runnable) {
        String lockKey = formatTaskKey(id);
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock(timeoutMillis, TimeUnit.MILLISECONDS);
            // 执行逻辑
            runnable.run();
        } finally {
            lock.unlock();
        }
    }

    public void lockReport(Long id, Long timeoutMillis, Runnable runnable) {
        String lockKey = formatReportKey(id);
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock(timeoutMillis, TimeUnit.MILLISECONDS);
            // 执行逻辑
            runnable.run();
        } finally {
            lock.unlock();
        }
    }

    private static String formatKey(Long id) {
        return String.format(FIRST_JOIN_LEAGUE_LOCK.getKeyTemplate(), id);
    }

    private static String formatTaskKey(Long id) {
        return String.format(LEAGUE_TASK_LOCK.getKeyTemplate(), id);
    }

    private static String formatReportKey(Long id) {
        return String.format(LEAGUE_REPORT_LOCK.getKeyTemplate(), id);
    }

}
