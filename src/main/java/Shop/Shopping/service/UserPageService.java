package Shop.Shopping.service;

import Shop.Shopping.domain.user.User;
import Shop.Shopping.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserPageService {
    private final UserRepository userRepository;

    public User findUser(Integer id){
        return userRepository.findById(id).get();
    }

    public void chargePoint(int id,int amount){
        User user = userRepository.findById(id).get();
        user.setMoney(amount);

        userRepository.save(user);
    }
}
