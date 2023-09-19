package com.hisun.kugga.duke.growthrule.rule;

import com.hisun.kugga.duke.enums.GrowthType;
import com.hisun.kugga.duke.growthrule.AbstractGrowthRule;
import com.hisun.kugga.duke.growthrule.GrowthLimit;
import com.hisun.kugga.duke.utils.RedisUtils;
import org.springframework.stereotype.Component;

/**
 * 与其他公会成员发起聊天
 *
 * @author: zhou_xiong
 */
@Component
public class ChatOtherRule extends AbstractGrowthRule {

    public ChatOtherRule(RedisUtils redisUtils) {
        super(redisUtils);
    }

    @Override
    public GrowthType growthType() {
        return GrowthType.CHAT_OTHER;
    }

    @Override
    public GrowthLimit growthLimit() {
        return new GrowthLimit(30, null);
    }
}
