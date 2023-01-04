package hello.proxy.app.v2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * V2 : 인터페이스 없는 구체 클래스 + 수동 빈 등록
 *
 * @Controller : 내부에 @Component 으로 인해 자동 빈 등록
 * @RequestMapping : 컴포넌트 스캔을 통한 자동이 아니라 수동으로 빈 등록하기 위해 사용
 * @Controller/@RequestMapping 이 있어야 컨트롤러로 인식해 HTTP URL이 매핑되고 동작
 */
@Slf4j
@RequestMapping
@ResponseBody
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;

    public OrderControllerV2(OrderServiceV2 orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/v2/request")
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }

    @GetMapping("/v2/no-log")
    public String noLog() {
        return "ok";
    }
}
