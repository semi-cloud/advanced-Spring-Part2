package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 프록시도 실제 객체와 모양이 같아야 하기 때문에 같은 인터페이스 구현
 * target : 실제 호출해야 하는 객체
 */
@Slf4j
public class CacheProxy implements Subject {

    private final Subject target;
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    /**
     * 캐싱의 접근 제어 : 값이 있다면 실제 객체를 호출하지 않음
     */
    @Override
    public String operation() {
        log.info("프록시 호출");
        if (cacheValue == null) {
            cacheValue = target.operation();
        }
        return cacheValue;
    }
}
