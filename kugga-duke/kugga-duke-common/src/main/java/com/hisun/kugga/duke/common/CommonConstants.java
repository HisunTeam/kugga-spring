package com.hisun.kugga.duke.common;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.hisun.kugga.framework.common.exception.ServiceException;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: zhou_xiong
 */
public class CommonConstants {
    private CommonConstants() {
    }

    public static final String ZH_CN = "zh-CN";
    public static final String EN_US = "en-US";

    public static class WalletStatus {
        public static final String PREPAY = "prepay";
        public static final String PROCESSING = "processing";
        public static final String SUCCESS = "success";
        public static final String FAILED = "failed";
        public static final String CLOSED = "closed";
    }

    public static class BillStatus {
        public static final String SUCCESS = "Succeeded";
    }

    /**
     * 全局单例snowflake
     */
    public static Snowflake SNOWFLAKE = IdUtil.createSnowflake(1, NetUtil.ipv4ToLong(getLocalHostAddress()) % 31);


    public static String getLocalHostAddress() {
        String hostAddress = null;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.UNKNOW_HOST);
        }
        return hostAddress;
    }
}
