package Shop.Shopping.service;

import Shop.Shopping.domain.cart_item.Cart_item;
import Shop.Shopping.domain.order.Order;
import Shop.Shopping.domain.order.OrderRepository;
import Shop.Shopping.domain.order_item.Order_item;
import Shop.Shopping.domain.sale.SaleRepository;
import Shop.Shopping.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final SaleRepository saleRepository;

    public void createOrder(User user){
        Order order = new Order();
        order.setUser(user);
        orderRepository.save(order);
    }

    public void order(User user, List<Cart_item> cart_items){
        List<Order_item> order_items = new ArrayList<>(); // 주문내역에 추가할 아이템리스트

        for(Cart_item cart_item : cart_items){
            Order_item order_item = Order_item.createOrderItem(cart_item.getItem(),cart_item.getCount());
            order_items.add(order_item);
        }

        Order order = Order.createOrder(user,order_items);
        order.setPrice(order.getTotalPrice());
        orderRepository.save(order);
    }

    // 전체 주문 조회
    public List<Order> orderList(){
        return orderRepository.findAll();
    }

    // 특정 주문 조회
    public Order orderView(Integer id){
        return orderRepository.findById(id).get();
    }

    // 주문 수정
    public void orderUpdate(Integer id, Order order){
        Order tempOrder = orderRepository.findById(id).get();
        tempOrder.setStatus(order.getStatus());

        orderRepository.save(tempOrder);
    }

    // 주문 환불
    public void orderRefund(Integer id){
        Order tempOrder = orderRepository.findById(id).get();
        List<Order_item> itemList = tempOrder.getOrder_items();
        for(Order_item order_item : itemList){

        }
        orderRepository.save(tempOrder);
    }
}
