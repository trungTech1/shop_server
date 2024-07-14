package com.example.shop_sv.modules.users.model.dto.request;

import com.example.shop_sv.modules.users.model.entity.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateDTO {
    private Integer id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private RoleName role;

    private String permission;

    private String avatarUrl;

    private boolean isBlocked;

    private boolean isDeleted;

    private boolean isVerified;

    private String createdAt;

    private String updatedAt;
}
