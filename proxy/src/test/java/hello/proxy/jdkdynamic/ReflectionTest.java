package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello target = new Hello();

        // 공통 로직 1 시작
        log.info("start");
        String resultA = target.callA();  // 호출하는 대상이 다름, 동적 처리 필요
        log.info("result={}", resultA);
        // 공통 로직 1 종료

        // 공통 로직 2 시작
        log.info("start");
        String resultB = target.callB();
        log.info("result={}", resultB);
        // 공통 로직 2 종료
    }

    @Test
    void reflection1() throws Exception {
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        // callA의 메서드 정보
        Method methodA = classHello.getMethod("callA");
        Object result1 = methodA.invoke(target);
        log.info("result1={}", result1);

        // callB의 메서드 정보
        Method methodB = classHello.getMethod("callB");
        Object result2 = methodB.invoke(target);
        log.info("result2={}", result2);
    }

    @Test
    void reflection2() throws Exception {
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        dynamicCall(classHello.getMethod("callA"), target);
        dynamicCall(classHello.getMethod("callB"), target);
    }

    private void dynamicCall(Method method, Object target) throws Exception {  // 메서드로 공통화
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}", result);
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}
