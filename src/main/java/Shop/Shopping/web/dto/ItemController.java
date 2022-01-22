package Shop.Shopping.web.dto;

import Shop.Shopping.config.auth.PrincipalDetails;
import Shop.Shopping.domain.item.Item;
import Shop.Shopping.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // 상품등록 페이지
    @GetMapping("/item/write")
    public String itemWriteForm(){
        return "/seller/itemwrite";
    }

    // 상품등록 처리
    @PostMapping("/item/writting")
    public String itemWritting(Item item, Model model, MultipartFile file,@AuthenticationPrincipal PrincipalDetails principalDetails)throws Exception{
        if(principalDetails.getUser().getRole().equals("ROLE_ADMIN") || principalDetails.getUser().getRole().equals("ROLE_SELLER")){
            item.setUser(principalDetails.getUser());
            itemService.save(item, file);
            return "redirect:/main";
        }else{
            return "redirect:/main";
        }
    }

    // 특정 상품정보 페이지 ( 비로그인 / 로그인구분 )
    @GetMapping("/item/view/{id}")
    public String itemView(@PathVariable Long id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails == null) {
            model.addAttribute("item", itemService.itemView(id));
            return "/none/item_none";
        } else if(principalDetails.getUser().getRole().equals("ROLE_ADMIN") || principalDetails.getUser().getRole().equals("ROLE_SELLER")) {
            model.addAttribute("user", principalDetails.getUser());
            model.addAttribute("item", itemService.itemView(id));
            return "/seller/itemview";
        }else{
            model.addAttribute("user", principalDetails.getUser());
            model.addAttribute("item", itemService.itemView(id));
            return "/user/itemview_user";
        }
    }

    // 특정 상품정보 수정
    @GetMapping("/item/modify/{id}")
    public String itemModify(@PathVariable("id") Long id, Model model){
        model.addAttribute("item",itemService.itemView(id));

        return "/seller/itemmodify";
    }

    // 특정 상품정보 수정처리
    @PostMapping("/item/update/{id}")
    public String itemUpdate(@PathVariable("id") Long id, Item item, MultipartFile file) throws Exception{
        itemService.itemModify(item,id,file);

        return "redirect:/main";
    }

    // 특정 상품 삭제
    @GetMapping("/item/delete")
    public String itemDelete(Long id){
        itemService.itemDelete(id);

        return "redirect:/main";
    }

}
