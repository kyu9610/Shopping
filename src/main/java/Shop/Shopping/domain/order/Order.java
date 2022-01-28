package Shop.Shopping.domain.order;

import Shop.Shopping.domain.cart.Cart;
import Shop.Shopping.domain.order_item.Order_item;
import Shop.Shopping.domain.user.User;
import lombok.*;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // order_id

    private String status; // 상황

    private int price; // 총 금액

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Order_item> order_items = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate; // 날짜

    public void addOrderItem(Order_item order_item){
        order_items.add(order_item);
        order_item.setOrder(this);
    }

    public static Order createOrder(User user, List<Order_item> orderItemList){
        Order order = new Order();
        order.setUser(user);
        for(Order_item order_item : orderItemList){
            order.addOrderItem(order_item);
        }
        order.setStatus("주문 완료");
        order.setCreateDate(LocalDate.now());
        return order;
    }

    public int getTotalPrice(){
        int totalPrice = 0;

        for(Order_item order_item : order_items){
            totalPrice += (order_item.getPrice() * order_item.getCount());
        }

        return totalPrice;
    }
}
