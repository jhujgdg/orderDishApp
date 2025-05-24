package ruanjian.order_dishes_app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String identifier; // 可以是手机号或邮箱
    private String password;
}
