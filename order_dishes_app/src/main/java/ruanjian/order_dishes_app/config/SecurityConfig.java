package ruanjian.order_dishes_app.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ruanjian.order_dishes_app.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/login", "/api/dishes/**","/api/users/register").permitAll() // 允许访问登录、菜品推荐和搜索接口
                        .anyRequest().authenticated() // 其他请求需要认证
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // 确保 JWT 过滤器只作用于需要验证的接口

                // 配置未认证访问时的错误处理
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"); // 返回 401 错误
                        })
                );

        return http.build();
    }
}
