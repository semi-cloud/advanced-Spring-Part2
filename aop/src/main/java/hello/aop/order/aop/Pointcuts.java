package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    // hello.aop.order 패키지의 하위 클래스에 적용
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder(){}

    // 클래스 이름 패턴이 *Service에 적용
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    // 두 가지 포인트컷을 조합
    @Pointcut("allOrder() && allService()")
    public void allOrderAndService(){}

}
