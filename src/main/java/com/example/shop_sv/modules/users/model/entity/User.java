package com.example.shop_sv.modules.users.model.entity;

import com.example.shop_sv.modules.address.AddressModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String avatarUrl;
    private String permission;
    private Boolean isBloked = false;
    private Boolean isDeleted = false;
    private Boolean isVerified = false;
    @OneToMany()
    @JoinColumn(name = "userId")
    @JsonManagedReference
    private List<AddressModel> address;

    private String createdAt;
    private String updatedAt;

    @Enumerated(EnumType.STRING)
    private RoleName role = RoleName.ROLE_USER;


}
