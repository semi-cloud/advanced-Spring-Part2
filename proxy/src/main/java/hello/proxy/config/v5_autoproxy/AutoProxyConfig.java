package hello.proxy.config.v5_autoproxy;

import hello.proxy.config.AppConfigV1;
import hello.proxy.config.AppConfigV2;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *  BeanPostProcessor는 자동으로 스프링 빈으로 등록
 */
@Configuration
@Import({AppConfigV1.class, AppConfigV2.class})
public class AutoProxyConfig {

//    @Bean
    public Advisor advisor1(LogTrace logTrace) {
        // pointcut : 필터링
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        // advice : 부가 기능 로직
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    /**
     * AspectJExpressionPointcut 를 통해 프록시를 적용할 패키지 + 메서드명 조합 가능
     */
    @Bean
    public Advisor advisor2(LogTrace logTrace) {
        // pointcut : 필터링
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))");    // 적용할 패키지 위치 지정

        // advice : 부가 기능 로직
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
