package Shop.Shopping.web.dto;

import Shop.Shopping.config.auth.PrincipalDetails;
import Shop.Shopping.domain.item.Item;
import Shop.Shopping.domain.user.User;
import Shop.Shopping.service.ItemService;
import Shop.Shopping.service.UserPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ShopController {
    private final UserPageService userPageService;
    private final ItemService itemService;

    // 메인페이지 ( 비로그인 유저 )
    @GetMapping("/")
    public String home(Model model){
        List<Item> itemList = itemService.itemList();
        model.addAttribute("itemlist",itemList);
        return "/none/main";
    }

    // 메인페이지 ( 로그인 유저 )
    @GetMapping("/main")
    public String main(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        List<Item> itemList = itemService.itemList();
        User user = userPageService.findUser(principalDetails.getUser().getId());
        model.addAttribute("itemlist",itemList);
        model.addAttribute("user",user);
        return "/user/main";
    }


}
