package com.example.shop_sv.modules.users.controller;

import com.example.shop_sv.modules.jwt.JwtService;
import com.example.shop_sv.modules.users.model.dto.request.FormLogin;
import com.example.shop_sv.modules.users.model.dto.request.FormRegister;
import com.example.shop_sv.modules.users.model.dto.responne.UserRespone;
import com.example.shop_sv.modules.users.model.entity.User;
import com.example.shop_sv.modules.users.service.UserService;
import jakarta.validation.Valid;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody FormLogin data){
        try {
            UserRespone user = userService.getUserByName(data.getUsername());

            if(user == null){
                throw new Exception("Tài khoản không tồn tại");
            }

            if(!BCrypt.checkpw(data.getPassword(), user.getPassword())){
                throw new Exception("Sai mật khẩu");
            }

            if(!user.getIsBlock()){
                throw new Exception("Tài khoản da bi khoa");
            }

            String token = JwtService.createTokenUser(user);

            JedisPool jedisPool = new JedisPool("localhost", 6379);
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.set(String.valueOf(user.getId()), token);
            }
            jedisPool.close();

            return new ResponseEntity<>(token, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody FormRegister formRegister){
        try{
            userService.save(formRegister);
            return new ResponseEntity<>("Đăng ký thành công", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
