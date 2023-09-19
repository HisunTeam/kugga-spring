package com.hisun.kugga.duke.growthrule.factory;

import com.hisun.kugga.duke.enums.GrowthType;
import com.hisun.kugga.duke.growthrule.GrowthRule;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author: zhou_xiong
 */
@Component
public class GrowthRuleFactory implements ApplicationContextAware {
    private static Map<GrowthType, GrowthRule> rules = new HashMap<>(8);

    public GrowthRule getBy(GrowthType growthType) {
        return Optional.ofNullable(rules.get(growthType)).orElseThrow(() -> new RuntimeException("illegal growthType"));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, GrowthRule> beans = applicationContext.getBeansOfType(GrowthRule.class);
        beans.forEach((beanName, rule) ->
                rules.put(rule.growthType(), rule)
        );
    }
}
