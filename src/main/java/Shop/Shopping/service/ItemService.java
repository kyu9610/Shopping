package Shop.Shopping.service;

import Shop.Shopping.domain.item.Item;
import Shop.Shopping.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    // 상품등록
    public void save(Item item){
        item.setCount(item.getStock());
        item.setSoldout(true);
        itemRepository.save(item);
    }

    // 전체 상품목록 조회
    public List<Item> itemList(){
        return itemRepository.findAll();
    }

    // 특정 상품 조회
    public Item itemView(Long id){
        return itemRepository.findById(id).get();
    }

    // 특정 상품 수정
    public void itemUpdate(Item item){

    }

    // 특정 상품 삭제
    public void itemDelete(Long id){
        itemRepository.deleteById(id);
    }
}
