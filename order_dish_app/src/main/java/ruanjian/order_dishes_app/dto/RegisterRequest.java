package ruanjian.order_dishes_app.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String phoneOrEmail;
    private String username;
    private String password;
}