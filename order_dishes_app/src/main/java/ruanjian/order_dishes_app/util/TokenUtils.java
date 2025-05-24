package ruanjian.order_dishes_app.util;

import ruanjian.order_dishes_app.entity.User;
import ruanjian.order_dishes_app.service.UserService;

public class TokenUtils {

    public static Integer extractUserId(String authorization, UserService userService) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new RuntimeException("缺少或非法的 Authorization Header");
        }

        String token = authorization.substring(7);
        String userId = JwtUtil.getSubject(token);

        User user = userService.findById(Integer.parseInt(userId));
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        return Integer.parseInt(userId);
    }
}
