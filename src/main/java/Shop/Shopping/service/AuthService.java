package Shop.Shopping.service;

import Shop.Shopping.domain.user.User;
import Shop.Shopping.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final CartService cartService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional // Write(Insert, Update, Delete)
    public User signup(User user) {
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("ROLE_USER"); // 기본 user 권한

        User userEntity = userRepository.save(user);
        cartService.createCart(user);
        return userEntity;
    }

    @Transactional // Write(Insert, Update, Delete)
    public User userUpdate(User user) {
        User userEntity = userRepository.save(user);
        return userEntity;
    }
}
