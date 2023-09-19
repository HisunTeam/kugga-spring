package com.hisun.kugga.framework.email.core.roundrobin;


/**
 * @author: zhou_xiong
 */
public interface RoundRobin {

    /**
     * 选择
     *
     * @return
     */
    Invoker select();

}
