package hello.proxy.pureproxy.proxy.code;

/**
 * 인터페이스에만 의존하고 있기 때문에 어떤 종류가 들어와도 상관 X : OCP 원칙
 */
public class ProxyPatternClient {

    private final Subject subject;

    public ProxyPatternClient(Subject subject) {
        this.subject = subject;
    }

    public void execute() {
        subject.operation();
    }
}
