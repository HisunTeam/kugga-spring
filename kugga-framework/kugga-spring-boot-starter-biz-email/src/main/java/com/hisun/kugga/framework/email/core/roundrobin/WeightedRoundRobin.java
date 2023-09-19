package com.hisun.kugga.framework.email.core.roundrobin;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author: zhou_xiong
 */
public class WeightedRoundRobin extends AbstractRoundRobin {

    private ReentrantLock lock = new ReentrantLock();

    public WeightedRoundRobin(Map<Invoker, Integer> invokersWeight) {
        if (invokersWeight != null && !invokersWeight.isEmpty()) {
            nodes = new ArrayList<>(invokersWeight.size());
            invokersWeight.forEach((invoker, weight) -> {
                if (invoker.isAvailable()) {
                    nodes.add(new Node(invoker, weight));
                }
            });
        } else {
            nodes = null;
        }
    }

    @Override
    public Invoker select() {
        if (!checkNodes()) {
            return null;
        } else if (nodes.size() == 1) {
            if (nodes.get(0).invoker.isAvailable()) {
                return nodes.get(0).invoker;
            } else {
                return null;
            }
        }
        Node nodeOfMaxWeight = nodes.get(0);
        Integer total = 0;
        lock.lock();
        try {
            for (Node node : nodes) {
                total += node.effectiveWeight;
                node.currentWeight += node.effectiveWeight;
                if (nodeOfMaxWeight == null) {
                    nodeOfMaxWeight = node;
                } else {
                    nodeOfMaxWeight = nodeOfMaxWeight.compareTo(node) > 0 ? nodeOfMaxWeight : node;
                }
            }
            nodeOfMaxWeight.currentWeight -= total;
        } finally {
            lock.unlock();
        }
        return nodeOfMaxWeight.invoker;
    }

}
