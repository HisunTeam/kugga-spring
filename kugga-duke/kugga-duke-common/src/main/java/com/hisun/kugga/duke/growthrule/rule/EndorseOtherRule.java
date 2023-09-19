package com.hisun.kugga.duke.growthrule.rule;

import com.hisun.kugga.duke.enums.GrowthType;
import com.hisun.kugga.duke.growthrule.AbstractGrowthRule;
import com.hisun.kugga.duke.growthrule.GrowthLimit;
import com.hisun.kugga.duke.utils.RedisUtils;
import org.springframework.stereotype.Component;

/**
 * 为其他公会认证
 *
 * @author: zhou_xiong
 */
@Component
public class EndorseOtherRule extends AbstractGrowthRule {

    public EndorseOtherRule(RedisUtils redisUtils) {
        super(redisUtils);
    }

    @Override
    public GrowthType growthType() {
        return GrowthType.ENDORSE_OTHER;
    }

    @Override
    public GrowthLimit growthLimit() {
        return new GrowthLimit(5, null);
    }
}
