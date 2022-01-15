package Shop.Shopping.web.dto;

import Shop.Shopping.domain.item.Item;
import Shop.Shopping.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ShopController {
    private final ItemService itemService;

    @GetMapping({"/","/main"})
    public String home(Model model){
        List<Item> itemList = itemService.itemList();
        model.addAttribute("itemlist",itemList);
        return "main";
    }


}
