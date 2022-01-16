package Shop.Shopping.web.dto;

import Shop.Shopping.domain.item.Item;
import Shop.Shopping.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // 특정 상품정보 수정
    @GetMapping("/item/modify/{id}")
    public String itemModify(@PathVariable("id") Long id, Model model){
        model.addAttribute("item",itemService.itemView(id));

        return "itemmodify";
    }

    // 특정 상품정보 수정처리
    @PostMapping("/item/update/{id}")
    public String itemUpdate(@PathVariable("id") Long id, Item item){
        Item tempItem = itemService.itemView(id);
        tempItem.setName(item.getName());
        tempItem.setPrice(item.getPrice());
        tempItem.setStock(item.getStock());
        tempItem.setSoldout(item.isSoldout());
        tempItem.setCount(item.getCount());
        tempItem.setText(item.getText());

        itemService.save(tempItem);

        return "redirect:/main";
    }

    // 특정 상품 삭제
    @GetMapping("/item/delete")
    public String itemDelete(Long id){
        itemService.itemDelete(id);

        return "redirect:/main";
    }

}
