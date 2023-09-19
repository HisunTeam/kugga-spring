package com.hisun.kugga.duke.league.dal.redis;

import com.hisun.kugga.duke.common.RedisKeyPrefixConstants;
import com.hisun.kugga.framework.redis.core.RedisKeyDefine;

import static com.hisun.kugga.framework.redis.core.RedisKeyDefine.KeyTypeEnum.STRING;

/**
 * 订单随机数key
 *
 * @author Lin
 */
public interface OrderRedisKeyConstants {

    /**
     * 加入公会
     */
    RedisKeyDefine ORDER_LEAGUE_JOIN_KEY = new RedisKeyDefine("",
            RedisKeyPrefixConstants.ORDER_LEAGUE_JOIN_KEY,
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

}
