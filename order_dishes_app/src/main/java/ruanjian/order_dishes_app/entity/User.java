package ruanjian.order_dishes_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String username;

    private String passwordHash;

    private String phone;

    private String email;

    private LocalDateTime registrationDate;

    private LocalDateTime lastLogin;

    private Byte status;

    private String gender; // 性别（例如 "男"、"女"、"其他"）

    private LocalDate birthDate; // 出生日期

    private String avatarUrl; // 头像URL
}
