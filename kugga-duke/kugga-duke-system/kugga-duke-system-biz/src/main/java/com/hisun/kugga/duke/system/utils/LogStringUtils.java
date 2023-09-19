package com.hisun.kugga.duke.system.utils;

import cn.hutool.core.util.StrUtil;

public class LogStringUtils {
    public static String shortBody(String body) {
        if (body != null) {
            return body.length() > 2500 ? StrUtil.sub(body, 0, 2499) : body;
        }
        return null;
    }
}
