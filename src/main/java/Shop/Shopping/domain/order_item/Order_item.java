package Shop.Shopping.domain.order_item;

import Shop.Shopping.domain.item.Item;
import Shop.Shopping.domain.order.Order;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Order_item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Order_item_id

    private int count; // 개수

    private int price; // 금액

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order; // 주문 연결

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item; // 아이템 연결

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    LocalDate createDate; // 날짜

    public static Order_item createOrderItem(Item item, int count){

        Order_item order_item = new Order_item();
        order_item.setItem(item);
        order_item.setCount(count);
        order_item.setPrice(item.getPrice());

        return order_item;
    }
}
