package com.hisun.kugga.framework.email.core.roundrobin;

import java.util.List;


/**
 * @author: zhou_xiong
 */
public abstract class AbstractRoundRobin implements RoundRobin {

    protected List<Node> nodes;

    protected boolean checkNodes() {
        return nodes != null && nodes.size() > 0;
    }

}
