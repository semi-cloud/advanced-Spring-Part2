package hello.aop.pointcut;

import hello.aop.member.annotation.ClassAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 주의 : @target 단독이 아닌, 반드시 프록시 적용 대상을 축소하는 표현식(execution)과 함께 사용해야 함
 */

/**
 * 문제 상황: @SpringBootTest + @Configuration
 * => 의존성이 먹히지 않아서 AOP 자체가 동작하지 않는 문제 발생
 *
 * 원인
 * => 내부 클래스에 선언된 @Configuration 클래스 경로가 자동으로 컴포넌트 스캔의 기본패키지로 지정되기 때문에(우선권을 가짐),
 * 모든 스프링 부트의 다양한 설정들이 먹히지 않게 되는데 따라서 AutoProxy와 같은 스프링 부트가 자동으로 만들어주는 AOP 기본 클래스도 빈으로 등록 X
 *
 * 해결 방법
 * => 1. @EnableAspectJProxy + @Configuration : 기존`src/main` 모듈 설정 따로, AOP 설정 따로 해주어야 하므로 번거로움
 * => 2. @TestConfiguration :  `src/main`전체 모듈의 기본적인 설정 + 추가적인 테스트 Config 설정
 */
@Slf4j
@Import(AtTargetWithinTest.Config.class)
@SpringBootTest
public class AtTargetWithinTest {

    @Autowired
    Parent.Child child;

    @Test
    void success() {
        log.info("child Proxy={}", child.getClass());
        child.childMethod(); //부모, 자식 모두 있는 메서드
        child.parentMethod(); //부모 클래스만 있는 메서드
    }

    static class Config {
        @Bean
        public Parent parent() {
            return new Parent();
        }

        @Bean
        public Parent.Child child() {
            return new Parent.Child();
        }

        @Bean
        public Parent.AtTargetAtWithinAspect atTargetAtWithinAspect() {
            return new Parent.AtTargetAtWithinAspect();
        }
    }

    static class Parent {
        public void parentMethod() {
        } //부모에만 있는 메서드 }

        @ClassAop
        static class Child extends Parent {
            public void childMethod() {
            }
        }

        @Slf4j
        @Aspect
        static class AtTargetAtWithinAspect {
            //@target: 인스턴스 기준으로 모든 메서드의 조인 포인트를 선정, 부모 타입의 메서드도 적용
            @Around("execution(* hello.aop..*(..)) && @target(hello.aop.member.annotation.ClassAop)")
            public Object atTarget(ProceedingJoinPoint joinPoint) throws Throwable {
                log.info("[@target] {}", joinPoint.getSignature());
                return joinPoint.proceed();
            }

            //@within: 선택된 클래스 내부에 있는 메서드만 조인 포인트로 선정, 부모 타입의 메서드는 적용되지 않음
            @Around("execution(* hello.aop..*(..)) && @within(hello.aop.member.annotation.ClassAop)")
            public Object atWithin(ProceedingJoinPoint joinPoint) throws Throwable {
                log.info("[@within] {}", joinPoint.getSignature());
                return joinPoint.proceed();
            }
        }
    }
}
