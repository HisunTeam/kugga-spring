package com.hisun.kugga.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author toi from lemon
 */
@ConfigurationProperties(prefix = "kugga.lock")
public class LockProperties {
    private Integer defaultLeaseTime;
    private Integer defaultWaitTime;

    public Integer getDefaultLeaseTime() {
        return defaultLeaseTime;
    }

    public void setDefaultLeaseTime(Integer defaultLeaseTime) {
        this.defaultLeaseTime = defaultLeaseTime;
    }

    public Integer getDefaultWaitTime() {
        return defaultWaitTime;
    }

    public void setDefaultWaitTime(Integer defaultWaitTime) {
        this.defaultWaitTime = defaultWaitTime;
    }

}
