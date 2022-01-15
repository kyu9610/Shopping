package Shop.Shopping.domain.item;

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

}
