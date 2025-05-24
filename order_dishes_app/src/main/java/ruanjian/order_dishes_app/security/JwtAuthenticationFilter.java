package ruanjian.order_dishes_app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ruanjian.order_dishes_app.util.JwtUtil;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String path = req.getRequestURI();

        // ✅ 跳过无需验证的路径
        if (path.startsWith("/api/users/login") ||
                path.startsWith("/api/users/register") ||
                path.startsWith("/api/dishes")) {
            chain.doFilter(req, res); // 直接放行
            return;
        }

        // ✅ 处理需要认证的请求
        String header = req.getHeader("Authorization");
        System.out.println("请求头 Authorization: " + header); // 打印 header

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (JwtUtil.validateToken(token)) {
                String userId = JwtUtil.getSubject(token);
                System.out.println("✅ Token 验证通过，userId = " + userId);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userId, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                System.out.println("❌ Token 验证失败");
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        } else {
            System.out.println("⚠️ 请求未携带 Authorization 或格式错误");
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        chain.doFilter(req, res); // 放行
    }
}
