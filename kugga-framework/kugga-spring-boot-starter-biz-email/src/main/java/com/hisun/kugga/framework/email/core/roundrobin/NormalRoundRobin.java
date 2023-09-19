package com.hisun.kugga.framework.email.core.roundrobin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author: zhou_xiong
 */
public class NormalRoundRobin extends AbstractRoundRobin {

    private final AtomicInteger position = new AtomicInteger();

    public NormalRoundRobin(List<Invoker> invokers) {
        nodes = new ArrayList<>(invokers.size());
        invokers.stream().filter(e -> e.isAvailable()).forEach(invoker -> nodes.add(new Node(invoker)));
    }

    @Override
    public Invoker select() {
        if (!checkNodes()) {
            return null;
        }
        int index = position.updateAndGet(p -> p + 1 < nodes.size() ? p + 1 : 0);
        return nodes.get(index).invoker;
    }
}
