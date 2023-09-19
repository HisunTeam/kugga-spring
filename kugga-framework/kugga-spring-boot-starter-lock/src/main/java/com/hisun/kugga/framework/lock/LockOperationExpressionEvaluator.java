package com.hisun.kugga.framework.lock;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author toi from lemon
 * @date 2020/3/26
 * @time 15:05
 * @since 2.0.0
 */
public class LockOperationExpressionEvaluator extends CachedExpressionEvaluator {

    private final Map<ExpressionKey, Expression> keyCache = new ConcurrentHashMap<>(64);

    public EvaluationContext createEvaluationContext(Method method, Object[] args, Object target, Class<?> targetClass, Method targetMethod,
                                                     @Nullable Object result, @Nullable BeanFactory beanFactory) {

        LockExpressionRootObject rootObject = new LockExpressionRootObject(method, args, target, targetClass);
        LockEvaluationContext evaluationContext = new LockEvaluationContext(
                rootObject, targetMethod, args, getParameterNameDiscoverer());
        if (beanFactory != null) {
            evaluationContext.setBeanResolver(new BeanFactoryResolver(beanFactory));
        }
        return evaluationContext;
    }

    public Object lockName(String lockNameExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
        return getExpression(this.keyCache, methodKey, lockNameExpression).getValue(evalContext);
    }
}
