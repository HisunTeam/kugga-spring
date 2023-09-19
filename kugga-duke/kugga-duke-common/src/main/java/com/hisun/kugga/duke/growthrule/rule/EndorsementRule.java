package com.hisun.kugga.duke.growthrule.rule;

import com.hisun.kugga.duke.enums.GrowthType;
import com.hisun.kugga.duke.growthrule.AbstractGrowthRule;
import com.hisun.kugga.duke.growthrule.GrowthLimit;
import com.hisun.kugga.duke.utils.RedisUtils;
import org.springframework.stereotype.Component;

/**
 * 邀请其他公会认证
 *
 * @author: zhou_xiong
 */
@Component
public class EndorsementRule extends AbstractGrowthRule {

    public EndorsementRule(RedisUtils redisUtils) {
        super(redisUtils);
    }

    @Override
    public GrowthType growthType() {
        return GrowthType.ENDORSEMENT;
    }

    @Override
    public GrowthLimit growthLimit() {
        return new GrowthLimit(10, null);
    }
}
