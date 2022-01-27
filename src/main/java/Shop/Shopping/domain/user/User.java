package Shop.Shopping.domain.user;

import Shop.Shopping.domain.cart.Cart;
import Shop.Shopping.domain.item.Item;
import Shop.Shopping.domain.order.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true) // username 중목 안됨
    private String username;
    private String password;
    private String name;
    private String email;
    private int money;

    //주소
    private String addr1;
    private String addr2;
    private String addr3;
    private String address; // 주소

    private String phone_number; // 핸드폰번호
    private String grade; // 등급

    private String role; // 권한

    private LocalDateTime createDate; // 날짜

    // Item 과 연결
    @OneToMany(mappedBy = "user")
    private List<Item> items = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    public void setMoney(int count){
        this.money = this.money + count;
    }

}