package com.example.shop_sv.modules.users.controller;


import com.example.shop_sv.modules.users.model.dto.responne.UserRespone;
import com.example.shop_sv.modules.users.security.jwt.JwtProvider;
import com.example.shop_sv.modules.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;
    @GetMapping("/")
    public ResponseEntity<Object> getUser(@RequestHeader("Authorization") String tokenFirst){
        String token = tokenFirst.substring(7);
        String user = jwtProvider.getUserNameAndIdAndRolesFromToken(token);
        String[] parts = user.split(" ");
        String username = parts[0];
       try{
              UserRespone userRespone = userService.getUserByName(username);
              return new ResponseEntity<>(userRespone, HttpStatus.OK);
         }catch (Exception e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
    }
}
