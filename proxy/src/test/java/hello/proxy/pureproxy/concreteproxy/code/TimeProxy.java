package hello.proxy.pureproxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy extends ConcreteLogic {

    private final ConcreteLogic target;

    public TimeProxy(ConcreteLogic target) {
        this.target = target;
    }

    @Override
    public String operation() {
        log.info("Time Decorator 실행");
        long startTime = System.currentTimeMillis();

        String result = target.operation();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("Time Decorator 종료 resultTime = {}ms", resultTime);
        return result;
    }
}
