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

    @GetMapping("/item/write")
    public String itemWriteForm(){
        return "itemwrite";
    }

    @PostMapping("/item/writting")
    public String itemWritting(Item item, Model model){
        itemService.save(item);
        return "redirect:/main";
    }

}
