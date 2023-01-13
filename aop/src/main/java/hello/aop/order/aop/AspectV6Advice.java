package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * 동일한 Aspect 안에서의 조인 포인트 우선순위
 * 적용 순서: @Around -> @Before -> @After -> @AfterReturning -> @AfterThrowing
 * 호출 순서: @Around -> @Before -> @AfterThrowing -> @AfterRunning -> @After -> @Around
 */
@Slf4j
@Aspect
public class AspectV6Advice {

    // 조인 포인트 실행 로직 반드시 호출 필수
    // 조인 포인트 실행 여부 선택, 전달 값 변환, 반환 값 변환, 예외 변환 등 모두 가능
    @Around("hello.aop.order.aop.Pointcuts.allOrderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // @Before
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();

            // @AfterReturning
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception ex) {
            // @AfterThrowing
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw ex;
        } finally {
            // @After
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    // 조인 포인트 실행 직전 까지의 로직 구현
    @Before("hello.aop.order.aop.Pointcuts.allOrderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    // 정상 반환 조건 처리
    // 주의 : 반환 타입이 다른 메서드는 아예 호출이 X
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.allOrderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    // 예외 반환 조건 처리
    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.allOrderAndService()", throwing = "ex")
    public void doReturn(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}", joinPoint.getSignature(), ex.getMessage());
    }

    // 정상 및 예외 반환 조건을 모두 처리
    @After("hello.aop.order.aop.Pointcuts.allOrderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }
}
