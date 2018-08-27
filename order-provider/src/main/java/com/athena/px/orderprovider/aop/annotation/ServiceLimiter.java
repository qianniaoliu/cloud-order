package com.athena.px.orderprovider.aop.annotation;

import java.lang.annotation.*;

/**
 * @Description:
 * @Author: ShenLong
 * @CreateDate: 2018/6/20 10:53
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLimiter {
    String description() default "";
}
