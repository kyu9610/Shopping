package Shop.Shopping.web.dto;

import Shop.Shopping.config.auth.PrincipalDetails;
import Shop.Shopping.service.ItemService;
import Shop.Shopping.service.UserPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserPageController {
    private final UserPageService userPageService;
    private final ItemService itemService;

    // 유저페이지
    @GetMapping("/user/{id}")
    public String userPage(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        if(principalDetails.getUser().getId() == id){
            model.addAttribute("user",userPageService.findUser(id));
            return "/user/page_user";
        }else{
            return "redirect:/main";
        }
    }
}
