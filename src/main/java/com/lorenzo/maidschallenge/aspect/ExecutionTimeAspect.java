package com.lorenzo.maidschallenge.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {
 

        @Around("@annotation(com.lorenzo.maidschallenge.annotation.LogExecutionTime)")
        public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
            final long start = System.currentTimeMillis();

            final Object proceed = joinPoint.proceed();

            final long executionTime = System.currentTimeMillis() - start;

            System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");

            return proceed;
        }

    
}
