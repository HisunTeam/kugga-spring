package com.hisun.kugga.framework.email.core.roundrobin;


/**
 * @author: zhou_xiong
 */
public class Node implements Comparable<Node> {
    final Invoker invoker;
    Integer weight;
    Integer effectiveWeight;
    Integer currentWeight;

    public Node(Invoker invoker) {
        this.invoker = invoker;
        this.currentWeight = 0;
    }

    Node(Invoker invoker, Integer weight) {
        this.invoker = invoker;
        this.weight = weight;
        this.effectiveWeight = weight;
        this.currentWeight = 0;
    }

    @Override
    public int compareTo(Node o) {
        return currentWeight > o.currentWeight ? 1 : (currentWeight.equals(o.currentWeight) ? 0 : -1);
    }

}
