package com.hisun.kugga.duke.growthrule;

import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.duke.dto.GrowthDTO;
import com.hisun.kugga.duke.enums.GrowthType;
import com.hisun.kugga.duke.utils.RedisUtils;
import com.hisun.kugga.framework.common.util.date.DateTimeUtils;
import com.hisun.kugga.framework.lock.DistributedLocked;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author: zhou_xiong
 */
@Slf4j
public abstract class AbstractGrowthRule implements GrowthRule {
    public static final String CURRENT_COUNT_PREFIX = "GROWTH_VALUE_COUNT_";
    public static final String CURRENT_SUM_PREFIX = "GROWTH_VALUE_SUM";
    public static final Integer DEFAULT_DAILY_SUM = 50;
    protected GrowthType growthType;
    protected GrowthLimit growthLimit;
    protected RedisUtils redisUtils;

    public AbstractGrowthRule(RedisUtils redisUtils) {
        this.growthLimit = this.growthLimit();
        this.growthType = this.growthType();
        this.redisUtils = redisUtils;
    }

    @Override
    @DistributedLocked(lockName = "'growthValue:'+#t.getLeagueId()+'_'+#t.getUserId()", leaseTime = 5, waitTime = 5)
    public <T extends GrowthDTO> Integer growthValue(T t, Consumer<T> consumer) {
        String sumKey = buildKey(CURRENT_SUM_PREFIX, t.getLeagueId(), t.getUserId());
        String countKey = buildKey(CURRENT_COUNT_PREFIX + growthType.name(), t.getLeagueId(), t.getUserId());
        Integer count = this.init(countKey);
        if (count >= growthLimit.getDailyCount()) {
            log.info("leagueId:{}下的userId:{}触发了{}的每日可累计次数:{}", t.getLeagueId(), t.getUserId(),
                    this.getClass().getSimpleName(), growthLimit.getDailyCount());
            return 0;
        }
        redisUtils.increment(countKey);
        Integer currentSum = this.init(sumKey);
        if (currentSum >= DEFAULT_DAILY_SUM) {
            log.info("leagueId:{}下的userId:{}触发了{}的每日可获得成长值上限:{}", t.getLeagueId(), t.getUserId(),
                    this.getClass().getSimpleName(), DEFAULT_DAILY_SUM);
            return 0;
        }
        int diff = DEFAULT_DAILY_SUM - currentSum;
        int growthValue = growthLimit.getPerValue() > diff ? diff : growthLimit.getPerValue();
        redisUtils.increment(sumKey, growthValue);
        t.setGrowthValue(growthValue);
        t.setGrowthType(growthType);
        consumer.accept(t);
        return growthValue;
    }

    private String buildKey(String keyPrefix, Long leagueId, Long userId) {
        return StrUtil.concat(true, keyPrefix, ":", DateTimeUtils.getCurrentDateStr(), ":",
                leagueId.toString(), "_", userId.toString());
    }

    private Integer init(String key) {
        Integer num = redisUtils.getInteger(key);
        return Optional.ofNullable(num).orElseGet(() -> {
            redisUtils.setForTimeCustom(key, 0, 24, TimeUnit.HOURS);
            return 0;
        });
    }
}
