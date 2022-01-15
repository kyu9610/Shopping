package Shop.Shopping.service;

import Shop.Shopping.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShopService {
    private final ItemRepository itemRepository;

}
