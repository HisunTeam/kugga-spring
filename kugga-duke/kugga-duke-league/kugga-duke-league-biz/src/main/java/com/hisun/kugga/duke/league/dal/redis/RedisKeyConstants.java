package com.hisun.kugga.duke.league.dal.redis;

import com.hisun.kugga.framework.redis.core.RedisKeyDefine;
import org.redisson.api.RLock;

/**
 * 公会 Redis Key 枚举类
 *
 * @author zuocheng
 */
public interface RedisKeyConstants {
    /**
     * 第一个加入公会的需要加锁处理（有打款,必须加个锁）
     * Redisson 的 Lock 锁，使用 Hash 数据结构
     * 参数来自 DefaultLockKeyBuilder 类
     */
    RedisKeyDefine FIRST_JOIN_LEAGUE_LOCK = new RedisKeyDefine("首次加入公会任务的分布式锁",
            "first_join_league:lock:",
            RedisKeyDefine.KeyTypeEnum.HASH, RLock.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

    RedisKeyDefine LEAGUE_TASK_LOCK = new RedisKeyDefine("认证/聊天 锁",
            "league_task:lock:",
            RedisKeyDefine.KeyTypeEnum.HASH, RLock.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

    RedisKeyDefine LEAGUE_REPORT_LOCK = new RedisKeyDefine("推荐报告 锁",
            "league_report:lock:",
            RedisKeyDefine.KeyTypeEnum.HASH, RLock.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);
}
