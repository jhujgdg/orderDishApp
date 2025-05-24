package ruanjian.order_dishes_app.util;

import io.jsonwebtoken.*;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET = "your_jwt_secret_key";
    private static final long EXPIRATION = 86400000; // 1天

    public static String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static String getSubject(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("❌ Token 验证失败: " + e.getMessage());
            return false;
        }
    }

}
