package com.hisun.kugga.framework.lock;

import com.hisun.kugga.framework.common.exception.ServerException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author yuzhou
 * @date 2020/3/26
 * @time 16:05
 * @since 2.0.0
 */
public class ProceedingJoinPointHelper {

    public static Method getMethod(ProceedingJoinPoint proceedingJoinPoint) {
        return Optional.of(proceedingJoinPoint)
                .map(ProceedingJoinPoint::getSignature)
                .filter(s -> s instanceof MethodSignature)
                .map(s -> (MethodSignature) s)
                .map(s -> getMethod(getTargetClass(proceedingJoinPoint), s.getName(), s.getParameterTypes()))
                .orElseThrow(() -> new ServerException("Could not found method by \"" + proceedingJoinPoint + "\""));
    }

    public static Class<?> getTargetClass(ProceedingJoinPoint proceedingJoinPoint) {
        return proceedingJoinPoint.getTarget().getClass();
    }

    public static Object[] getArgs(ProceedingJoinPoint proceedingJoinPoint) {
        return proceedingJoinPoint.getArgs();
    }

    private static Method getMethod(Class<?> targetClass, String methodName, Class[] parameterTypes) {
        try {
            return targetClass.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new ServerException(e);
        }
    }
}
