package com.hisun.kugga.framework.lock;

import java.lang.reflect.Method;

/**
 * @author toi from lemon
 * @date 2020/3/26
 * @time 15:37
 * @since 2.0.0
 */
public class LockExpressionRootObject {
    private final Method method;

    private final Object[] args;

    private final Object target;

    private final Class<?> targetClass;


    public LockExpressionRootObject(Method method, Object[] args, Object target, Class<?> targetClass) {

        this.method = method;
        this.target = target;
        this.targetClass = targetClass;
        this.args = args;
    }

    public Method getMethod() {
        return this.method;
    }

    public String getMethodName() {
        return this.method.getName();
    }

    public Object[] getArgs() {
        return this.args;
    }

    public Object getTarget() {
        return this.target;
    }

    public Class<?> getTargetClass() {
        return this.targetClass;
    }
}
