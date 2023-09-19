package com.hisun.kugga.duke.growthrule.rule;

import com.hisun.kugga.duke.enums.GrowthType;
import com.hisun.kugga.duke.growthrule.AbstractGrowthRule;
import com.hisun.kugga.duke.growthrule.GrowthLimit;
import com.hisun.kugga.duke.utils.RedisUtils;
import org.springframework.stereotype.Component;

/**
 * 为其他公会成员撰写推荐报告
 *
 * @author: zhou_xiong
 */
@Component
public class WriteRecommendationRule extends AbstractGrowthRule {

    public WriteRecommendationRule(RedisUtils redisUtils) {
        super(redisUtils);
    }

    @Override
    public GrowthType growthType() {
        return GrowthType.WRITE_RECOMMENDATION;
    }

    @Override
    public GrowthLimit growthLimit() {
        return new GrowthLimit(20, null);
    }
}
