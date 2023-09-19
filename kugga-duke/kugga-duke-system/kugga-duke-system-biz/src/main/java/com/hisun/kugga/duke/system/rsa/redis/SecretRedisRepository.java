package com.hisun.kugga.duke.system.rsa.redis;

import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.common.RedisConstants;
import com.hisun.kugga.duke.enums.SecretTypeEnum;
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
import static com.hisun.kugga.duke.system.rsa.redis.RedisKeyConstants.*;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;

@Repository
public class SecretRedisRepository {

    @Value("${duck.password.random.expires-time-min:5}")
    private Integer expiresTimeMinutes;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    private static String formatLoginKey(String key) {
        return String.format(SECRET_LOGIN_KEY.getKeyTemplate(), key);
    }

    private static String formatLoginUpdateKey(String key) {
        return String.format(SECRET_LOGIN_UPDATE_KEY.getKeyTemplate(), key);
    }

    private static String formatPayKey(String key) {
        return String.format(SECRET_PAY_KEY.getKeyTemplate(), key);
    }

    private static String formatPayUpdateKey(String key) {
        return String.format(SECRET_PAY_UPDATE_KEY.getKeyTemplate(), key);
    }


    /**
     * 根据场景设置随机数
     */
    public String getRandom(SecretTypeEnum typeEnum, String key) {
        String redisKey = getRedisKeyByType(typeEnum, key);
        return stringRedisTemplate.opsForValue().get(redisKey);
    }

    /**
     * 设置k-v
     */
    public void setRandom(SecretTypeEnum typeEnum, String key, String value) {
        String redisKey = getRedisKeyByType(typeEnum, key);
        stringRedisTemplate.opsForValue().set(redisKey, value,
                expiresTimeMinutes,
                TimeUnit.MINUTES);
    }

    public void deleteRandom(SecretTypeEnum typeEnum, String key) {
        String redisKey = getRedisKeyByType(typeEnum, key);
        stringRedisTemplate.delete(redisKey);
    }

    /**
     * lau 脚本删除
     *
     * @param typeEnum
     * @param key
     * @param value
     */
    public void deleteLuaRandom(SecretTypeEnum typeEnum, String key, String value) {
        //得到key值
        String redisKey = getRedisKeyByType(typeEnum, key);

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
     * @param typeEnum
     * @param key
     * @return
     */
    public String getRedisKeyByType(SecretTypeEnum typeEnum, String key) {
        if (ObjectUtil.equal(SecretTypeEnum.LOGIN, typeEnum)) {
            return formatLoginKey(key);
        } else if (ObjectUtil.equal(SecretTypeEnum.LOGIN_UPDATE, typeEnum)) {
            return formatLoginUpdateKey(key);
        } else if (ObjectUtil.equal(SecretTypeEnum.PAY, typeEnum)) {
            return formatPayKey(key);
        } else if (ObjectUtil.equal(SecretTypeEnum.PAY_UPDATE, typeEnum)) {
            return formatPayUpdateKey(key);
        } else {
            throw exception(PARAM_ERROR);
        }
    }
}
