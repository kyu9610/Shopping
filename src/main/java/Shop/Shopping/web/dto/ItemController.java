package Shop.Shopping.web.dto;

import Shop.Shopping.domain.item.Item;
import Shop.Shopping.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // 상품등록 페이지
    @GetMapping("/item/write")
    public String itemWriteForm(){
        return "itemwrite";
    }

    // 상품등록 처리
    @PostMapping("/item/writting")
    public String itemWritting(Item item, Model model){
        itemService.save(item);
        return "redirect:/main";
    }

    // 특정 상품정보 페이지
    @GetMapping("/item/view")
    public String itemView(Long id,Model model){
        model.addAttribute("item",itemService.itemView(id));

        return "itemview";
    }

}
