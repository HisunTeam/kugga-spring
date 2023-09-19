package com.hisun.kugga.framework.email.core.roundrobin;


/**
 * @author: zhou_xiong
 */
public interface Invoker {
    /**
     * 是否可用
     *
     * @return
     */
    Boolean isAvailable();

    /**
     * 标识
     *
     * @return
     */
    String id();

}
