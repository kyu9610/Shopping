package Shop.Shopping.web.dto;

import Shop.Shopping.domain.order.Order;
import Shop.Shopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class OrderController {
    private final OrderService orderService;
    // 특정 주문내역 수정처리
    @PostMapping("/order/update/{id}")
    public String orderUpdate(@PathVariable("id") Integer id, Order order){
        orderService.orderUpdate(id,order);

        return "redirect:/main";
    }

    // 특정 주문내역 환불처리
    @PostMapping("/order/refund/{id}")
    public String orderRefund(@PathVariable("id") Integer id, Order order){
        orderService.orderUpdate(id,order);
        orderService.orderRefund(id); // 환불처리

        return "redirect:/main";
    }

    // 특정 주문내역 환불처리취소
    @PostMapping("/order/refundCancle/{id}")
    public String orderRefundCancle(@PathVariable("id") Integer id, Order order){
        order.setStatus("환불 취소");
        orderService.orderUpdate(id,order); // 환불처리

        return "redirect:/main";
    }
}
