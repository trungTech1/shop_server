package com.example.shop_sv.modules.users.controller;
import com.example.shop_sv.modules.users.model.dto.request.UpdateDTO;
import com.example.shop_sv.modules.users.model.entity.User;
import com.example.shop_sv.modules.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseEntity<Object> getAllUsers() {
        try {
            List<User> users = userService.findUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestAttribute("data") User user, @RequestBody UpdateDTO updateData){
        try {
            if(!user.getPermission().contains("user.u")) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            Optional<User> userToUpdate = userService.findUserById(updateData.getId());
            if (!userToUpdate.isPresent()) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            // Update fields from UpdateDTO to UserModel
            if (updateData.getUsername() != null) userToUpdate.get().setUsername(updateData.getUsername());
            if (updateData.getEmail() != null) userToUpdate.get().setEmail(updateData.getEmail());
            if (updateData.getPhone() != null) userToUpdate.get().setPhone(updateData.getPhone());
            if (updateData.getRole() != null) userToUpdate.get().setRole(updateData.getRole());
            if (updateData.getPermission() != null) userToUpdate.get().setPermission(updateData.getPermission());
            if (updateData.getAvatarUrl() != null) userToUpdate.get().setAvatarUrl(updateData.getAvatarUrl());
            userToUpdate.get().setIsBlocked(updateData.isBlocked());
            userToUpdate.get().setIsDeleted(updateData.isDeleted());
            userToUpdate.get().setIsVerified(updateData.isVerified());
            if (updateData.getCreatedAt() != null) userToUpdate.get().setCreatedAt(updateData.getCreatedAt());
            if (updateData.getUpdatedAt() != null) userToUpdate.get().setUpdatedAt(updateData.getUpdatedAt());

            User updatedUser = userService.update(userToUpdate.get());

            JedisPool jedisPool = new JedisPool("localhost", 6379);
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.set(String.valueOf(userToUpdate.get().getId()), "");
            }
            jedisPool.close();

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
