package hello.proxy.pureproxy.concreteproxy.code;

public class ConcreteClient {

    private final ConcreteLogic concreteLogic;  // ConcreteLogic, TimeProxy(자식) 모두 할당 가능

    public ConcreteClient(ConcreteLogic concreteLogic) {
        this.concreteLogic = concreteLogic;
    }

    public void execute() {
        concreteLogic.operation();
    }
}
