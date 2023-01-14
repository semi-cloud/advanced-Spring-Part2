package hello.aop.pointcut;

import hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * 적용 타입을 반드시 하나만 지정 가능, 부모 타입 허용
 * this : 스프링 빈 객체(스프링 AOP 프록시)를 대상으로 하는 조인 포인트
 * target : Target 객체(스프링 AOP 프록시가 가리키는 실제 대상)를 대상으로 하는 조인 포인트
 */
@Slf4j
@Import(ThisTargetTest.ThisTargetAspect.class)
@SpringBootTest(properties = "spring.aop.proxy-target-class=true")  // JDK 동적 프록시 적용(기본 CGLIB)
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Aspect
    @Slf4j
    static class ThisTargetAspect {

        /**
         * 인터페이스 대상
         * this(인터페이스) : Proxy(this) + 타겟 클래스(target) 모두 AOP 적용
         * 부모 타입을 허용하므로 JDK 동적 프록시 / CGLIB 모두 적용
         * */
        @Around("this(hello.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }


        @Around("this(hello.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }


        /**
         * 구체 클래스 대상
         * 1. JDK 동적 프록시
         * this(구체 클래스) : 타겟 클래스(target)에만 AOP 적용(부모 타입인 인터페이스만 아는 상태)
         * 2. CGLIB
         * this(구체 클래스) : Proxy(this) + 타겟 클래스(target)에 AOP 적용
         */
        @Around("this(hello.aop.member.MemberServiceImpl)")
        public Object doThis(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-implement] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(hello.aop.member.MemberServiceImpl)")
        public Object doTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-implement] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
