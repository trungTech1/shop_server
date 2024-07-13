package com.example.shop_sv.modules.users.service;

import com.example.shop_sv.modules.users.model.dto.responne.UserRespone;
import com.example.shop_sv.modules.users.model.entity.RoleName;
import com.example.shop_sv.modules.users.model.entity.User;
import com.example.shop_sv.modules.users.repository.UserRepository;
import com.example.shop_sv.modules.users.model.dto.request.FormRegister;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(FormRegister user){
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPhone(user.getPhone());
        newUser.setAddress(null);
        // Check if the ROLE_USER exists, if not create it
        newUser.setRole(RoleName.ROLE_USER);
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
                .isBlocked(user.getIsBlocked())
                .role(user.getRole())
                .permission(user.getPermission())
                .isDeleted(user.getIsDeleted())
                .avatarUrl(user.getAvatarUrl())
                .password(user.getPassword())
                .isVerified(user.getIsVerified())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }


    public List<User> findUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findUserById(int id){
        return userRepository.findById(id);
    }

    public User update(User data){
        return userRepository.save(data);
    }
}
