package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {

    /**
     * 인터페이스 없는 구체 클래스의 동적 프록시 생성
     * 구체 클래스를 상속 받아서 프록시 생성
     */
    @Test
    void cglib() {
        ConcreteService target = new ConcreteService();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ConcreteService.class);       // 어떤 구체 클래스 상성 받을시 설정
        enhancer.setCallback(new TimeMethodInterceptor(target));  // 프록시에 실행할 로직 할당
        ConcreteService proxy = (ConcreteService) enhancer.create();  // 동적으로 상속 받아서 프록시 생성
        log.info("target class={}", target.getClass());
        log.info("target class={}", target.getClass());

        proxy.call();
    }
}
