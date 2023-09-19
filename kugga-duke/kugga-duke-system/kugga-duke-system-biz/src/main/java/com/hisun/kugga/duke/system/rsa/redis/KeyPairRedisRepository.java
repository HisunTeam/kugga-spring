package com.hisun.kugga.duke.system.rsa.redis;

import com.hisun.kugga.duke.system.rsa.bo.KeyPairBo;
import com.hisun.kugga.framework.common.util.json.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.hisun.kugga.duke.system.rsa.redis.RedisKeyConstants.DUKE_KEYPAIR_KEYS;
import static com.hisun.kugga.duke.system.rsa.redis.RedisKeyConstants.DUKE_KEYPAIR_KEY_ID;

@Repository
public class KeyPairRedisRepository {

    @Value("${duck.password.key-pair.expires-time-hour:24}")
    private Integer expiresTimeHour;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static String formatKeyPairKeyId() {
        return DUKE_KEYPAIR_KEY_ID.getKeyTemplate();
    }

    private static String formatKeyPairKeys(String key) {
        return String.format(DUKE_KEYPAIR_KEYS.getKeyTemplate(), key);
    }


    /**
     * 获取当前公钥Key
     */
    public String getKeyPairKeyId() {
        String redisKey = formatKeyPairKeyId();
        return stringRedisTemplate.opsForValue().get(redisKey);
    }

    /**
     * 设置当前公钥Key
     */
    public void setKeyPairKeyId(String publicKey) {
        String redisKey = formatKeyPairKeyId();
        stringRedisTemplate.opsForValue().set(redisKey, publicKey,
                expiresTimeHour,
                TimeUnit.HOURS);
    }

    /**
     *
     */
    public KeyPairBo getKeyPairKeys(String publicKey) {
        String redisKey = formatKeyPairKeys(publicKey);
        return JsonUtils.parseObject(stringRedisTemplate.opsForValue().get(redisKey), KeyPairBo.class);
    }

    public void setKeyPairKeys(KeyPairBo keyPairBo) {
        String redisKey = formatKeyPairKeys(keyPairBo.getPublicKey());
        stringRedisTemplate.opsForValue().set(redisKey,
                JsonUtils.toJsonString(keyPairBo),
                // 有效期稍微略长于 + 1h
                expiresTimeHour + 1,
                TimeUnit.HOURS);
    }

}
