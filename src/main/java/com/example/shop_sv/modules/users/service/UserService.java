package com.example.shop_sv.modules.users.service;

import com.example.shop_sv.modules.users.model.dto.request.FormRegister;
import com.example.shop_sv.modules.users.model.entity.Role;
import com.example.shop_sv.modules.users.model.entity.RoleName;
import com.example.shop_sv.modules.users.model.entity.User;
import com.example.shop_sv.modules.users.repository.RoleRepository;
import com.example.shop_sv.modules.users.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleService;


    public User save(FormRegister user){

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPhone(user.getPhone());
        newUser.setAddress(null);
        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findByRoleName(RoleName.ROLE_USER);
        if (userRole != null) {
            roles.add(userRole);
        }
        newUser.setAvatarUrl(user.getAvatarUrl());
        newUser.setCreatedAt(new Date().toString());
        newUser.setUpdatedAt("");
        return userRepository.save(newUser);
    }

}
