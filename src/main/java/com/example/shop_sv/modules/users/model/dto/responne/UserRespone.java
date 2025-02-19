package com.example.shop_sv.modules.users.model.dto.responne;

import com.example.shop_sv.modules.address.AddressModel;
import com.example.shop_sv.modules.users.model.entity.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRespone {
    private Integer id;
    private String username;
    private String fullName;
    private String password;
    private String email;
    private String phone;
    private RoleName role;
    private Boolean isBlocked;
    private Boolean isDeleted;
    private Boolean isVerified;
    private String avatarUrl;
    private String permission;
    private List<AddressModel> addressList;
    private String createdAt;
    private String updatedAt;
}
