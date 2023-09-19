package com.hisun.kugga.duke.league.dal.redis.league;

import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.common.RedisConstants;
import com.hisun.kugga.duke.enums.OrderType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.PARAM_ERROR;
import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.RANDOM_EXPIRE;
import static com.hisun.kugga.duke.league.dal.redis.OrderRedisKeyConstants.ORDER_LEAGUE_JOIN_KEY;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * @author Lin
 */
@Repository
public class OrderRedisRepository {

    @Value("${duck.order.random.expires-time-min:5}")
    private Integer expiresTimeMinutes;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static String formatLeagueJoinKey(String key) {
        return String.format(ORDER_LEAGUE_JOIN_KEY.getKeyTemplate(), key);
    }

    private static String formatLeagueCreateKey(String key) {
        return String.format(ORDER_LEAGUE_JOIN_KEY.getKeyTemplate(), key);
    }


    /**
     * 根据场景设置随机数
     */
    public String getRandom(OrderType orderType, String key) {
        String redisKey = getRedisKeyByType(orderType, key);
        return stringRedisTemplate.opsForValue().get(redisKey);
    }

    /**
     * 设置k-v
     */
    public void setRandom(OrderType orderType, String key, String value) {
        String redisKey = getRedisKeyByType(orderType, key);
        stringRedisTemplate.opsForValue().set(redisKey, value,
                expiresTimeMinutes,
                TimeUnit.MINUTES);
    }

    public void deleteRandom(OrderType orderType, String key) {
        String redisKey = getRedisKeyByType(orderType, key);
        stringRedisTemplate.delete(redisKey);
    }

    /**
     * lau 脚本删除
     *
     * @param orderType
     * @param key
     * @param value
     */
    public void deleteLuaRandom(OrderType orderType, String key, String value) {
        //得到key值
        String redisKey = getRedisKeyByType(orderType, key);

        DefaultRedisScript<Long> longDefaultRedisScript = new DefaultRedisScript<>(RedisConstants.LUA_SCRIPT, Long.class);
        List<String> keys = Collections.singletonList(redisKey);

        // 原子验证校验码
        Long result = stringRedisTemplate.execute(longDefaultRedisScript, keys, value);
        if (result == null || result == 0L) {
            throw exception(RANDOM_EXPIRE);
        }
    }

    /**
     * 根据类型获取对应key
     *
     * @param orderType 订单场景
     * @param key
     * @return
     */
    public String getRedisKeyByType(OrderType orderType, String key) {
        if (ObjectUtil.equal(OrderType.JOIN_LEAGUE, orderType)) {
            return formatLeagueJoinKey(key);
        } else if (ObjectUtil.equal(OrderType.CREATE_LEAGUE, orderType)) {
            return formatLeagueCreateKey(key);
        }/*else if (ObjectUtil.equal(SecretTypeEnum.LOGIN_UPDATE,typeEnum)){
            return formatLoginUpdateKey(key);
        }else if (ObjectUtil.equal(SecretTypeEnum.PAY,typeEnum)){
            return formatPayKey(key);
        }else if (ObjectUtil.equal(SecretTypeEnum.PAY_UPDATE,typeEnum)){
            return formatPayUpdateKey(key);
        }*/ else {
            throw exception(PARAM_ERROR);
        }
    }
}
