package com.hisun.kugga.duke.common;

/**
 * @Description: redis 常量
 * @author： Lin
 * @Date 2022/9/16 15:37
 */
public class RedisConstants {
    /**
     * lua 脚本
     */
    public static final String LUA_SCRIPT = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
}
