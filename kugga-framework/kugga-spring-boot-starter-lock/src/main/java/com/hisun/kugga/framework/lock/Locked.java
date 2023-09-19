package com.hisun.kugga.framework.lock;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 标注一个方法，表示该方法会用分布式锁锁定
 *
 * @author toi from lemon
 * @date 2017年7月20日
 * @time 上午11:45:45
 * @deprecated as of context 2.0.0, in favor of using {@link DistributedLocked}
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Deprecated
public @interface Locked {

    /**
     * 锁名
     *
     * @return
     */
    @AliasFor("lockName")
    String value() default "";

    /**
     * 锁名
     *
     * @return
     */
    @AliasFor("value")
    String lockName() default "";

    /**
     * 等待锁超时时间，单位：秒
     * 默认30s
     *
     * @return
     */
    int waitTime() default 30;

    /**
     * 自动解锁时间，单位秒
     * 自动解锁时间一定得大于方法执行时间，否则会导致锁提前释放
     * 默认100s
     *
     * @return
     */
    int leaseTime() default 100;

    /**
     * 忽略所有异常，否则会往外抛
     *
     * @return
     */
    boolean ignoreException() default false;

    /**
     * 忽略没有获取到锁的异常，默认为true
     *
     * @return
     */
    boolean ignoreUnableToAcquiredLockException() default true;
}
