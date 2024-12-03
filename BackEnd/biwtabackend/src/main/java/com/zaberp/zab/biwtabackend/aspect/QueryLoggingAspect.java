package com.zaberp.zab.biwtabackend.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class QueryLoggingAspect {

    @Before("execution(* com.zaberp.zab.biwtabackend.repository.ImtorheaderRepository.*(..))")
    public void logQuery(JoinPoint joinPoint) {
        // Log the method name and parameters
        System.out.println("Executing method: " + joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            System.out.println("Parameter: " + arg);
        }
    }
}
