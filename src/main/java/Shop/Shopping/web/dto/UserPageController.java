package Shop.Shopping.web.dto;

import Shop.Shopping.config.auth.PrincipalDetails;
import Shop.Shopping.domain.cart.Cart;
import Shop.Shopping.domain.cart_item.Cart_item;
import Shop.Shopping.domain.item.Item;
import Shop.Shopping.domain.user.User;
import Shop.Shopping.service.AuthService;
import Shop.Shopping.service.CartService;
import Shop.Shopping.service.ItemService;
import Shop.Shopping.service.UserPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserPageController {
    private final UserPageService userPageService;
    private final ItemService itemService;
    private final CartService cartService;
    private final AuthService authService;

    // 유저 상세페이지
    @GetMapping("/user/{id}")
    public String userPage(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        if(principalDetails.getUser().getId() == id){
            if(principalDetails.getUser().getRole().equals("ROLE_ADMIN") || principalDetails.getUser().getRole().equals("ROLE_SELLER")){
                model.addAttribute("user",userPageService.findUser(id));
                return "/seller/page_seller";
            }else{
                model.addAttribute("user",userPageService.findUser(id));
                return "/user/page_user";
            }
        }else{
            return "redirect:/main";
        }
    }

    // 유저 정보수정
    @GetMapping("/user/modify/{id}")
    public String userModify(@PathVariable("id") Integer id, Model model){
        model.addAttribute("user",userPageService.findUser(id));

        return "useredit";
    }

    // 유저 정보수정 처리
    @PostMapping("/user/update/{id}")
    public String userUpdate(@PathVariable("id") Integer id, User user, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User tempUser = userPageService.findUser(id);
        tempUser.setEmail(user.getEmail());
        tempUser.setAddress(user.getAddress());
        tempUser.setPhone_number(user.getPhone_number());
        tempUser.setAddr1(user.getAddr1());
        tempUser.setAddr2(user.getAddr2());
        tempUser.setAddr3(user.getAddr3());
        tempUser.setAddress(user.getAddr2()+user.getAddr3());

        authService.userUpdate(tempUser);

        return "redirect:/main";
    }

    // 내가 등록한 상품조회
    @GetMapping("/user/{id}/item")
    public String myItemPage(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User tempUser = userPageService.findUser(id);

        model.addAttribute("user",userPageService.findUser(id));
        model.addAttribute("itemList",itemService.userItemView(tempUser));
        return "/seller/myitem_seller";
    }

    // 내 장바구니 조회
    @GetMapping("/user/{id}/cart")
    public String myCartPage(@PathVariable("id") Integer id,Model model,@AuthenticationPrincipal PrincipalDetails principalDetails){
        // 로그인 User == 접속 User
        if(principalDetails.getUser().getId() == id){
            // User의 장바구니를 가져온다.
            Cart cart = principalDetails.getUser().getCart();
            // 장바구니의 아이템을 가져온다.
            List<Cart_item> cart_items = cartService.userCartView(cart);

            model.addAttribute("cart_itemList",cart_items);
            model.addAttribute("user",userPageService.findUser(id));

            return "/cart/cart";
        }else{
            return "redirect:/main";
        }
    }

    // 특정 상품을 장바구니에 추가
    @PostMapping("/user/{id}/cart/{itemId}")
    public String myCartAdd(@PathVariable("id") Integer id,@PathVariable("itemId") Long itemId,int count){
        User user = userPageService.findUser(id);
        Item item = itemService.itemView(itemId);

        cartService.addCart(user,item,count);

        return "redirect:/item/view/{itemId}";
    }
}
