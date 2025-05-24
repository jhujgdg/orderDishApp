package ruanjian.order_dishes_app.dto;

import ruanjian.order_dishes_app.entity.User;

public class LoginResponse {
    private String token;
    private User user; // 添加 user 字段

    public LoginResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
