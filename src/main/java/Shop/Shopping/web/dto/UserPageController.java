package Shop.Shopping.web.dto;

import Shop.Shopping.config.auth.PrincipalDetails;
import Shop.Shopping.domain.cart.Cart;
import Shop.Shopping.domain.cart_item.Cart_item;
import Shop.Shopping.domain.item.Item;
import Shop.Shopping.domain.order.Order;
import Shop.Shopping.domain.order_item.Order_item;
import Shop.Shopping.domain.user.User;
import Shop.Shopping.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserPageController {
    private final UserPageService userPageService;
    private final ItemService itemService;
    private final CartService cartService;
    private final AuthService authService;
    private final OrderService orderService;

    // 유저 상세페이지
    @GetMapping("/user/{id}")
    public String userPage(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        if(principalDetails.getUser().getId() == id){
            model.addAttribute("user",userPageService.findUser(id));
            return "/user/mypage";
        }else{
            return "redirect:/main";
        }
    }

    // 유저 정보수정
    @GetMapping("/user/modify/{id}")
    public String userModify(@PathVariable("id") Integer id, Model model){
        model.addAttribute("user",userPageService.findUser(id));

        return "/user/useredit";
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
        tempUser.setAddress(user.getAddr2() + ' ' + user.getAddr3());

        authService.userUpdate(tempUser);

        return "redirect:/main";
    }

    // 내가 등록한 상품조회
    @GetMapping("/user/{id}/item")
    public String myItemPage(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User tempUser = userPageService.findUser(id);

        model.addAttribute("user",userPageService.findUser(id));
        model.addAttribute("itemList",itemService.userItemView(tempUser));
        return "/user/myitem";
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

            int totalPrice = 0;
            for(Cart_item cart_item : cart_items){
                totalPrice += ( cart_item.getItem().getPrice() * cart_item.getCount());
            }

            model.addAttribute("cart_itemList",cart_items);
            model.addAttribute("totalPrice",totalPrice);
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

    // 특정 상품을 장바구니에서 삭제
    @GetMapping("/user/{id}/cart/{cart_itemId}/delete")
    public String myCartDelete(@PathVariable("id") Integer id, @PathVariable("cart_itemId") int cart_itemId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = userPageService.findUser(id);
        user.getCart().setCount(user.getCart().getCount() - 1);
        cartService.cartItemDelete(cart_itemId);

        return "redirect:/user/{id}/cart";
    }

    // 결제 페이지
    @Transactional
    @PostMapping("/user/{id}/cart/checkout")
    public String myCartPayment(@PathVariable("id") Integer id, Model model){
        User user = userPageService.findUser(id);
        cartService.cartPayment(id); // 결제처리
        cartService.cartDelete(id); // 장바구니 비우기

        return "redirect:/user/{id}/order";
    }

    // 내 주문내역 조회
    @Transactional
    @GetMapping("/user/{id}/order")
    public String myOrderPage(@PathVariable("id") Integer id,Model model,@AuthenticationPrincipal PrincipalDetails principalDetails){
        // 로그인 User == 접속 User
        if(principalDetails.getUser().getId() == id){
            // User의 주문내역을 가져온다.
            User user = userPageService.findUser(id);
            List<Order> orderList = user.getOrders();

            model.addAttribute("orderList",orderList);
            model.addAttribute("user",user);

            return "/user/order";
        }else{
            return "redirect:/main";
        }
    }

    // 내 판매현황 조회
    @Transactional
    @GetMapping("/user/{id}/sale")
    public String mySalePage(@PathVariable("id")Integer id,Model model,@AuthenticationPrincipal PrincipalDetails principalDetails){
        if(principalDetails.getUser().getId() == id){
            // 판매자 정보를 받아온다.
            User seller = userPageService.findUser(id);

            List<Order> orderList = orderService.orderList();
            List<Order> mySaleList = new ArrayList<>();

            for(Order order : orderList){
                List<Order_item> orderItemList = order.getOrder_items();

                for(Order_item order_item : orderItemList){
                    if(seller == order_item.getItem().getUser()){
                        mySaleList.add(order);
                        break;
                    }
                }
            }

            model.addAttribute("saleList",mySaleList);
            model.addAttribute("user",seller);

            return "user/sale";
        }else{
            return "redirect:/main";
        }
    }

    // 잔액충전
    @Transactional
    @GetMapping("/user/{id}/cash")
    public String myCash(@PathVariable("id") Integer id,Model model,@AuthenticationPrincipal PrincipalDetails principalDetails){
        // 로그인 User == 접속 User
        if(principalDetails.getUser().getId() == id){
            // User의 주문내역을 가져온다.
            User user = userPageService.findUser(id);
            model.addAttribute("user",user);

            return "/user/cash";
        }else{
            return "redirect:/main";
        }
    }

    // 잔액충전 처리
    @GetMapping("/user/charge/point")
    public String myCashPro(int amount,@AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = userPageService.findUser(principalDetails.getUser().getId());
        userPageService.chargePoint(user.getId(),amount);
        return "redirect:/main";
    }

    // 관리자 유전관리
    @GetMapping("/user/{id}/admin")
    public String adminPage(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User admin = userPageService.findUser(id);
        // User == Admin.Role 일 경우
        if(admin.getRole().equals("ROLE_ADMIN")){
            List<User> userList = userPageService.userList();
            model.addAttribute("user",admin);
            model.addAttribute("userList",userList);
            return "/user/adminpage";
        }else{
            return "redirect:/main";
        }
    }

    // 관리자유저 정보수정 처리
    @PostMapping("/user/change/{id}")
    public String userChange(@PathVariable("id") Integer id, User user){
        userPageService.userUpdate(id,user);

        return "redirect:/main";
    }
}
