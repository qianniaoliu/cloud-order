package com.athena.px.orderprovider.aop.aspect;

import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * @Description:
 * @Author: ShenLong
 * @CreateDate: 2018/6/20 10:45
 */
@Aspect
@Scope
@Component
public class LimiterAspect {

    private static RateLimiter rateLimiter = RateLimiter.create(100.0);

    private final static Logger logger = LoggerFactory.getLogger(LimiterAspect.class);

    @Pointcut("@annotation(com.athena.px.orderprovider.aop.annotation.ServiceLimiter)")
    public void serviceAspectAnnotation(){}

    @Pointcut("execution(* com.athena.px.orderprovider.serviceimpl..*.*(..))")
    public void serviceAspectMethod(){}


    /**
     * 限流
     * @param joinPoint
     */
    @Around("serviceAspectAnnotation()")
    public void around(ProceedingJoinPoint joinPoint){
        Boolean flag = rateLimiter.tryAcquire();
        try {
            if(flag) {
                joinPoint.proceed();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Before("serviceAspectMethod()")
    public void before(JoinPoint joinPoint){
        logger.info("方法签名为:" + joinPoint.getSignature());
    }
}
