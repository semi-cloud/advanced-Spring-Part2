package hello.proxy.config.v4_postprocessor.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 특정 패키지 하위에 있는 프로세서에만 빈 후처리기 적용
 */
@Slf4j
public class PackageLogTracePostProcessor implements BeanPostProcessor {

    private final String basePackage;
    private final Advisor advisor;

    public PackageLogTracePostProcessor(String basePackage, Advisor advisor) {
        this.basePackage = basePackage;
        this.advisor = advisor;
    }

    // 빈 객체가 완전히 초기화 하고 난 후에 조작
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("beanName={} bean={}", beanName, bean.getClass());

        // 프록시 적용 대상 여부 체크
        String packageName = bean.getClass().getPackageName();
        // 프록시 대상 여부가 아닌 경우 : app 패키지 하위 클래스가 아니라면 원본 반환
        // 스프링 부트 뿐만 아니라 스프링 내부에서 쓰는 모든 빈들에 적용하면 안되므로 필터링 적용
        if (!packageName.startsWith(basePackage)) {
            return bean;
        }

        // 프록시 대상 여부이면 프록시 반환
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvisor(advisor);

        Object proxy = proxyFactory.getProxy();
        log.info("create proxy: target={} proxy={}", bean.getClass(), proxy.getClass());
        return proxy;
    }
}
