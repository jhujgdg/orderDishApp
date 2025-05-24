package ruanjian.order_dishes_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruanjian.order_dishes_app.entity.User;
import ruanjian.order_dishes_app.repository.UserRepository;
import ruanjian.order_dishes_app.service.UserService;
import ruanjian.order_dishes_app.util.PasswordUtil;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> loginByIdentifier(String identifier, String password) {
        Optional<User> userOptional;

        if (identifier.contains("@")) {
            userOptional = userRepository.findByEmail(identifier);
        } else {
            userOptional = userRepository.findByPhone(identifier);
        }

        return userOptional.filter(user -> passwordMatches(password, user.getPasswordHash()));
    }

    @Override
    @Transactional
    public User registerByEmailOrPhone(String phoneOrEmail, String username, String rawPassword) {
        // 校验手机号或邮箱是否已注册
        if (phoneOrEmail.contains("@")) {
            if (userRepository.findByEmail(phoneOrEmail).isPresent()) {
                throw new RuntimeException("邮箱已注册");
            }
        } else {
            if (userRepository.findByPhone(phoneOrEmail).isPresent()) {
                throw new RuntimeException("手机号已注册");
            }
        }

        // 创建用户
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(PasswordUtil.encodePassword(rawPassword));
        user.setRegistrationDate(LocalDateTime.now());
        user.setStatus((byte) 1);

        if (phoneOrEmail.contains("@")) {
            user.setEmail(phoneOrEmail);
        } else {
            user.setPhone(phoneOrEmail);
        }

        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    private boolean passwordMatches(String rawPassword, String hashedPassword) {
        return PasswordUtil.checkPassword(rawPassword, hashedPassword);
    }

    @Override
    public User findById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户未找到"));
    }


    @Override
    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return PasswordUtil.checkPassword(rawPassword, hashedPassword);
    }

}
