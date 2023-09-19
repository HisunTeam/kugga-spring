package com.hisun.kugga.duke.system.rsa.redis;

import com.hisun.kugga.duke.common.RedisKeyPrefixConstants;
import com.hisun.kugga.duke.system.rsa.bo.KeyPairBo;
import com.hisun.kugga.framework.redis.core.RedisKeyDefine;

import static com.hisun.kugga.framework.redis.core.RedisKeyDefine.KeyTypeEnum.STRING;

/**
 * System Redis Key 枚举类
 */
public interface RedisKeyConstants {

    /**
     * 当前生效的Rsa公钥
     * k 是duke_keypair_key_id
     * v 是公钥
     */
    RedisKeyDefine DUKE_KEYPAIR_KEY_ID = new RedisKeyDefine("",
            "duke_keypair_key_id",
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

    /**
     * 公私钥对
     * k是prefix:公钥
     * v=公私钥对象
     * 它的有效期要比DUKE_KEYPAIR_KEY_ID稍长一点
     */
    RedisKeyDefine DUKE_KEYPAIR_KEYS = new RedisKeyDefine("",
            "duke_keypair_keys:%s",
            STRING, KeyPairBo.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);


    /**
     * 获取公钥时佩戴的token 随机数
     */
    RedisKeyDefine SECRET_LOGIN_KEY = new RedisKeyDefine("",
            RedisKeyPrefixConstants.SECRET_RANDOM_LOGIN_KEY,
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);
    RedisKeyDefine SECRET_LOGIN_UPDATE_KEY = new RedisKeyDefine("",
            RedisKeyPrefixConstants.SECRET_RANDOM_LOGIN_UPDATE_KEY,
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

    RedisKeyDefine SECRET_PAY_KEY = new RedisKeyDefine("",
            RedisKeyPrefixConstants.SECRET_RANDOM_PAY_KEY,
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

    RedisKeyDefine SECRET_PAY_UPDATE_KEY = new RedisKeyDefine("",
            RedisKeyPrefixConstants.SECRET_RANDOM_PAY_UPDATE_KEY,
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);
}
