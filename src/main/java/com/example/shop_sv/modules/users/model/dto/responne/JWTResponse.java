package com.example.shop_sv.modules.users.model.dto.responne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.shop_sv.modules.users.model.entity.Role;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JWTResponse {
    private Integer id;
    private String fullName;
    private Set<Role> roles;
    private final String type = "Bearer Token";
    private String accessToken;
}
