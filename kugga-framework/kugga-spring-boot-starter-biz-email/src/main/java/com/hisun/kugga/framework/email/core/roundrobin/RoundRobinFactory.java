package com.hisun.kugga.framework.email.core.roundrobin;

import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.framework.email.core.property.CustomMailProperties;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author: zhou_xiong
 */
public class RoundRobinFactory {

    public static final String NORMAL = "normal";

    public static final String WEIGHTED = "weighted";

    public static RoundRobin create(String type, final Collection<CustomMailProperties> properties) {
        switch (type) {
            case WEIGHTED:
                Map<Invoker, Integer> invokerMap = new HashMap<>(properties.size());
                properties.stream().forEach(pro -> {
                    invokerMap.put(new Invoker() {

                        @Override
                        public Boolean isAvailable() {
                            return pro.getAvailable();
                        }

                        @Override
                        public String id() {
                            return pro.getUsername();
                        }

                        @Override
                        public int hashCode() {
                            int hash = 17;
                            hash = hash * 31 + id().hashCode();
                            return hash;
                        }

                        @Override
                        public boolean equals(Object o) {
                            if (this == o) {
                                return true;
                            }
                            if (o == null || getClass() != o.getClass()) {
                                return false;
                            }
                            Invoker invoker = (Invoker) o;
                            return StrUtil.equals(invoker.id(), id());
                        }
                    }, pro.getWeight());
                });
                return new WeightedRoundRobin(invokerMap);
            case NORMAL:
                List<Invoker> invokerList = properties.stream().map(pro -> new Invoker() {
                    @Override
                    public Boolean isAvailable() {
                        return pro.getAvailable();
                    }

                    @Override
                    public String id() {
                        return pro.getUsername();
                    }
                }).collect(Collectors.toList());
                return new NormalRoundRobin(invokerList);
            default:
                return null;
        }
    }
}
