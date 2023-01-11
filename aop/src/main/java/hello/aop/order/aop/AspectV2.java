package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    /**
     * 주문과 관련된 모든 기능을 대상으로 하는 포인트 컷
     */
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){}      // pointCut Signature(메서드 이름 + 파라미터)

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {  // Advice
        log.info("[log] {}", joinPoint.getSignature());   // 메서드의 정보
        return joinPoint.proceed();
    }
}
