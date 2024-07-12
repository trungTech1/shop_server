package com.example.shop_sv.modules.users.service;

import com.example.shop_sv.modules.users.model.dto.request.FormRegister;
import com.example.shop_sv.modules.users.model.dto.responne.UserRespone;
import com.example.shop_sv.modules.users.model.entity.Role;
import com.example.shop_sv.modules.users.model.entity.RoleName;
import com.example.shop_sv.modules.users.model.entity.User;
import com.example.shop_sv.modules.users.repository.RoleRepository;
import com.example.shop_sv.modules.users.repository.UserRepository;
import com.example.shop_sv.modules.users.security.jwt.JwtProvider;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.shop_sv.modules.address.AddressModel;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleService;

    @Autowired
    private JwtProvider jwtProvider;


    public void save(FormRegister user){

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPhone(user.getPhone());
        newUser.setAddress(null);
        Set<Role> roles = new HashSet<>();
        // Check if the ROLE_USER exists, if not create it
        Role userRole = roleService.findByRoleName(RoleName.ROLE_USER);
        if (userRole == null) {
            userRole = new Role();
            userRole.setRoleName(RoleName.ROLE_USER);
            roleService.save(userRole); // Assuming there's a save method in roleService
        }
        roles.add(userRole);
        newUser.setRoles(roles);
        newUser.setAvatarUrl(user.getAvatarUrl());
        newUser.setCreatedAt(new Date().toString());
        newUser.setUpdatedAt("");
        userRepository.save(newUser);
    }

    public UserRespone getUserByName(String username){
        User user = userRepository.findByUsername(username).orElseThrow();
        return UserRespone.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }

}
