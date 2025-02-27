package com.karan.authservice.audit;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // @Before("execution(* com.karan.authservice.service.AuthService.*(..))")
    // public void logMethodEntry(JoinPoint joinPoint){

    //     SourceLocation loc  = joinPoint.getSourceLocation();
        

    // }
    
}
