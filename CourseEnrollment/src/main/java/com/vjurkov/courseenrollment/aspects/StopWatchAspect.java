package com.vjurkov.courseenrollment.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class StopWatchAspect {
    private long start;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* *(..)) &&\n" +
            "(\n" +
            "    within(com.vjurkov.courseenrollment.service..*)\n" +
            ")")
    public void beforeFunction(JoinPoint joinPoint){
        logger.warn("Starting benchmark on: " + joinPoint.getSignature().getName());
        start = System.nanoTime();
    }

    @After("execution(* *(..)) &&\n" +
            "(\n" +
            "    within(com.vjurkov.courseenrollment.service..*)\n" +
            ")")
    public void afterFunction(JoinPoint joinPoint){
        logger.warn("Ended benchmark on: " + joinPoint.getSignature().getName());
        long timeToExecute = System.nanoTime() - start;
        logger.warn("Time to execute: " + timeToExecute + "ns");
    }
}
