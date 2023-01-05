package hello.proxy;

import hello.proxy.config.v1_proxy.ConcreteProxyConfig;
import hello.proxy.config.v2_dynamicproxy.DynamicProxyBasicConfig;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @Import(AppV1Config.class)
 * : 클래스를 직접 스프링 빈으로 등록
 *
 * @SpringBootApplication(scanBasePackages)
 * : @ComponentScan 의 기능과 같으며, 컴포넌트 스캔을 시작할 위치를 지정
 * : 값을 설정하면 해당 패키지와 그 하위 패키지를 컴포넌트 스캔, 값을 설정하지 않으면 전체 스캔
 */
//@Import({AppConfigV1.class, AppConfigV2.class})
//@Import(InterfaceProxyConfig.class)
//@Import(ConcreteProxyConfig.class)
@Import(DynamicProxyBasicConfig.class)
@SpringBootApplication(scanBasePackages = "hello.proxy.app")
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

	@Bean
	public LogTrace logTrace() {
		return new ThreadLocalLogTrace();
	}

}
