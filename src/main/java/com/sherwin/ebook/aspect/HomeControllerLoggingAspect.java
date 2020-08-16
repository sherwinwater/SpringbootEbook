package com.sherwin.ebook.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for logging execution.
 * *
 */
@Aspect
@Component
@Order(0)
public class HomeControllerLoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Run before the method execution.
     *
     * @param joinPoint
     */
    @Before("execution(* com.sherwin.ebook.home.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("logBefore running from home.....");
        log.info("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

    }

    /**
     * Run after the method returned a result.
     *
     * @param joinPoint
     */
	@After("execution(* com.sherwin.ebook.home.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        log.info("logAfter running from home.....");
        log.info("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

}
