package com.hisun.kugga.framework.lock;

import cn.hutool.core.lang.Assert;
import com.hisun.kugga.framework.common.exception.ServerException;
import com.hisun.kugga.framework.common.exception.ServiceException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.Ordered;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;

import javax.annotation.Resource;
import java.lang.reflect.Method;

import static com.hisun.kugga.framework.lock.ProceedingJoinPointHelper.getArgs;
import static com.hisun.kugga.framework.lock.ProceedingJoinPointHelper.getMethod;
import static org.springframework.aop.support.AopUtils.getTargetClass;

/**
 * 分布式锁Aspect
 *
 * @author toi from lemon
 * @date 2017年7月20日
 * @time 上午11:51:42
 */
@Configuration
@Aspect
public class DistributedLockerAspect implements Ordered {
    private static final Logger logger = LoggerFactory.getLogger(DistributedLockerAspect.class);

    private final LockOperationExpressionEvaluator evaluator = new LockOperationExpressionEvaluator();

    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Resource
    private DistributedLocker distributedLocker;

    public static boolean isBusinessException(Throwable throwable) {
        return throwable instanceof Exception && isBusinessException((Exception) throwable);
    }

    public static boolean isBusinessException(Exception exception) {
        return exception instanceof ServiceException;
    }

    @Around("@annotation(locked)")
    public Object lock(ProceedingJoinPoint pjp, Locked locked) {
        return doLock(readMetadata(locked), pjp);
    }

    @Around("@annotation(distributedLocked)")
    public Object distributedLock(ProceedingJoinPoint pjp, DistributedLocked distributedLocked) {
        return doLock(readMetadata(distributedLocked), pjp);
    }

    private Object doLock(LockMetadata lockMetadata, ProceedingJoinPoint pjp) {
        Assert.notEmpty(lockMetadata.getLockName());
        String lockName = evaluateLockName(lockMetadata.getLockName(), pjp);
        try {
            return distributedLocker.lock(lockName, lockMetadata.getLeaseTime(), lockMetadata.getWaitTime(),
                    () -> proceed(pjp));
        } catch (UnableToAcquireLockException e) {
            if (!(lockMetadata.isIgnoreUnableToAcquiredLockException() || lockMetadata.isIgnoreException())) {
                throw e;
            }
            if (logger.isWarnEnabled()) {
                logger.warn("Failed to acquire distributed lock with name {}, lease time {}, wait time {} at method {}",
                        lockMetadata.getLockName(), lockMetadata.getLeaseTime(), lockMetadata.getWaitTime(), pjp.getSignature().getName());
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Unable to acquire distributed lock.", e);
            }
        } catch (ServiceException e) {
            if (!lockMetadata.isIgnoreException()) {
                throw e;
            }
            if (logger.isWarnEnabled()) {
                logger.warn("Failed to executing method \"" + pjp.getSignature().getName() + "\".", e);
            }
        } catch (Throwable e) {
            if (isBusinessException(e)) {
                throw e;
            }
            if (!lockMetadata.isIgnoreException()) {
                throw new ServerException(e);
            }
            if (logger.isWarnEnabled()) {
                logger.warn("Unexpected error occurred during executing method \"" + pjp.getSignature().getName() + "\".", e);
            }
        }
        return null;
    }

    private String evaluateLockName(String lockName, ProceedingJoinPoint pjp) {
        Class<?> targetClass = getTargetClass(pjp);
        Method method = getMethod(pjp);
        LockExpressionRootObject rootObject = new LockExpressionRootObject(method, getArgs(pjp),
                pjp.getTarget(), targetClass);
        EvaluationContext evaluationContext = new LockEvaluationContext(rootObject, method,
                getArgs(pjp), this.getParameterNameDiscoverer());
        AnnotatedElementKey elementKey = new AnnotatedElementKey(method, targetClass);
        return (String) evaluator.lockName(lockName, elementKey, evaluationContext);
    }

    public Object proceed(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            if (isBusinessException(e)) {
                throw (ServiceException) e;
            }
            throw new ServerException(e);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE - 10;
    }

    private LockMetadata readMetadata(DistributedLocked distributedLocked) {
        return new LockMetadata(distributedLocked.lockName(), distributedLocked.waitTime(), distributedLocked.leaseTime(), distributedLocked.ignoreException(), distributedLocked.ignoreUnableToAcquiredLockException());
    }

    private LockMetadata readMetadata(Locked distributedLocked) {
        return new LockMetadata(distributedLocked.lockName(), distributedLocked.waitTime(), distributedLocked.leaseTime(), distributedLocked.ignoreException(), distributedLocked.ignoreUnableToAcquiredLockException());
    }

    public ParameterNameDiscoverer getParameterNameDiscoverer() {
        return parameterNameDiscoverer;
    }

    public static class LockMetadata {
        /**
         * 锁名
         */
        private String lockName;
        /**
         * 等待锁超时时间，单位：秒
         * 默认30s
         */
        private int waitTime;

        /**
         * 自动解锁时间，单位秒
         * 自动解锁时间一定得大于方法执行时间，否则会导致锁提前释放
         * 默认100s
         */
        private int leaseTime;

        /**
         * 忽略所有异常，否则会往外抛
         */
        private boolean ignoreException;

        /**
         * 忽略没有获取到锁的异常，默认为true
         */
        private boolean ignoreUnableToAcquiredLockException;

        public LockMetadata(String lockName, int waitTime, int leaseTime, boolean ignoreException, boolean ignoreUnableToAcquiredLockException) {
            this.lockName = lockName;
            this.waitTime = waitTime;
            this.leaseTime = leaseTime;
            this.ignoreException = ignoreException;
            this.ignoreUnableToAcquiredLockException = ignoreUnableToAcquiredLockException;
        }

        public String getLockName() {
            return lockName;
        }

        public void setLockName(String lockName) {
            this.lockName = lockName;
        }

        public int getWaitTime() {
            return waitTime;
        }

        public void setWaitTime(int waitTime) {
            this.waitTime = waitTime;
        }

        public int getLeaseTime() {
            return leaseTime;
        }

        public void setLeaseTime(int leaseTime) {
            this.leaseTime = leaseTime;
        }

        public boolean isIgnoreException() {
            return ignoreException;
        }

        public void setIgnoreException(boolean ignoreException) {
            this.ignoreException = ignoreException;
        }

        public boolean isIgnoreUnableToAcquiredLockException() {
            return ignoreUnableToAcquiredLockException;
        }

        public void setIgnoreUnableToAcquiredLockException(boolean ignoreUnableToAcquiredLockException) {
            this.ignoreUnableToAcquiredLockException = ignoreUnableToAcquiredLockException;
        }
    }
}
