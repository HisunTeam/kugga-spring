package com.hisun.kugga.framework.lock;

import com.hisun.kugga.framework.common.exception.ServerException;

/**
 * 不能获取锁
 *
 * @author toi from lemon
 * @date 2017年7月19日
 * @time 下午7:31:39
 */
public class UnableToAcquireLockException extends ServerException {

    private static final long serialVersionUID = 4121125716772813963L;

    public UnableToAcquireLockException() {
        super("UNABLE_ACQUIRE_DISTRIBUTED_LOCK");
    }

    public UnableToAcquireLockException(Exception e) {
        super("UNABLE_ACQUIRE_DISTRIBUTED_LOCK", e);
    }
}
