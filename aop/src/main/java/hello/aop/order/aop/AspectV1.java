package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect  // Advisor
public class AspectV1 {

    @Around("execution(* hello.aop.order..*(..))")   // PointCut
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {  // Advice
        log.info("[log] {}", joinPoint.getSignature());   // 메서드의 정보
        return joinPoint.proceed();
    }
}
