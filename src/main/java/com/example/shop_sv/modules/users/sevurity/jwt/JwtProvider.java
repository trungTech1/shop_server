package com.example.shop_sv.modules.users.sevurity.jwt;

import com.example.shop_sv.modules.users.model.entity.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${jwt.expired}")



    private long EXPIRED;
    // tạo token
    public String generateToken(User user) {
        Date today = new Date();
        return Jwts.builder()
                .setSubject(user.getUsername()) // mã hóa username
                .claim("id", user.getId())
                .claim("roles", user.getRoles().stream().map(role -> role.getRoleName().name()).collect(Collectors.toList()))
                .claim("fullName", user.getFullName())
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
    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
