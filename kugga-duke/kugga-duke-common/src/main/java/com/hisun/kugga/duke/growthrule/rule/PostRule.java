package com.hisun.kugga.duke.growthrule.rule;

import com.hisun.kugga.duke.enums.GrowthType;
import com.hisun.kugga.duke.growthrule.AbstractGrowthRule;
import com.hisun.kugga.duke.growthrule.GrowthLimit;
import com.hisun.kugga.duke.utils.RedisUtils;
import org.springframework.stereotype.Component;

/**
 * 发帖
 *
 * @author: zhou_xiong
 */
@Component
public class PostRule extends AbstractGrowthRule {

    public PostRule(RedisUtils redisUtils) {
        super(redisUtils);
    }

    @Override
    public GrowthType growthType() {
        return GrowthType.POST;
    }

    @Override
    public GrowthLimit growthLimit() {
        return new GrowthLimit(3, 5);
    }
}
