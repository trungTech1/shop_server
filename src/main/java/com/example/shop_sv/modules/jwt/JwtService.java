package com.example.shop_sv.modules.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.shop_sv.modules.users.model.dto.responne.UserRespone;
import com.example.shop_sv.modules.users.model.entity.RoleName;
import com.example.shop_sv.modules.users.model.entity.User;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Date;

@Service
public class JwtService {

    private static final String secretKey = "ntbphuoc";

    public static String createTokenUser(UserRespone data) throws IllegalAccessException {
        JWTCreator.Builder builder = JWT.create().withIssuer("auth0");

        long oneHourInMillis = 3600 * 1000 * 48;
        Date expirationTime = new Date(System.currentTimeMillis() + oneHourInMillis);
        builder.withExpiresAt(expirationTime);
        Field[] fields = UserRespone.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(data);
            if (value != null) {
                builder.withClaim(field.getName(), value.toString());
            }
        }

        return builder.sign(Algorithm.HMAC256(secretKey));
    }

    public static UserRespone verifyTokenUser(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            DecodedJWT jwt = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build()
                    .verify(token);

            UserRespone user = new UserRespone();

            Integer id = Integer.parseInt(jwt.getClaim("id").asString());
            user.setId(id);

            String userName = jwt.getClaim("userName").asString();
            user.setUsername(userName);

            String email = jwt.getClaim("email").asString();
            user.setEmail(email);

            String phone = jwt.getClaim("phone").asString();
            user.setPhone(phone);

            String role = jwt.getClaim("role").asString();

            RoleName roleEnum = RoleName.valueOf(role);
            user.setRole(roleEnum);

            String permission = jwt.getClaim("permission").asString();
            user.setPermission(permission);

            String avatarUrl = jwt.getClaim("avatarUrl").asString();
            user.setAvatarUrl(avatarUrl);

            Boolean status = Boolean.valueOf(jwt.getClaim("isBlock").asString());
            user.setIsBlock(status);

            Boolean isDeleted = Boolean.valueOf(jwt.getClaim("isDeleted").asString());
            user.setIsDeleted(isDeleted);

            Boolean isVerified = Boolean.valueOf(jwt.getClaim("isVerified").asString());
            user.setIsVerified(isVerified);

            String createAt = jwt.getClaim("createAt").asString();
            user.setCreatedAt(createAt);

            String updateAt = jwt.getClaim("updateAt").asString();
            user.setUpdatedAt(updateAt);

            return user;
        } catch (JWTVerificationException exception){
            //System.out.println("exception =>" + exception);
            return null;
        }
    }
}
