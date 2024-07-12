package com.example.shop_sv.modules.users.security.jwt;

import com.example.shop_sv.modules.users.model.entity.Role;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Slf4j
@Component
public class JwtProvider {
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${jwt.expired}")



    private long EXPIRED;
    // tạo token
    public String generateToken(String  username, Set<Role> roles, Integer id) {
        Date today = new Date();
        String role = roles.stream()
                .findFirst()
                .map(Role::getRoleName)
                .map(Enum::name)
                .orElse("DEFAULT_ROLE"); // Provide a default role if not found

        return Jwts.builder()
                .setSubject(username)
                .claim("id", id)
                .claim("role", role)
                .setIssuedAt(today)
                .setExpiration(new Date(today.getTime() + EXPIRED))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // xác thực token

    // giải mã token
    // validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("JWT: message error expired:", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT: message error unsupported:", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("JWT: message error not formated:", e.getMessage());
        } catch (SignatureException e) {
            log.error("JWT: message error signature not math:", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT: message claims empty or argument invalid: ", e.getMessage());
        }
        return false;
    }

    // giải mã lây ra username
    public String getUserNameAndIdAndRolesFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getSubject() + " " + claims.get("id") + " " + claims.get("role");
    }

}
