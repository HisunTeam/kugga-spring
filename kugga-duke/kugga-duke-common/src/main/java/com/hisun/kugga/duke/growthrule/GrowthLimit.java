package com.hisun.kugga.duke.growthrule;

import java.util.Optional;

/**
 * @author: zhou_xiong
 */
public class GrowthLimit {
    /**
     * 成长值
     */
    private Integer perValue;
    /**
     * 每日可积累次数
     */
    private Integer dailyCount;

    public GrowthLimit(Integer perValue, Integer dailyCount) {
        this.perValue = perValue;
        this.dailyCount = Optional.ofNullable(dailyCount).orElse(Integer.MAX_VALUE);
    }

    public Integer getPerValue() {
        return perValue;
    }

    public void setPerValue(Integer perValue) {
        this.perValue = perValue;
    }

    public Integer getDailyCount() {
        return dailyCount;
    }

    public void setDailyCount(Integer dailyCount) {
        this.dailyCount = dailyCount;
    }

}
