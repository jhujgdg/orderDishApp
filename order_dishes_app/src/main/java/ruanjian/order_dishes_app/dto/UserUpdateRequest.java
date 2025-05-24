package ruanjian.order_dishes_app.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateRequest {
    private String username;
    private String gender;
    private LocalDate birthDate;
    private String avatarUrl;
    private String email;


}
