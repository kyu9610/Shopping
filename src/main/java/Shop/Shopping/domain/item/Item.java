package Shop.Shopping.domain.item;

import Shop.Shopping.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity

@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // 아이템 고유번호

    private String name; // 상품이름

    private int price; // 가격

    private int stock; // 재고

    private boolean isSoldout; // 판매여부

    private int count; // 팔린 갯수

    private String text; // 상품설명

    private String filename; // 상품 사진 파일이름

    private String filepath; // 상품 사진 파일경로

    // 판매자랑 연결
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    //@OneToMany(mappedBy = "item")
    //private List<Cart_item> cart_items = new ArrayList<>();
}
