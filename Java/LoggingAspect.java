// Day 11: Measuring API performance and method execution latency using Spring AOP
// Topic: Aspect-Oriented Programming (AOP) & Production Monitoring

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceTrackerAspect {

    // Intercepts all method executions within the service layer automatically
    @Around("execution(* com.example.service.*.*(..))")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        // Executes the actual core business logic method
        Object obj = joinPoint.proceed(); 
        
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        
        System.out.println("Performance Metrics -> Method [" + joinPoint.getSignature().getName() + "] took " + executionTime + "ms to execute.");
        return obj;
    }
}

// Benefit: Eliminates code duplication. Instead of writing timing logic manually inside 
// hundreds of service methods, this single class monitors the entire backend application globally.
