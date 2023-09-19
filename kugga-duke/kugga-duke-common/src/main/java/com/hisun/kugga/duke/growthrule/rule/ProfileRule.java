package com.hisun.kugga.duke.growthrule.rule;

import com.hisun.kugga.duke.enums.GrowthType;
import com.hisun.kugga.duke.growthrule.AbstractGrowthRule;
import com.hisun.kugga.duke.growthrule.GrowthLimit;
import com.hisun.kugga.duke.utils.RedisUtils;
import org.springframework.stereotype.Component;

/**
 * 简历首次完善
 *
 * @author: zhou_xiong
 */
@Component
public class ProfileRule extends AbstractGrowthRule {

    public ProfileRule(RedisUtils redisUtils) {
        super(redisUtils);
    }

    @Override
    public GrowthType growthType() {
        return GrowthType.PROFILE;
    }

    @Override
    public GrowthLimit growthLimit() {
        return new GrowthLimit(40, null);
    }
}
