package ruanjian.order_dishes_app.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ruanjian.order_dishes_app.dto.*;
import ruanjian.order_dishes_app.entity.User;
import ruanjian.order_dishes_app.service.UserService;
import ruanjian.order_dishes_app.util.JwtUtil;
import ruanjian.order_dishes_app.util.PasswordUtil;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 登录接口
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return userService.loginByIdentifier(request.getIdentifier(), request.getPassword())
                .map(user -> {
                    String token = JwtUtil.generateToken(String.valueOf(user.getUserId()));
                    return ResponseEntity.ok().body(new LoginResponse(token, user));
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(
                    userService.registerByEmailOrPhone(
                            request.getPhoneOrEmail(),
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage()); // 返回错误信息
        }
    }

    // 查询用户信息接口
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        String userId = (String) authentication.getPrincipal();  // 从认证信息中获取 userId
        User user = userService.findById(Integer.parseInt(userId));

        if (user != null) {
            System.out.println("验证成功，返回信息");
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户不存在");
        }
    }


    // 修改用户信息接口
    @PutMapping("/profile/update")
    public ResponseEntity<?> updateUserProfile(
            @RequestHeader("Authorization") String authorization,
            @RequestBody UserUpdateRequest updateRequest) {
        try {
            // 从 Authorization header 中提取 Token
            String token = authorization.substring(7); // "Bearer " is 7 characters long
            String userId = JwtUtil.getSubject(token);  // 获取用户的 ID

            User user = userService.findById(Integer.parseInt(userId));
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户不存在");
            }

            // 更新用户信息
            if (updateRequest.getUsername() != null) {
                user.setUsername(updateRequest.getUsername());
            }
            if (updateRequest.getGender() != null) {
                user.setGender(updateRequest.getGender());
            }
            if (updateRequest.getBirthDate() != null) {
                user.setBirthDate(updateRequest.getBirthDate());
            }
            if (updateRequest.getAvatarUrl() != null) {
                user.setAvatarUrl(updateRequest.getAvatarUrl());
            }
            if (updateRequest.getEmail() != null) {
                user.setEmail(updateRequest.getEmail());
            }

            // 保存更新后的用户信息
            User updatedUser = userService.save(user);

            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新用户信息时发生错误");
        }
    }


    // 修改密码接口
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestHeader("Authorization") String authorization, @RequestBody UpdatePasswordRequest passwordRequest) {
        try {
            // 从 Authorization header 中提取 Token
            String token = authorization.substring(7); // "Bearer " is 7 characters long
            String userId = JwtUtil.getSubject(token);  // 获取用户的 ID

            User user = userService.findById(Integer.parseInt(userId));
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户不存在");
            }

            // 校验旧密码
            if (!userService.checkPassword(passwordRequest.getOldPassword(), user.getPasswordHash())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("旧密码不正确");
            }

            // 设置新密码
            user.setPasswordHash(PasswordUtil.encodePassword(passwordRequest.getNewPassword()));

            // 保存更新后的用户信息
            User updatedUser = userService.save(user);

            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新密码时发生错误");
        }
    }
}
