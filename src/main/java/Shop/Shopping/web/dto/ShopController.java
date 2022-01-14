package Shop.Shopping.web.dto;

import Shop.Shopping.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController {
    //private final ShopService shopService;

    @GetMapping("/")
    public String home(){
        return "main";
    }
}
