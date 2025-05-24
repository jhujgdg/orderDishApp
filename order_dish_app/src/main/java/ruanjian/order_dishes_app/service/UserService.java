package ruanjian.order_dishes_app.service;
import ruanjian.order_dishes_app.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> loginByIdentifier(String identifier, String password);
    User registerByEmailOrPhone(String phoneOrEmail, String username, String rawPassword);
    User save(User user);
    User findById(Integer userId);
    boolean checkPassword(String rawPassword, String hashedPassword);

}
