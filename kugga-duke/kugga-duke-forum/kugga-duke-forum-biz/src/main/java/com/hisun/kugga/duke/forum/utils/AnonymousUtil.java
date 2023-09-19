package com.hisun.kugga.duke.forum.utils;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;

import java.math.BigInteger;

/**
 * 实现匿名计算工具
 *
 * @author zc654
 */
public class AnonymousUtil {
    /**
     * 匿名
     *
     * @param source
     * @return
     */
    public static String md5Hex(String source) {
        String md5Str = MD5.create().digestHex16(source);
        BigInteger bigInteger = HexUtil.toBigInteger(md5Str);
        BigInteger mod = bigInteger.mod(new BigInteger("1000000"));
        return StrUtil.fillBefore(mod.toString(), '0', 6);
    }
}
