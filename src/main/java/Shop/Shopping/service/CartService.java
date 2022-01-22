package Shop.Shopping.service;

import Shop.Shopping.domain.cart.Cart;
import Shop.Shopping.domain.cart.CartRepository;
import Shop.Shopping.domain.cart_item.Cart_item;
import Shop.Shopping.domain.cart_item.Cart_itemRepository;
import Shop.Shopping.domain.item.Item;
import Shop.Shopping.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final Cart_itemRepository cart_itemRepository;

    public void addCart(User user, Item item, int count){

        Cart cart = cartRepository.findByUserId(user.getId());

        // cart 가 비어있다면 생성.
        if(cart == null){
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }

        // cart_item 생성
        Cart_item cart_item = cart_itemRepository.findByCartIdAndItemId(cart.getId(),item.getId());

        // cart_item이 비어있다면 새로 생성.
        if(cart_item == null){
            cart_item = Cart_item.createCartItem(cart,item,count);
            cart_itemRepository.save(cart_item);
        }else{
            // 비어있지 않다면 그만큼 갯수를 추가.
            cart_item.addCount(count);
        }

    }

    public List<Cart_item> userCartView(Cart cart){
        List<Cart_item> cart_items = cart_itemRepository.findAll();
        List<Cart_item> user_items = new ArrayList<>();

        for(Cart_item cart_item : cart_items){
            if(cart_item.getCart().getId() == cart.getId()){
                user_items.add(cart_item);
            }
        }

        return user_items;
    }
}
