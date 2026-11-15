package com.project.auth_service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.project.auth_service.service..*.*(..))")
    public Object logDuration(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        long startTime = System.currentTimeMillis();
        log.info("[AOP] Masuk {} dengan argumen: {}", methodName, Arrays.toString(joinPoint.getArgs()));

        Object result = joinPoint.proceed();

        log.info("[AOP] Selesai {} dalam {} ms", methodName, System.currentTimeMillis() - startTime);
        return result;
    }

    @AfterThrowing(pointcut = "execution(* com.project.auth_service.service..*.*(..))", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        log.error("[AOP] Exception di {}: {}", joinPoint.getSignature().toShortString(), exception.getMessage());
    }
}