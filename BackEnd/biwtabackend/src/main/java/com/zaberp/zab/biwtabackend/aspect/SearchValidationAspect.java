package com.zaberp.zab.biwtabackend.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Aspect
@Component
public class SearchValidationAspect {

    @Around("execution(* com.zaberp.zab.biwtabackend.controller.*.*(..)) && args(zid, searchText, ..)")
    public Object validateSearchText(ProceedingJoinPoint joinPoint, int zid, String searchText) throws Throwable {
        if (searchText == null || searchText.trim().length() < 3) {
            return Collections.emptyList();
        }
        return joinPoint.proceed();
    }
}

