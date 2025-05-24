package ruanjian.order_dishes_app.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // 加密密码
    public static String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    // 验证密码
    public static boolean checkPassword(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}
