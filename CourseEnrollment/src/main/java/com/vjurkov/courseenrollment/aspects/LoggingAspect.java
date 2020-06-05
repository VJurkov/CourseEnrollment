package com.vjurkov.courseenrollment.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;



@Aspect
@Configuration
public class LoggingAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* *(..)) &&\n" +
            "(\n" +
            "    within(com.vjurkov.courseenrollment.controller..*) ||\n" +
            "    within(com.vjurkov.courseenrollment.dao..*) ||\n" +
            "    within(com.vjurkov.courseenrollment.service..*)\n" +
            ")")
    public void beforeFunction(JoinPoint joinPoint){
        logger.info("I will now execute: "+joinPoint.getSignature().getName());

    }


}
