package com.hisun.kugga.duke.growthrule.rule;

import com.hisun.kugga.duke.enums.GrowthType;
import com.hisun.kugga.duke.growthrule.AbstractGrowthRule;
import com.hisun.kugga.duke.growthrule.GrowthLimit;
import com.hisun.kugga.duke.utils.RedisUtils;
import org.springframework.stereotype.Component;

/**
 * 回帖
 *
 * @author: zhou_xiong
 */
@Component
public class ReplyPostRule extends AbstractGrowthRule {

    public ReplyPostRule(RedisUtils redisUtils) {
        super(redisUtils);
    }

    @Override
    public GrowthType growthType() {
        return GrowthType.REPLY_POST;
    }

    @Override
    public GrowthLimit growthLimit() {
        return new GrowthLimit(1, 10);
    }
}
