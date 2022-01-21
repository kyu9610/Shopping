package Shop.Shopping.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findItemById(Long id);
    //List<Item> findItemByUserId(int id);
}
