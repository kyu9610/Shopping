package Shop.Shopping.domain.sale;

import Shop.Shopping.domain.order.Order;
import Shop.Shopping.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 판매 번호
    private int price; // 총 가격

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    private Order order;

    public Sale addSale(User user, Order order){
        Sale sale = new Sale();
        sale.setUser(user);
        sale.setOrder(order);

        return sale;
    }
}
